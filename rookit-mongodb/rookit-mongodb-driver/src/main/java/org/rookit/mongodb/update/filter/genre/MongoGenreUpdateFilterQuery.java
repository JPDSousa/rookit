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
package org.rookit.mongodb.update.filter.genre;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.storage.filter.GenericGenreFilter;
import org.rookit.api.storage.query.GenreQuery;
import org.rookit.api.storage.update.filter.GenreUpdateFilterQuery;
import org.rookit.mongodb.update.filter.play.able.AbstractMongoPlayableUpdateFilterQuery;

final class MongoGenreUpdateFilterQuery extends AbstractMongoPlayableUpdateFilterQuery<Genre, GenreQuery,
        GenreUpdateFilterQuery> implements GenreUpdateFilterQuery {

    MongoGenreUpdateFilterQuery(final MongoCollection<Genre> collection, final Bson update, final GenreQuery query) {
        super(collection, update, query);
    }

    @Override
    public GenreUpdateFilterQuery withAnyDescription() {
        return appendClause(GenericGenreFilter::withAnyDescription);
    }

    @Override
    public GenreUpdateFilterQuery withDescription(final String description) {
        return appendClause(mongoQuery -> mongoQuery.withDescription(description));
    }

    @Override
    public GenreUpdateFilterQuery withName(final String name) {
        return appendClause(mongoQuery -> mongoQuery.withName(name));
    }

    @Override
    public GenreUpdateFilterQuery withNoDescription() {
        return appendClause(GenericGenreFilter::withNoDescription);
    }

    @Override
    protected GenreUpdateFilterQuery self() {
        return this;
    }
}
