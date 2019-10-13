package org.rookit.mongodb.datastore;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.RookitModelDataStore;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.api.storage.update.TrackUpdateQuery;
import org.rookit.api.storage.update.filter.TrackUpdateFilterQuery;
import org.rookit.failsafe.Failsafe;

final class MongoTrackDataStore extends BaseMongoDataStore<Track, TrackQuery,
        TrackUpdateQuery, TrackUpdateFilterQuery> implements RookitModelDataStore<Track, TrackQuery,
                TrackUpdateQuery, TrackUpdateFilterQuery> {

    @Inject
    private MongoTrackDataStore(final Failsafe failsafe,
                                final MongoCollection<Track> collection,
                                final MongoWriteOperationManager<Track, TrackUpdateQuery,
                                        TrackUpdateFilterQuery> writeOperations,
                                final MongoReadOperationManager<Track, TrackQuery> readOperations) {
        super(failsafe, collection, writeOperations, readOperations);
    }

}
