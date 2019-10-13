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
package org.rookit.mongodb.query.result;

import com.google.common.base.MoreObjects;
import com.mongodb.client.MongoCollection;
import one.util.streamex.StreamEx;
import org.bson.conversions.Bson;
import org.rookit.api.dm.RookitModel;
import org.rookit.storage.query.QueryResult;

import java.util.Optional;

final class MongoQueryResult<E extends RookitModel> implements QueryResult<E> {

    private final MongoCollection<E> collection;
    private final Bson query;
    private final Bson sort;

    MongoQueryResult(final MongoCollection<E> collection, final Bson query, final Bson sort) {
        this.collection = collection;
        this.query = query;
        this.sort = sort;
    }

    @Override
    public long count() {
        return this.collection.count(this.query);
    }

    @Override
    public Optional<E> first() {
        return Optional.ofNullable(this.collection.find(this.query).sort(this.sort).first());
    }

    @Override
    public StreamEx<E> stream() {
        return StreamEx.of(this.collection.find(this.query).sort(this.sort).spliterator());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("collection", this.collection)
                .add("query", this.query)
                .add("sort", this.sort)
                .toString();
    }
}
