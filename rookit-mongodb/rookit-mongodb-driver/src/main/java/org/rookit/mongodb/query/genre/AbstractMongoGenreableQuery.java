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

import com.google.common.collect.ImmutableList;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.able.GenericGenreableMetaType;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.storage.query.GenreableQuery;
import org.rookit.mongodb.query.MongoQueryResultFactory;
import org.rookit.mongodb.query.play.able.AbstractPlayableQuery;

import java.util.Collection;

public abstract class AbstractMongoGenreableQuery<E extends Genreable,
        Q extends GenreableQuery<E, Q>,
        P extends GenericGenreableMetaType<E>> extends AbstractPlayableQuery<E, Q, P> implements GenreableQuery<E, Q> {

    protected AbstractMongoGenreableQuery(final P properties,
                                          final Bson initialClause,
                                          final MongoQueryResultFactory<E> resultFactory) {
        super(properties, initialClause, resultFactory);
    }

    @Override
    public Q withGenres(final Collection<Genre> present) {
        return addQueryClause(Filters.all(properties().genres().propertyName(), present));
    }

    @Override
    public Q withNoGenres(final Genre absent) {
        return withNoGenres(ImmutableList.of(absent));
    }

    @Override
    public Q withNoGenres(final Collection<Genre> absent) {
        return addQueryClause(Filters.nin(properties().genres().propertyName(), absent));
    }

    @Override
    public Q withGenres(final Genre present) {
        return withGenres(ImmutableList.of(present));
    }
}
