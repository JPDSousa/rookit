package org.rookit.mongodb.datastore;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.storage.RookitModelDataStore;
import org.rookit.api.storage.query.PlaylistQuery;
import org.rookit.api.storage.update.PlaylistUpdateQuery;
import org.rookit.api.storage.update.filter.PlaylistUpdateFilterQuery;
import org.rookit.failsafe.Failsafe;

final class MongoPlaylistDataStore extends BaseMongoDataStore<Playlist, PlaylistQuery,
        PlaylistUpdateQuery, PlaylistUpdateFilterQuery> implements RookitModelDataStore<Playlist, PlaylistQuery, PlaylistUpdateQuery,
                PlaylistUpdateFilterQuery> {

    @Inject
    private MongoPlaylistDataStore(final Failsafe failsafe,
                                   final MongoCollection<Playlist> collection,
                                   final MongoWriteOperationManager<Playlist, PlaylistUpdateQuery,
                                           PlaylistUpdateFilterQuery> writeOperations,
                                   final MongoReadOperationManager<Playlist, PlaylistQuery> readOperations) {
        super(failsafe, collection, writeOperations, readOperations);
    }
}
