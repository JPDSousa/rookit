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
package org.rookit.mongodb.update.track;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.VersionTrackMetaType;
import org.rookit.api.storage.update.VersionTrackUpdateQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.api.storage.update.filter.VersionTrackUpdateFilterQuery;

import java.util.Collection;
import java.util.List;

final class MongoVersionTrackUpdateQuery extends AbstractMongoTrackUpdateQuery<VersionTrack, VersionTrackUpdateQuery,
        VersionTrackUpdateFilterQuery, VersionTrackMetaType> implements VersionTrackUpdateQuery {

    MongoVersionTrackUpdateQuery(final UpdateFilterQueryFactory<VersionTrackUpdateFilterQuery> filterQueryFactory,
                                 final List<Bson> updates,
                                 final VersionTrackMetaType properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public VersionTrackUpdateQuery setVersionType(final TypeVersion versionType) {
        final String propertyName = properties().versionType().propertyName();
        return appendUpdate(Updates.set(propertyName, versionType));
    }

    @Override
    public VersionTrackUpdateQuery Track(final Track Track) {
        final String propertyName = properties().original().propertyName();
        return appendUpdate(Updates.set(propertyName, Track));
    }

    @Override
    public VersionTrackUpdateQuery addAllVersionArtists(final Collection<Artist> versionArtists) {
        final String propertyName = properties().versionArtists().propertyName();
        return appendUpdate(Updates.set(propertyName, ImmutableList.copyOf(versionArtists)));
    }

    @Override
    public VersionTrackUpdateQuery removeAllVersionArtists(final Collection<Artist> versionArtists) {
        final String propertyName = properties().versionArtists().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(versionArtists)));
    }

    @Override
    public VersionTrackUpdateQuery addVersionArtists(final Artist versionArtists) {
        return addAllVersionArtists(ImmutableList.of(versionArtists));
    }

    @Override
    public VersionTrackUpdateQuery removeVersionArtists(final Artist versionArtists) {
        return removeAllVersionArtists(ImmutableList.of(versionArtists));
    }

    @Override
    public VersionTrackUpdateQuery setVersionToken(final String versionToken) {
        return null;
    }

    @Override
    protected VersionTrackUpdateQuery self() {
        return null;
    }
}
