
package org.rookit.mongodb.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.mongodb.client.MongoDatabase;
import org.rookit.mongodb.client.ClientModule;
import org.rookit.mongodb.datastore.DataStoreModule;
import org.rookit.mongodb.gridfs.GridFsBiStreamModule;
import org.rookit.mongodb.query.QueriesModule;
import org.rookit.mongodb.update.MongoUpdateQueryModule;
import org.rookit.mongodb.utils.UtilsModule;

@SuppressWarnings("javadoc")
public final class MongoDbModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new MongoDbModule(),
            ClientModule.getModule(),
            UtilsModule.getModule(),
            DataStoreModule.getModule(),
            GridFsBiStreamModule.getModule(),
            QueriesModule.getModule(),
            MongoUpdateQueryModule.getModule());

    public static Module getModule() {
        return MODULE;
    }

    private MongoDbModule() {}

    @Override
    protected void configure() {
        bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);
    }
}
