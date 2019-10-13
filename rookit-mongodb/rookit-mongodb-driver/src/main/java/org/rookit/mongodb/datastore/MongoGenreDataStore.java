package org.rookit.mongodb.datastore;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.storage.RookitModelDataStore;
import org.rookit.api.storage.query.GenreQuery;
import org.rookit.api.storage.update.GenreUpdateQuery;
import org.rookit.api.storage.update.filter.GenreUpdateFilterQuery;
import org.rookit.failsafe.Failsafe;

final class MongoGenreDataStore extends BaseMongoDataStore<Genre, GenreQuery,
        GenreUpdateQuery, GenreUpdateFilterQuery> implements RookitModelDataStore<Genre, GenreQuery, GenreUpdateQuery, GenreUpdateFilterQuery> {

    @Inject
    private MongoGenreDataStore(final Failsafe failsafe,
                                final MongoCollection<Genre> collection,
                                final MongoWriteOperationManager<Genre, GenreUpdateQuery,
                                        GenreUpdateFilterQuery> writeOperations,
                                final MongoReadOperationManager<Genre, GenreQuery> readOperations) {
        super(failsafe, collection, writeOperations, readOperations);
    }

}
