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
package org.rookit.mongodb.update.filter.track;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.storage.query.VersionTrackQuery;
import org.rookit.api.storage.update.filter.VersionTrackUpdateFilterQuery;

import java.util.Collection;

final class MongoVersionTrackUpdateFilterQuery extends AbstractMongoTrackUpdateFilterQuery<VersionTrack,
        VersionTrackQuery, VersionTrackUpdateFilterQuery> implements VersionTrackUpdateFilterQuery {

    MongoVersionTrackUpdateFilterQuery(final MongoCollection<VersionTrack> collection,
                                       final Bson update,
                                       final VersionTrackQuery query) {
        super(collection, update, query);
    }

    @Override
    protected VersionTrackUpdateFilterQuery self() {
        return this;
    }

    @Override
    public VersionTrackUpdateFilterQuery withNoVersionArtists(final Artist absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoVersionArtists(absent));
    }

    @Override
    public VersionTrackUpdateFilterQuery withVersionArtists(final Artist present) {
        return appendClause(mongoQuery -> mongoQuery.withVersionArtists(present));
    }

    @Override
    public VersionTrackUpdateFilterQuery withNoVersionArtists(final Collection<Artist> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoVersionArtists(absent));
    }

    @Override
    public VersionTrackUpdateFilterQuery withVersionToken(final String versionToken) {
        return appendClause(mongoQuery -> mongoQuery.withVersionToken(versionToken));
    }

    @Override
    public VersionTrackUpdateFilterQuery withTrack(final Track Track) {
        return appendClause(mongoQuery -> mongoQuery.withTrack(Track));
    }

    @Override
    public VersionTrackUpdateFilterQuery withVersionType(final TypeVersion versionType) {
        return appendClause(mongoQuery -> mongoQuery.withVersionType(versionType));
    }

    @Override
    public VersionTrackUpdateFilterQuery withVersionArtists(final Collection<Artist> present) {
        return appendClause(mongoQuery -> mongoQuery.withVersionArtists(present));
    }
}
