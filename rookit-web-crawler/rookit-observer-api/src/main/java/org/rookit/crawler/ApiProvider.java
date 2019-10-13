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
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.models.authentication.ClientCredentials;
import org.rookit.crawler.guice.Cache;

import java.util.concurrent.ConcurrentMap;

final class ApiProvider implements Provider<Api> {

    private final ClientCredentials credentials;
    private final RateLimiter limiter;
    private final ConcurrentMap<String, String> cache;

    @Inject
    private ApiProvider(final ClientCredentials credentials,
                        final RateLimiter limiter,
                        @Cache final ConcurrentMap<String, String> cache) {
        this.credentials = credentials;
        this.limiter = limiter;
        this.cache = cache;
    }

    @Override
    public Api get() {
        return Api.builder()
                .accessToken(this.credentials.getAccessToken())
                .cache(this.cache)
                .rateLimiter(this.limiter)
                .build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("credentials", this.credentials)
                .add("limiter", this.limiter)
                .add("cache", this.cache)
                .toString();
    }
}
