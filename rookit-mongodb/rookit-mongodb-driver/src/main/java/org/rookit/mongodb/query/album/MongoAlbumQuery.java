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
package org.rookit.mongodb.query.album;

import com.google.common.collect.ImmutableSet;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumMetaType;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.storage.query.AlbumQuery;
import org.rookit.mongodb.query.MongoQueryResultFactory;
import org.rookit.mongodb.query.genre.AbstractMongoGenreableQuery;

import java.time.LocalDate;
import java.util.Collection;

final class MongoAlbumQuery extends AbstractMongoGenreableQuery<Album, AlbumQuery, AlbumMetaType>
        implements AlbumQuery {

    MongoAlbumQuery(final AlbumMetaType properties,
                    final Bson initialClause,
                    final MongoQueryResultFactory<Album> resultFactory) {
        super(properties, initialClause, resultFactory);
    }

    @Override
    public AlbumQuery withNoArtists(final Collection<Artist> absent) {
        return addQueryClause(Filters.nin(properties().artists().propertyName(), absent));
    }

    @Override
    public AlbumQuery withNoArtists(final Artist absent) {
        return withNoArtists(ImmutableSet.of(absent));
    }

    @Override
    public AlbumQuery withReleaseDateBetween(final LocalDate before, final LocalDate after) {
        final String property = properties().release().date().propertyName();

        return addQueryClause(Filters.and(
                Filters.gt(property, before),
                Filters.lt(property, after)
        ));
    }

    @Override
    public AlbumQuery withCover(final BiStream cover) {
        return addQueryClause(Filters.eq(properties().cover().propertyName(), cover));
    }

    @Override
    public AlbumQuery withTracksDiscs(final Collection<Disc> present) {
        final String property = properties().tracks().discs().propertyName();

        return addQueryClause(Filters.all(property, present));
    }

    @Override
    public AlbumQuery withReleaseDate(final LocalDate releaseDate) {
        final String property = properties().release().date().propertyName();

        return addQueryClause(Filters.eq(property, releaseDate));
    }

    @Override
    public AlbumQuery withTracksDiscs(final Disc present) {
        return withTracksDiscs(ImmutableSet.of(present));
    }

    @Override
    public AlbumQuery withAnyReleaseDate() {
        final String property = properties().release().date().propertyName();

        return addQueryClause(Filters.exists(property));
    }

    @Override
    public AlbumQuery withNoTracksDiscs(final Disc absent) {
        return withNoTracksDiscs(ImmutableSet.of(absent));
    }

    @Override
    public AlbumQuery withNoReleaseDate() {
        final String property = properties().release().date().propertyName();

        return addQueryClause(Filters.exists(property, false));
    }

    @Override
    public AlbumQuery withType(final TypeAlbum type) {
        return addQueryClause(Filters.eq(properties().type().propertyName(), type));
    }

    @Override
    public AlbumQuery withTitle(final String title) {
        return addQueryClause(Filters.eq(properties().title().propertyName(), title));
    }

    @Override
    public AlbumQuery withArtists(final Collection<Artist> present) {
        return addQueryClause(Filters.all(properties().artists().propertyName(), present));
    }

    @Override
    public AlbumQuery withArtists(final Artist present) {
        return withArtists(ImmutableSet.of(present));
    }

    @Override
    public AlbumQuery withReleaseDateAfter(final LocalDate releaseDate) {
        final String property = properties().release().date().propertyName();

        return addQueryClause(Filters.gt(property, releaseDate));
    }

    @Override
    public AlbumQuery withNoTracksDiscs(final Collection<Disc> absent) {
        final String property = properties().tracks().discs().propertyName();

        return addQueryClause(Filters.exists(property, false));
    }

    @Override
    public AlbumQuery withReleaseType(final TypeRelease releaseType) {
        final String property = properties().release().type().propertyName();

        return addQueryClause(Filters.eq(property, releaseType));
    }

    @Override
    public AlbumQuery withReleaseDateBefore(final LocalDate releaseDate) {
        final String property = properties().release().date().propertyName();

        return addQueryClause(Filters.lt(property, releaseDate));
    }

    @Override
    protected AlbumQuery self() {
        return this;
    }

    @Override
    public AlbumQuery toAlbumQuery() {
        return this;
    }

}
