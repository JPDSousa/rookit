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

import com.google.inject.Inject;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.StaticPlaylistMetaType;
import org.rookit.api.storage.query.PlaylistQuery;
import org.rookit.api.storage.query.StaticPlaylistQuery;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;

final class MongoStaticPlaylistQueryFactory implements MongoQueryFactory<StaticPlaylist, StaticPlaylistQuery> {

    private final StaticPlaylistMetaType properties;
    private final MongoQueryResultFactory<StaticPlaylist> resultFactory;
    private final MongoQueryFactory<Playlist, PlaylistQuery> queryFactory;

    @Inject
    private MongoStaticPlaylistQueryFactory(@Mongo final StaticPlaylistMetaType properties,
                                            final MongoQueryResultFactory<StaticPlaylist> resultFactory,
                                            final MongoQueryFactory<Playlist, PlaylistQuery> queryFactory) {
        this.properties = properties;
        this.resultFactory = resultFactory;
        this.queryFactory = queryFactory;
    }

    @Override
    public StaticPlaylistQuery create(final Bson query) {
        return new MongoStaticPlaylistQuery(this.properties, query, this.resultFactory, this.queryFactory);
    }

    @Override
    public String toString() {
        return "MongoStaticPlaylistQueryFactory{" +
                "properties=" + this.properties +
                ", resultFactory=" + this.resultFactory +
                ", queryFactory=" + this.queryFactory +
                "}";
    }
}
