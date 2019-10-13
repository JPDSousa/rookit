package org.rookit.mongodb.datastore;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.storage.RookitModelDataStore;
import org.rookit.api.storage.query.ArtistQuery;
import org.rookit.api.storage.update.ArtistUpdateQuery;
import org.rookit.api.storage.update.filter.ArtistUpdateFilterQuery;
import org.rookit.failsafe.Failsafe;

final class MongoArtistDataStore extends BaseMongoDataStore<Artist, ArtistQuery,
        ArtistUpdateQuery, ArtistUpdateFilterQuery> implements RookitModelDataStore<Artist, ArtistQuery,
                ArtistUpdateQuery, ArtistUpdateFilterQuery> {


    @Inject
    private MongoArtistDataStore(final Failsafe failsafe,
                                 final MongoCollection<Artist> collection,
                                 final MongoWriteOperationManager<Artist, ArtistUpdateQuery,
                                         ArtistUpdateFilterQuery> writeOperations,
                                 final MongoReadOperationManager<Artist, ArtistQuery> readOperations) {
        super(failsafe, collection, writeOperations, readOperations);
    }

}
