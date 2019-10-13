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
package org.rookit.mongodb.update.filter.play;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.storage.query.GenericPlaylistQuery;
import org.rookit.api.storage.update.filter.GenericPlaylistUpdateFilterQuery;
import org.rookit.mongodb.update.filter.genre.able.AbstractMongoGenreableUpdateFilterQuery;

abstract class AbstractMongoPlaylistUpdateFilterQuery<E extends Playlist, Q extends GenericPlaylistQuery<E, Q>,
        U extends GenericPlaylistUpdateFilterQuery<U>> extends AbstractMongoGenreableUpdateFilterQuery<E, Q, U>
        implements GenericPlaylistUpdateFilterQuery<U> {

    protected AbstractMongoPlaylistUpdateFilterQuery(final MongoCollection<E> collection,
                                                     final Bson update,
                                                     final Q query) {
        super(collection, update, query);
    }

    @Override
    public U withType(final TypePlaylist type) {
        return appendClause(mongoQuery -> mongoQuery.withType(type));
    }

    @Override
    public U withImage(final BiStream image) {
        return appendClause(mongoQuery -> mongoQuery.withImage(image));
    }

    @Override
    public U withName(final String name) {
        return appendClause(mongoQuery -> mongoQuery.withName(name));
    }

}
