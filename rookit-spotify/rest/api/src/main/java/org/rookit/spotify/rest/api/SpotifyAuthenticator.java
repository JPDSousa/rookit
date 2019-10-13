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

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.rookit.spotify.model.api.authentication.ClientCredentials;

import java.io.IOException;
import java.util.Objects;

final class SpotifyAuthenticator implements Interceptor {

    private final ClientCredentialsHandler credentialsSupplier;

    SpotifyAuthenticator(final ClientCredentialsHandler credentialsSupplier) {
        this.credentialsSupplier = credentialsSupplier;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request request = chain.request();

        if (Objects.isNull(request.header("Authorization"))) {
            return chain.proceed(refreshToken(chain, request));
        }

        return chain.proceed(request);
    }

    private Request refreshToken(final Chain chain, final Request request) {
        final ClientCredentials clientCredentials = this.credentialsSupplier.get(chain::proceed);
        return request.newBuilder()
                .addHeader("Authorization", "Bearer " + clientCredentials.accessToken())
                .build();
    }

    @Override
    public String toString() {
        return "SpotifyAuthenticator{" +
                "credentialsSupplier=" + this.credentialsSupplier +
                "}";
    }
}
