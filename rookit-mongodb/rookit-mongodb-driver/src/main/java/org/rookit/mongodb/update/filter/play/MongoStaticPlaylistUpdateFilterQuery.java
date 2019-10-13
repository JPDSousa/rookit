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
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.query.StaticPlaylistQuery;
import org.rookit.api.storage.update.filter.StaticPlaylistUpdateFilterQuery;

import java.util.Collection;

final class MongoStaticPlaylistUpdateFilterQuery extends AbstractMongoPlaylistUpdateFilterQuery<StaticPlaylist, 
        StaticPlaylistQuery, StaticPlaylistUpdateFilterQuery> implements StaticPlaylistUpdateFilterQuery {

    MongoStaticPlaylistUpdateFilterQuery(final MongoCollection<StaticPlaylist> collection, 
                                         final Bson update, 
                                         final StaticPlaylistQuery query) {
        super(collection, update, query);
    }

    @Override
    public StaticPlaylistUpdateFilterQuery withNoTracks(final Track absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoTracks(absent));
    }

    @Override
    public StaticPlaylistUpdateFilterQuery withNoTracks(final Collection<Track> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoTracks(absent));
    }

    @Override
    public StaticPlaylistUpdateFilterQuery withTracks(final Collection<Track> present) {
        return appendClause(mongoQuery -> mongoQuery.withTracks(present));
    }

    @Override
    public StaticPlaylistUpdateFilterQuery withTracks(final Track present) {
        return appendClause(mongoQuery -> mongoQuery.withTracks(present));
    }

    @Override
    protected StaticPlaylistUpdateFilterQuery self() {
        return this;
    }
}
