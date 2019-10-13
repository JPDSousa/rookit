package org.rookit.mongodb.datastore;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.album.Album;
import org.rookit.api.storage.RookitModelDataStore;
import org.rookit.api.storage.query.AlbumQuery;
import org.rookit.api.storage.update.AlbumUpdateQuery;
import org.rookit.api.storage.update.filter.AlbumUpdateFilterQuery;
import org.rookit.failsafe.Failsafe;

final class MongoAlbumDataStore extends BaseMongoDataStore<Album, AlbumQuery,
        AlbumUpdateQuery, AlbumUpdateFilterQuery> implements RookitModelDataStore<Album, AlbumQuery, AlbumUpdateQuery,
                AlbumUpdateFilterQuery> {

    @Inject
    private MongoAlbumDataStore(final Failsafe failsafe,
                                final MongoCollection<Album> collection,
                                final MongoWriteOperationManager<Album,
                                        AlbumUpdateQuery, AlbumUpdateFilterQuery> writeOperations,
                                final MongoReadOperationManager<Album, AlbumQuery> readOperations) {
        super(failsafe, collection, writeOperations, readOperations);
    }
}
