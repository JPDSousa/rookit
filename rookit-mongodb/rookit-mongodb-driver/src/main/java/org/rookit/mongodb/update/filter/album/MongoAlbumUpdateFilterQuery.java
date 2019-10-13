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
package org.rookit.mongodb.update.filter.album;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.storage.filter.GenericAlbumFilter;
import org.rookit.api.storage.query.AlbumQuery;
import org.rookit.api.storage.update.filter.AlbumUpdateFilterQuery;
import org.rookit.mongodb.update.filter.genre.able.AbstractMongoGenreableUpdateFilterQuery;

import java.time.LocalDate;
import java.util.Collection;

final class MongoAlbumUpdateFilterQuery extends AbstractMongoGenreableUpdateFilterQuery<Album, AlbumQuery, 
        AlbumUpdateFilterQuery> implements AlbumUpdateFilterQuery {

    MongoAlbumUpdateFilterQuery(final MongoCollection<Album> collection, final Bson update, final AlbumQuery query) {
        super(collection, update, query);
    }

    @Override
    public AlbumUpdateFilterQuery withNoArtists(final Collection<Artist> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtists(absent));
    }

    @Override
    public AlbumUpdateFilterQuery withNoArtists(final Artist absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtists(absent));
    }

    @Override
    public AlbumUpdateFilterQuery withReleaseDateBetween(final LocalDate before, final LocalDate after) {
        return appendClause(mongoQuery -> mongoQuery.withReleaseDateBetween(before, after));
    }

    @Override
    public AlbumUpdateFilterQuery withCover(final BiStream cover) {
        return appendClause(mongoQuery -> mongoQuery.withCover(cover));
    }

    @Override
    public AlbumUpdateFilterQuery withTracksDiscs(final Collection<Disc> present) {
        return appendClause(mongoQuery -> mongoQuery.withTracksDiscs(present));
    }

    @Override
    public AlbumUpdateFilterQuery withReleaseDate(final LocalDate releaseDate) {
        return appendClause(mongoQuery -> mongoQuery.withReleaseDate(releaseDate));
    }

    @Override
    public AlbumUpdateFilterQuery withTracksDiscs(final Disc present) {
        return appendClause(mongoQuery -> mongoQuery.withTracksDiscs(present));
    }

    @Override
    public AlbumUpdateFilterQuery withAnyReleaseDate() {
        return appendClause(GenericAlbumFilter::withAnyReleaseDate);
    }

    @Override
    public AlbumUpdateFilterQuery withNoReleaseDate() {
        return appendClause(GenericAlbumFilter::withNoReleaseDate);
    }

    @Override
    public AlbumUpdateFilterQuery withNoTracksDiscs(final Disc absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoTracksDiscs(absent));
    }

    @Override
    public AlbumUpdateFilterQuery withType(final TypeAlbum type) {
        return appendClause(mongoQuery -> mongoQuery.withType(type));
    }

    @Override
    public AlbumUpdateFilterQuery withTitle(final String title) {
        return appendClause(mongoQuery -> mongoQuery.withTitle(title));
    }

    @Override
    public AlbumUpdateFilterQuery withArtists(final Collection<Artist> present) {
        return appendClause(mongoQuery -> mongoQuery.withArtists(present));
    }

    @Override
    public AlbumUpdateFilterQuery withArtists(final Artist present) {
        return appendClause(mongoQuery -> mongoQuery.withArtists(present));
    }

    @Override
    public AlbumUpdateFilterQuery withReleaseDateAfter(final LocalDate releaseDate) {
        return appendClause(mongoQuery -> mongoQuery.withReleaseDateAfter(releaseDate));
    }

    @Override
    public AlbumUpdateFilterQuery withNoTracksDiscs(final Collection<Disc> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoTracksDiscs(absent));
    }

    @Override
    public AlbumUpdateFilterQuery withReleaseType(final TypeRelease releaseType) {
        return appendClause(mongoQuery -> mongoQuery.withReleaseType(releaseType));
    }

    @Override
    public AlbumUpdateFilterQuery withReleaseDateBefore(final LocalDate releaseDate) {
        return appendClause(mongoQuery -> mongoQuery.withReleaseDateBefore(releaseDate));
    }

    @Override
    protected AlbumUpdateFilterQuery self() {
        return this;
    }
}
