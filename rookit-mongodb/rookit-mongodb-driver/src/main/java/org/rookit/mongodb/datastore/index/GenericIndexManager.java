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
package org.rookit.mongodb.datastore.index;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import org.bson.conversions.Bson;
import org.rookit.api.dm.RookitModel;
import org.rookit.mongodb.datastore.MongoIndex;
import org.rookit.mongodb.datastore.MongoIndexManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final class GenericIndexManager<E extends RookitModel> implements MongoIndexManager<E> {

    private final String id;
    private final Set<MongoIndex<E>> indices;

    GenericIndexManager(final String id, final Set<MongoIndex<E>> indices) {
        this.id = id;
        this.indices = ImmutableSet.copyOf(indices);
    }

    @Override
    public void createIndexes(final MongoCollection<E> collection) {
        final List<IndexModel> indices = this.indices.stream()
                .map(MongoIndex::indexModel)
                .collect(Collectors.toList());
        collection.createIndexes(indices);
    }

    @Override
    public Bson createUniqueQuery(final E element) {
        final List<Bson> filters = Lists.newLinkedList();
        element.id()
                .map(idValue -> Filters.eq(this.id, idValue))
                .ifPresent(filters::add);

        this.indices.stream()
                .filter(index -> index.indexModel().getOptions().isUnique())
                .map(index -> index.queryFrom(element))
                .forEach(filters::add);

        return Filters.or(filters);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", this.id)
                .add("indices", this.indices)
                .toString();
    }
}
