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
package org.rookit.mongodb.update.genre;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.bson.conversions.Bson;
import org.rookit.api.dm.genre.GenreMetaType;
import org.rookit.api.storage.update.GenreUpdateQuery;
import org.rookit.api.storage.update.filter.GenreUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.MongoUpdateQueryFactory;

final class MongoGenreUpdateQueryFactory implements MongoUpdateQueryFactory<GenreUpdateQuery, GenreUpdateFilterQuery> {

    private final GenreMetaType properties;
    private final UpdateFilterQueryFactory<GenreUpdateFilterQuery> filterQueryFactory;

    @Inject
    private MongoGenreUpdateQueryFactory(final GenreMetaType properties,
                                         final UpdateFilterQueryFactory<GenreUpdateFilterQuery> filterQueryFactory) {
        this.properties = properties;
        this.filterQueryFactory = filterQueryFactory;
    }

    @Override
    public GenreUpdateQuery create(final Bson initialState) {
        return new MongoGenreUpdateQuery(this.filterQueryFactory,
                ImmutableList.of(initialState),
                this.properties);
    }

    @Override
    public GenreUpdateQuery create() {
        return new MongoGenreUpdateQuery(this.filterQueryFactory,
                ImmutableList.of(),
                this.properties);
    }

    @Override
    public String toString() {
        return "MongoGenreUpdateQueryFactory{" +
                "properties=" + this.properties +
                ", filterQueryFactory=" + this.filterQueryFactory +
                "}";
    }
}
