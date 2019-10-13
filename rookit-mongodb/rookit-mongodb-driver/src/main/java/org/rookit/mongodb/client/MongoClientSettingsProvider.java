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
package org.rookit.mongodb.client;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import org.bson.codecs.configuration.CodecRegistry;
import org.rookit.mongodb.config.ConnectionConfig;

final class MongoClientSettingsProvider implements Provider<MongoClientSettings> {

    private final MongoClientSettings settings;

    @Inject
    private MongoClientSettingsProvider(final CodecRegistry registry,
                                        final ConnectionConfig config) {
        final ServerAddress address = new ServerAddress(config.hostName(), config.port());
        this.settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(ImmutableList.of(address)))
                // TODO provide app support
                .applicationName("Rookit")
                .codecRegistry(registry)
                .build();
    }

    @Override
    public MongoClientSettings get() {
        return this.settings;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("settings", this.settings)
                .toString();
    }
}
