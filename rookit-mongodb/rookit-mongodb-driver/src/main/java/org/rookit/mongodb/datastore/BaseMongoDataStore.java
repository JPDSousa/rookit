package org.rookit.mongodb.datastore;

import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.storage.query.RookitModelQuery;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BaseMongoDataStore<T extends RookitModel,
        Q extends RookitModelQuery<T, Q>,
        U extends RookitModelUpdateQuery<U, F>,
        F extends RookitModelUpdateFilterQuery<F>>
        implements RookitModelDataStore<T, Q, U, F> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseMongoDataStore.class);

    private final Failsafe failsafe;
    private final MongoCollection<T> collection;
    private final MongoWriteOperationManager<T, U, F> writeOperations;
    private final MongoReadOperationManager<T, Q> readOperations;

    BaseMongoDataStore(final Failsafe failsafe,
                       final MongoCollection<T> collection,
                       final MongoWriteOperationManager<T, U, F> writeOperations,
                       final MongoReadOperationManager<T, Q> readOperations) {
        this.failsafe = failsafe;
        this.collection = collection;
        this.writeOperations = writeOperations;
        this.readOperations = readOperations;
    }

    @Override
    public String name() {
        return this.collection.getNamespace().getCollectionName();
    }

    @Override
    public void insert(final T element) {
        this.failsafe.checkArgument().isNotNull(logger, element, "type");
        this.writeOperations.insert(element);
    }

    @Override
    public U update() {
        return this.writeOperations.update();
    }

    @Override
    public void delete(final T element) {
        this.failsafe.checkArgument().isNotNull(logger, element, "type");
        this.writeOperations.delete(element);
    }

    @Override
    public void clear() {
        this.writeOperations.clear();
    }

    @Override
    public void replace(final T element) {
        insert(element);
    }

    @Override
    public Q query() {
        return this.readOperations.query();
    }

    @Override
    public String toString() {
        return "BaseMongoDataStore{" +
                "injector=" + this.failsafe +
                ", collection=" + this.collection +
                ", writeOperations=" + this.writeOperations +
                ", readOperations=" + this.readOperations +
                "}";
    }
}
