
package org.rookit.mongodb.guice;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.rookit.mongodb.config.ConnectionConfig;

@SuppressWarnings("javadoc")
final class MongoDatabaseProvider implements Provider<MongoDatabase> {

    private final MongoClient client;
    private final String databaseName;

    @Inject
    private MongoDatabaseProvider(final MongoClient client, final ConnectionConfig config) {
        this.client = client;
        this.databaseName = config.dbName();
    }

    @Override
    public MongoDatabase get() {
        return this.client.getDatabase(this.databaseName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("client", this.client)
                .add("databaseName", this.databaseName)
                .toString();
    }
}
