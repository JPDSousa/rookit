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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.immutables.value.Value;
import org.rookit.io.http.BaseUrlConfig;

import java.util.Base64;
import java.util.ServiceLoader;

@Value.Immutable
public interface CredentialsProvider extends BaseUrlConfig {

    @Value.Default
    default Gson gson() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        for (final TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
        }
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        return gsonBuilder.create();
    }

    @Value.Default
    default ClientCredentialsHandler clientCredentialsSupplier() {
        final String credentials = clientId() + ":" + clientSecret();
        final String encodedCredentials = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
        final RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        final Request request = new Request.Builder()
                .url(url())
                .post(body)
                .header("Authorization", encodedCredentials)
                .build();
        return new ClientCredentialsSupplier(gson(), request);
    }

    String clientId();

    String clientSecret();

    @Value.Default
    @Override
    default HttpUrl url() {
        return new HttpUrl.Builder()
                .scheme(scheme())
                .host(host())
                .port(port())
                .addPathSegments("api")
                .addPathSegments("token")
                .build();
    }

    @Value.Default
    @Override
    default String host() {
        return "accounts.spotify.com";
    }

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
}
