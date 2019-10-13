/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.spotify.rest.api;

import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.rookit.spotify.model.api.authentication.ClientCredentials;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.util.Objects;

@ThreadSafe
final class ClientCredentialsSupplier implements ClientCredentialsHandler {

    private final Object instanceLock;

    private final Gson gson;
    private final Request request;

    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private ClientCredentials credentials;
    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private long checkpoint;

    ClientCredentialsSupplier(final Gson gson,
                              final Request request) {
        this.instanceLock = new Object();
        this.gson = gson;
        this.request = request;
    }

    @Override
    public ClientCredentials get(final RequestHandler requestHandler) {
        // TODO is there a workaround for this synchronized? It seems a bit bottleneck-ish
        synchronized (this.instanceLock) {
            if (Objects.isNull(this.credentials) || hasExpired()) {
                this.credentials = fetchNewCredentials(requestHandler);
            }
            return this.credentials;
        }
    }

    private boolean hasExpired() {
        return (this.checkpoint + this.credentials.expiresIn()) < System.currentTimeMillis();
    }

    @SuppressWarnings("resource")
    private ClientCredentials fetchNewCredentials(final RequestHandler requestHandler) {
        // TODO make this call async
        try {
            final Response response = requestHandler.handle(this.request);
            return handleResponse(response);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientCredentials handleResponse(final Response response) throws IOException {
        final ResponseBody body = response.body();
        if (Objects.isNull(body)) {
            throw new RuntimeException("The response for the credentials request does not contain body.");
        }
        this.checkpoint = System.currentTimeMillis();
        return this.gson.fromJson(body.string(), ClientCredentials.class);
    }

    @Override
    public String toString() {
        synchronized (this.instanceLock) {
            return "ClientCredentialsSupplier{" +
                    "instanceLock=" + this.instanceLock +
                    ", gson=" + this.gson +
                    ", request=" + this.request +
                    ", credentials=" + this.credentials +
                    ", checkpoint=" + this.checkpoint +
                    "}";
        }
    }
}
