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

import org.bson.conversions.Bson;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.DynamicPlaylistMetaType;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.storage.query.DynamicPlaylistQuery;
import org.rookit.api.storage.query.PlaylistQuery;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;

final class MongoDynamicPlaylistQuery extends AbstractMongoPlaylistQuery<DynamicPlaylist, DynamicPlaylistQuery,
        DynamicPlaylistMetaType> implements DynamicPlaylistQuery {

    private final MongoQueryFactory<Playlist, PlaylistQuery> queryFactory;

    MongoDynamicPlaylistQuery(final DynamicPlaylistMetaType properties,
                              final Bson initialClause,
                              final MongoQueryResultFactory<DynamicPlaylist> resultFactory,
                              final MongoQueryFactory<Playlist, PlaylistQuery> queryFactory) {
        super(properties, initialClause, resultFactory);
        this.queryFactory = queryFactory;
    }

    @Override
    public PlaylistQuery toPlaylistQuery() {
        return this.queryFactory.create(this);
    }

    @Override
    protected DynamicPlaylistQuery self() {
        return this;
    }
}
