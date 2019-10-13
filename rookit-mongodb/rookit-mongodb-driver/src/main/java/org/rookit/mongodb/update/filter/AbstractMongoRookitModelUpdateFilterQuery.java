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
package org.rookit.mongodb.update.filter;

import com.mongodb.client.MongoCollection;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.storage.query.RookitModelQuery;
import org.rookit.utils.source.Prototype;

import java.util.function.Consumer;

@Prototype
public abstract class AbstractMongoRookitModelUpdateFilterQuery<E extends RookitModel, Q extends RookitModelQuery<E, Q>,
        U extends RookitModelUpdateFilterQuery<U>> implements RookitModelUpdateFilterQuery<U> {

    private final MongoCollection<E> collection;
    private final Bson update;
    private final boolean requiresUpdate;
    private final Q query;

    protected AbstractMongoRookitModelUpdateFilterQuery(final MongoCollection<E> collection,
                                                        final Bson update,
                                                        final Q query) {
        this.collection = collection;
        this.update = update;
        this.query = query;

        if (this.update instanceof BsonDocument) {
            this.requiresUpdate = !((BsonDocument) this.update).isEmpty();
        }
        else if (this.update instanceof BsonArray) {
            this.requiresUpdate = !((BsonArray) this.update).isEmpty();
        }
        else {
            this.requiresUpdate = true;
        }
    }

    @Override
    public void execute() {
        /*
         * TODO this is dirty. It is not 100% guaranteed that this query (Q) comes from a MongoDB implementation.
         * The solution is to create an additional Query API that extends BSON, and use it here, as well as in the
         * original Query API (through delegation). Both the new Query API as well as this implementation and the
         * original Query API's implementation can be automated.
         */
        if (this.requiresUpdate) {
            this.collection.updateMany((Bson) this.query, this.update);
        }
    }

    protected abstract U self();

    protected U appendClause(final Consumer<Q> queryOp) {
        if (this.requiresUpdate) {
            queryOp.accept(this.query);
        }
        return self();
    }

    @Override
    public U withAnyId() {
        return appendClause(Q::withAnyId);
    }

    @Override
    public U withId(final String id) {
        return appendClause(mongoQuery -> mongoQuery.withId(id));
    }

    @Override
    public U withNoId() {
        return appendClause(Q::withNoId);
    }

    @Override
    public String toString() {
        return "AbstractMongoRookitModelUpdateFilterQuery{" +
                "collection=" + this.collection +
                ", update=" + this.update +
                ", requiresUpdate=" + this.requiresUpdate +
                ", query=" + this.query +
                "}";
    }
}
