
package org.rookit.mongodb.gridfs;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import org.rookit.api.dm.artist.ArtistMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.utils.source.Prototype;

@Prototype
final class ArtistBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

    private static final String BUCKET_NAME = "pictures";

    @Inject
    private ArtistBiStreamFactoryProvider(final MongoDatabase database,
                                          @Mongo final ArtistMetaType properties,
                                          final Failsafe failsafe) {
        super(database, BUCKET_NAME, properties, failsafe);
    }

}
