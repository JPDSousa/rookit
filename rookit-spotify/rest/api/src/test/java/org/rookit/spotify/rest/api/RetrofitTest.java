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

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.rookit.io.http.RetrofitApiConfig;
import org.rookit.spotify.rest.utils.MockClientCredentialsSupplier;
import org.rookit.spotify.rest.utils.TestDispatcher;

import java.io.IOException;
import java.time.Duration;

public class RetrofitTest {

    protected static MockWebServer webServer;
    protected static SpotifyRestApi spotifyRestApi;

    @BeforeAll
    static void setUp() throws IOException {
        webServer = new MockWebServer();
        webServer.setDispatcher(new TestDispatcher());
        webServer.start();

        final CredentialsProvider credentialsProvider = ImmutableCredentialsProvider.builder()
                .clientId("clientId")
                .clientSecret("clientSecret")
                .clientCredentialsSupplier(new MockClientCredentialsSupplier())
                .build();
        final RetrofitApiConfig config = ImmutableSpotifyRetrofitApiConfig.builder()
                .url(webServer.url("/"))
                .readTimeout(Duration.ofSeconds(3))
                .connectTimeout(Duration.ofSeconds(1))
                .credentials(credentialsProvider)
                .build();

        spotifyRestApi = RetrofitSpotifyRestApi.create(config);
    }

    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    @AfterAll
    static void tearDown() throws IOException {
        webServer.shutdown();
    }

}
