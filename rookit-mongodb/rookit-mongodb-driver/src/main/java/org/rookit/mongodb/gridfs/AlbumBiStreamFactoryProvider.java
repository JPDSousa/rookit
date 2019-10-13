
package org.rookit.mongodb.gridfs;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import org.rookit.api.dm.album.AlbumMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.utils.source.Prototype;

@Prototype
final class AlbumBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

    private static final String BUCKET_NAME = "cover";

    @Inject
    private AlbumBiStreamFactoryProvider(final MongoDatabase database,
                                         @Mongo final AlbumMetaType properties,
                                         final Failsafe failsafe) {
        super(database, BUCKET_NAME, properties, failsafe);
    }

}
