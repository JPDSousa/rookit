
package org.rookit.mongodb.gridfs;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import org.rookit.api.dm.track.TrackMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.utils.source.Prototype;

@Prototype
final class TrackBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

    private static final String BUCKET_NAME = "audio";

    @Inject
    private TrackBiStreamFactoryProvider(final MongoDatabase database,
                                         @Mongo final TrackMetaType properties,
                                         final Failsafe failsafe) {
        super(database, BUCKET_NAME, properties, failsafe);
    }

}
