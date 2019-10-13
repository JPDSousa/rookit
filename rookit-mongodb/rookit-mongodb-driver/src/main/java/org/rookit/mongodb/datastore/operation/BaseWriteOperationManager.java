/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.mongodb.datastore.operation;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import org.rookit.api.dm.RookitModel;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.DependencyHandler;
import org.rookit.mongodb.datastore.MongoIndexManager;
import org.rookit.mongodb.datastore.MongoWriteOperationManager;
import org.rookit.mongodb.update.MongoUpdateQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class BaseWriteOperationManager<T extends RookitModel,
        U extends RookitModelUpdateQuery<U, F>,
        F extends RookitModelUpdateFilterQuery<F>> implements MongoWriteOperationManager<T, U, F> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseWriteOperationManager.class);
    
    private final Failsafe failsafe;
    private final DependencyHandler<T> dependencyHandler;
    private final MongoCollection<T> collection;
    private final MongoIndexManager<T> indexManager;
    private final MongoUpdateQueryFactory<U, F> updateQueryFactory;

    BaseWriteOperationManager(final Failsafe failsafe, 
                              final DependencyHandler<T> dependencyHandler,
                              final MongoCollection<T> collection,
                              final MongoIndexManager<T> indexManager,
                              final MongoUpdateQueryFactory<U, F> updateQueryFactory) {
        this.failsafe = failsafe;
        // TODO this looks like an anti-pattern -> depending on the index manager just to use it this way seems :(
        indexManager.createIndexes(collection);
        this.dependencyHandler = dependencyHandler;
        this.collection = collection;
        this.indexManager = indexManager;
        this.updateQueryFactory = updateQueryFactory;
    }

    @Override
    public void insert(final T element) {
        this.failsafe.checkArgument().isNotNull(logger, element, "type");
        this.dependencyHandler.handleDependenciesFor(element);
        final FindOneAndReplaceOptions options = new FindOneAndReplaceOptions();
        options.upsert(true);

        this.collection.findOneAndReplace(this.indexManager.createUniqueQuery(element), element, options);
    }

    @Override
    public U update() {
        return this.updateQueryFactory.create();
    }

    @Override
    public void delete(final T element) {
        this.failsafe.checkArgument().isNotNull(logger, element, "type");
        this.collection.deleteOne(this.indexManager.createUniqueQuery(element));
    }

    @Override
    public void clear() {
        logger.trace("Deleting all elements in {}.", this.collection.getNamespace().getCollectionName());
        this.collection.drop();
    }

    @Override
    public String toString() {
        return "BaseWriteOperationManager{" +
                "injector=" + this.failsafe +
                ", dependencyHandler=" + this.dependencyHandler +
                ", collection=" + this.collection +
                ", indexManager=" + this.indexManager +
                ", updateQueryFactory=" + this.updateQueryFactory +
                "}";
    }
}
