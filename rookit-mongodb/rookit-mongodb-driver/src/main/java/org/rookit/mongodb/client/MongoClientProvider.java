
package org.rookit.mongodb.client;

import com.google.common.base.MoreObjects;
import com.google.common.io.Closer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@SuppressWarnings("javadoc")
final class MongoClientProvider implements Provider<MongoClient> {

    private final MongoClientSettings settings;
    private final Closer closer;

    @Inject
    private MongoClientProvider(final MongoClientSettings settings, final Closer closer) {
        this.settings = settings;
        this.closer = closer;
    }

    @Override
    public MongoClient get() {
        final MongoClient mongoClient = MongoClients.create(this.settings);

        return this.closer.register(new MongoClientCloser(mongoClient));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("settings", this.settings)
                .add("closer", this.closer)
                .toString();
    }
}
