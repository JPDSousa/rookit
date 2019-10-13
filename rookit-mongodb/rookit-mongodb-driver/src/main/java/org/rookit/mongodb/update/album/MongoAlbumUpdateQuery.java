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
package org.rookit.mongodb.update.album;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumMetaType;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.storage.update.AlbumUpdateQuery;
import org.rookit.api.storage.update.filter.AlbumUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.genre.able.AbstractMongoGenreableUpdateQuery;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

final class MongoAlbumUpdateQuery extends AbstractMongoGenreableUpdateQuery<Album, AlbumUpdateQuery,
        AlbumUpdateFilterQuery, AlbumMetaType> implements AlbumUpdateQuery {

    MongoAlbumUpdateQuery(final UpdateFilterQueryFactory<AlbumUpdateFilterQuery> filterQueryFactory,
                          final List<Bson> updates,
                          final AlbumMetaType properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    protected AlbumUpdateQuery self() {
        return this;
    }

    @Override
    public AlbumUpdateQuery addTracksDiscs(final Disc tracksDiscs) {
        return addAllTracksDiscs(ImmutableList.of(tracksDiscs));
    }

    @Override
    public AlbumUpdateQuery removeAllArtists(final Collection<Artist> artists) {
        final String propertyName = properties().artists().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(artists)));
    }

    @Override
    public AlbumUpdateQuery setReleaseDate(final LocalDate releaseDate) {
        final String propertyName = properties().release().date().propertyName();
        return appendUpdate(Updates.set(propertyName, releaseDate));
    }

    @Override
    public AlbumUpdateQuery addArtists(final Artist artists) {
        return addAllArtists(ImmutableList.of(artists));
    }

    @Override
    public AlbumUpdateQuery removeArtists(final Artist artists) {
        return removeAllArtists(ImmutableList.of(artists));
    }

    @Override
    public AlbumUpdateQuery setCover(final BiStream cover) {
        final String propertyName = properties().cover().propertyName();
        return appendUpdate(Updates.set(propertyName, cover));
    }

    @Override
    public AlbumUpdateQuery removeReleaseDate() {
        final String propertyName = properties().release().date().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public AlbumUpdateQuery addAllArtists(final Collection<Artist> artists) {
        final String propertyName = properties().artists().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(artists)));
    }

    @Override
    public AlbumUpdateQuery removeTracksDiscs(final Disc tracksDiscs) {
        return removeAllTracksDiscs(ImmutableList.of(tracksDiscs));
    }

    @Override
    public AlbumUpdateQuery removeAllTracksDiscs(final Collection<Disc> tracksDiscs) {
        final String propertyName = properties().tracks().discs().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(tracksDiscs)));
    }

    @Override
    public AlbumUpdateQuery addAllTracksDiscs(final Collection<Disc> tracksDiscs) {
        final String propertyName = properties().tracks().discs().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(tracksDiscs)));
    }
}
