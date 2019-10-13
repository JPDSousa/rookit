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

import com.google.common.util.concurrent.RateLimiter;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.immutables.value.Value;
import org.rookit.io.http.RetrofitApiConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
@Value.Immutable
public interface SpotifyRetrofitApiConfig extends RetrofitApiConfig {

    @Value.Default
    @Override
    default String host() {
        return "api.spotify.com";
    }

    @SuppressWarnings("MagicNumber")
    @Value.Default
    @Override
    default int port() {
        return 443;
    }

    @Value.Default
    @Override
    default String scheme() {
        return "https";
    }

    @Value.Default
    @Override
    default HttpUrl url() {
        return RetrofitApiConfig.super.url();
    }

    @Value.Default
    @Override
    default Duration readTimeout() {
        return RetrofitApiConfig.super.readTimeout();
    }

    @Value.Default
    @Override
    default Duration connectTimeout() {
        return RetrofitApiConfig.super.connectTimeout();
    }

    @SuppressWarnings("MagicNumber")
    @Value.Default
    @Override
    default RateLimiter rateLimiter() {
        return RateLimiter.create(18);
    }

    @Value.Default
    @Override
    default ScheduledExecutorService scheduler() {
        return RetrofitApiConfig.super.scheduler();
    }

    CredentialsProvider credentials();

    @Value.Default
    @Override
    default OkHttpClient httpClient() {
        final Duration readTimeout = readTimeout();
        final Duration connectTimeout = connectTimeout();
        return new OkHttpClient.Builder()
                .addInterceptor(new SpotifyAuthenticator(credentials().clientCredentialsSupplier()))
                .addInterceptor(new RateLimiterInterceptor(rateLimiter(), 5, scheduler()))
                .readTimeout(readTimeout.getSeconds(), TimeUnit.SECONDS)
                .connectTimeout(connectTimeout.getSeconds(), TimeUnit.SECONDS)
                .build();
    }

    @Value.Default
    @Override
    default Retrofit retrofit() {
        return new Retrofit.Builder()
                .client(httpClient())
                .baseUrl(url())
                .addConverterFactory(GsonConverterFactory.create(credentials().gson()))
                .build();
    }
}
