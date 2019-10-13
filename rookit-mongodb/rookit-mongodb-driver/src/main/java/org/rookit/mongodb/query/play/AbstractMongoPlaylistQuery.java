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
package org.rookit.mongodb.query.play;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.GenericPlaylistMetaType;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.storage.query.GenericPlaylistQuery;
import org.rookit.mongodb.query.MongoQueryResultFactory;
import org.rookit.mongodb.query.genre.AbstractMongoGenreableQuery;

abstract class AbstractMongoPlaylistQuery<E extends Playlist, Q extends GenericPlaylistQuery<E, Q>,
        P extends GenericPlaylistMetaType<E>> extends AbstractMongoGenreableQuery<E, Q, P>
        implements GenericPlaylistQuery<E, Q> {

    AbstractMongoPlaylistQuery(final P properties,
                               final Bson initialClause,
                               final MongoQueryResultFactory<E> resultFactory) {
        super(properties, initialClause, resultFactory);
    }

    @Override
    public Q withType(final TypePlaylist type) {
        return addQueryClause(Filters.eq(properties().type().propertyName(), type));
    }

    @Override
    public Q withImage(final BiStream image) {
        return addQueryClause(Filters.eq(properties().image().propertyName(), image));
    }

    @Override
    public Q withName(final String name) {
        return addQueryClause(Filters.eq(properties().name().propertyName(), name));
    }

}
