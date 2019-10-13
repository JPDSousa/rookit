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
package org.rookit.mongodb.query;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.rookit.api.dm.GenericRookitModelMetaType;
import org.rookit.api.dm.RookitModel;
import org.rookit.storage.query.QueryResult;
import org.rookit.api.storage.query.RookitModelQuery;

import java.util.List;

public abstract class AbstractMongoRookitModelQuery<E extends RookitModel,
        Q extends RookitModelQuery<E, Q>,
        P extends GenericRookitModelMetaType<E>>
        implements RookitModelQuery<E, Q>, Bson {

    private final P properties;
    private final List<Bson> query;
    private final MongoQueryResultFactory<E> resultFactory;

    protected AbstractMongoRookitModelQuery(final P properties,
                                            final Bson initialClause,
                                            final MongoQueryResultFactory<E> resultFactory) {
        this.properties = properties;
        this.resultFactory = resultFactory;
        this.query = Lists.newArrayList();
        this.query.add(initialClause);
    }

    protected abstract Q self();

    @Override
    public QueryResult<E> execute() {
        return this.resultFactory.create(this);
    }

    protected Q addQueryClause(final Bson bson) {
        this.query.add(bson);
        return self();
    }

    protected P properties() {
        return this.properties;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> tDocumentClass,
                                                   final CodecRegistry codecRegistry) {
        // TODO can we remove the and operation, thus replacing this.query with a BsonDocument?
        return Filters.and(this.query).toBsonDocument(tDocumentClass, codecRegistry);
    }

    @Override
    public Q withId(final String id) {
        return addQueryClause(Filters.eq(this.properties.id().propertyName(), id));
    }

    @Override
    public Q withAnyId() {
        return addQueryClause(Filters.exists(this.properties.id().propertyName()));
    }

    @Override
    public Q withNoId() {
        return addQueryClause(Filters.exists(this.properties.id().propertyName(), false));
    }

    @Override
    public String toString() {
        return "AbstractMongoRookitModelQuery{" +
                "properties=" + this.properties +
                ", query=" + this.query +
                ", resultFactory=" + this.resultFactory +
                "}";
    }
}
