
package org.rookit.mongodb.test.inject;

import com.google.inject.AbstractModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings("javadoc")
public class MongoDataBaseTestModule extends AbstractModule {

    private final MongoClient client;

    public MongoDataBaseTestModule() {
        this.client = MongoClients.create();
    }

    @Override
    protected void configure() {
        bind(MongoClient.class).toInstance(this.client);
        bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);
    }
}
