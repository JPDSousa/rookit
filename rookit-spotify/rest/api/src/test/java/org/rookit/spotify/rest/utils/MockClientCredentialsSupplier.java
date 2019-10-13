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
package org.rookit.spotify.rest.utils;

import org.rookit.spotify.model.api.authentication.ClientCredentials;
import org.rookit.spotify.model.api.authentication.ImmutableClientCredentials;
import org.rookit.spotify.rest.api.ClientCredentialsHandler;
import org.rookit.spotify.rest.api.RequestHandler;

public final class MockClientCredentialsSupplier implements ClientCredentialsHandler {

    private final ClientCredentials credentials;

    public MockClientCredentialsSupplier() {
        this.credentials = ImmutableClientCredentials.builder()
                .accessToken("SomeRandomAccessToken")
                .tokenType("SomeTokenType")
                .expiresIn(Integer.MAX_VALUE)
                .build();
    }

    @Override
    public ClientCredentials get(final RequestHandler requestHandler) {
        return this.credentials;
    }
}
