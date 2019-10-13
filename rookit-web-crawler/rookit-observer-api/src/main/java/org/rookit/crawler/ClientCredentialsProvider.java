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
package org.rookit.crawler;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.Request;
import com.wrapper.spotify.models.authentication.ClientCredentials;

final class ClientCredentialsProvider implements Provider<ClientCredentials> {

    private final SpotifyConfig config;
    private final RequestDispatcher dispatcher;

    @Inject
    private ClientCredentialsProvider(final SpotifyConfig config,
                                      final RequestDispatcher dispatcher) {
        this.config = config;
        this.dispatcher = dispatcher;
    }

    @Override
    public ClientCredentials get() {
        final Request<ClientCredentials> request = Api.builder()
                .clientId(this.config.clientID())
                .clientSecret(this.config.clientSecret())
                .build()
                .clientCredentialsGrant()
                .build();
        return this.dispatcher.exec(request);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", this.config)
                .add("dispatcher", this.dispatcher)
                .toString();
    }
}
