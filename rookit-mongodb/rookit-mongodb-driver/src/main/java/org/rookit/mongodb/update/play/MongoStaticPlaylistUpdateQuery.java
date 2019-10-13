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
package org.rookit.mongodb.update.play;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.StaticPlaylistMetaType;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.update.StaticPlaylistUpdateQuery;
import org.rookit.api.storage.update.filter.StaticPlaylistUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;

import java.util.Collection;
import java.util.List;

final class MongoStaticPlaylistUpdateQuery extends AbstractMongoPlaylistUpdateQuery<StaticPlaylist,
        StaticPlaylistUpdateQuery, StaticPlaylistUpdateFilterQuery, StaticPlaylistMetaType>
        implements StaticPlaylistUpdateQuery {

    MongoStaticPlaylistUpdateQuery(final UpdateFilterQueryFactory<StaticPlaylistUpdateFilterQuery> filterQueryFactory,
                                   final List<Bson> updates,
                                   final StaticPlaylistMetaType properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public StaticPlaylistUpdateQuery addAllTracks(final Collection<Track> tracks) {
        final String fieldName = properties().tracks().propertyName();
        final Bson bson = Updates.addEachToSet(fieldName, ImmutableList.copyOf(tracks));
        return appendUpdate(bson);
    }

    @Override
    public StaticPlaylistUpdateQuery addTracks(final Track tracks) {
        return addAllTracks(ImmutableList.of(tracks));
    }

    @Override
    public StaticPlaylistUpdateQuery removeTracks(final Track tracks) {
        return removeAllTracks(ImmutableList.of(tracks));
    }

    @Override
    public StaticPlaylistUpdateQuery removeAllTracks(final Collection<Track> tracks) {
        final String propertyName = properties().tracks().propertyName();
        final Bson bson = Updates.pullAll(propertyName, ImmutableList.copyOf(tracks));
        return appendUpdate(bson);
    }

    @Override
    protected StaticPlaylistUpdateQuery self() {
        return this;
    }
}
