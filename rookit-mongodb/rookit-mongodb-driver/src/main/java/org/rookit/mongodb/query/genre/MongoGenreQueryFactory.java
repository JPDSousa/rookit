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
package org.rookit.mongodb.query.genre;

import com.google.inject.Inject;
import org.bson.conversions.Bson;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.GenreMetaType;
import org.rookit.api.storage.query.GenreQuery;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;

final class MongoGenreQueryFactory implements MongoQueryFactory<Genre, GenreQuery> {

    private final GenreMetaType properties;
    private final MongoQueryResultFactory<Genre> resultFactory;

    @Inject
    private MongoGenreQueryFactory(@Mongo final GenreMetaType properties,
                                   final MongoQueryResultFactory<Genre> resultFactory) {
        this.properties = properties;
        this.resultFactory = resultFactory;
    }

    @Override
    public GenreQuery create(final Bson query) {
        return new MongoGenreQuery(this.properties, query, this.resultFactory);
    }

    @Override
    public String toString() {
        return "MongoGenreQueryFactory{" +
                "properties=" + this.properties +
                ", resultFactory=" + this.resultFactory +
                "}";
    }
}
