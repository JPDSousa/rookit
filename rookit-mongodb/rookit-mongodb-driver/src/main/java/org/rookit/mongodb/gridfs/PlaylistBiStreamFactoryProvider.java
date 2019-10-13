
package org.rookit.mongodb.gridfs;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import org.rookit.api.dm.play.PlaylistMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.utils.source.Prototype;

@Prototype
final class PlaylistBiStreamFactoryProvider extends AbstractBiStreamFactoryProvider {

    // TODO move the bucket official to a config file. Apply to others
    private static final String PLAYLIST_COVERS = "playlist_covers";

    @Inject
    private PlaylistBiStreamFactoryProvider(final MongoDatabase database,
                                            @Mongo final PlaylistMetaType properties,
                                            final Failsafe failsafe) {
        super(database, PLAYLIST_COVERS, properties, failsafe);
    }

}
