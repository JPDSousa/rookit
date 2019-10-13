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
package org.rookit.mongodb.query.track;

import com.google.common.collect.ImmutableSet;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.VersionTrackMetaType;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.api.storage.query.VersionTrackQuery;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;

import java.util.Collection;

final class MongoVersionTrackQuery extends AbstractMongoTrackQuery<VersionTrack, VersionTrackQuery,
        VersionTrackMetaType> implements VersionTrackQuery {

    private final MongoQueryFactory<Track, TrackQuery> queryFactory;

    MongoVersionTrackQuery(final VersionTrackMetaType properties,
                           final Bson initialClause,
                           final MongoQueryResultFactory<VersionTrack> resultFactory,
                           final MongoQueryFactory<Track, TrackQuery> queryFactory) {
        super(properties, initialClause, resultFactory);
        this.queryFactory = queryFactory;
    }

    @Override
    public VersionTrackQuery withVersionArtists(final Artist present) {
        return withVersionArtists(ImmutableSet.of(present));
    }

    @Override
    public VersionTrackQuery withVersionToken(final String versionToken) {
        return addQueryClause(Filters.eq(properties().versionToken().propertyName(), versionToken));
    }

    @Override
    public VersionTrackQuery withNoVersionArtists(final Artist absent) {
        return withNoVersionArtists(ImmutableSet.of(absent));
    }

    @Override
    public VersionTrackQuery withNoVersionArtists(final Collection<Artist> absent) {
        final String propertyName = properties().versionArtists().propertyName();

        return addQueryClause(Filters.nin(propertyName, absent));
    }

    @Override
    public VersionTrackQuery withTrack(final Track Track) {
        return addQueryClause(Filters.eq(properties().original().propertyName(), Track));
    }

    @Override
    public VersionTrackQuery withVersionType(final TypeVersion versionType) {
        return addQueryClause(Filters.eq(properties().versionType().propertyName(), versionType));
    }

    @Override
    public VersionTrackQuery withVersionArtists(final Collection<Artist> present) {
        final String propertyName = properties().versionArtists().propertyName();

        return addQueryClause(Filters.all(propertyName, present));
    }

    @Override
    public TrackQuery toTrackQuery() {
        return this.queryFactory.create(this);
    }

    @Override
    protected VersionTrackQuery self() {
        return this;
    }

    @Override
    public String toString() {
        return "MongoVersionTrackQuery{" +
                "queryFactory=" + this.queryFactory +
                "} " + super.toString();
    }
}
