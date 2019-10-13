
package org.rookit.mongodb.test.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public class MongoDatabaseProvider implements Provider<MongoDatabase> {

    private final MongoClient client;
    private final ThreadLocal<MongoDatabase> database;

    @Inject
    private MongoDatabaseProvider(final MongoClient client) {
        this.client = client;
        this.database = new ThreadLocal<MongoDatabase>() {

            @Override
            protected MongoDatabase initialValue() {
                return MongoDatabaseProvider.this.client
                        .getDatabase(Thread.currentThread().getName());
            }

        };
    }

    @Override
    public MongoDatabase get() {
        return this.database.get();
    }

}
