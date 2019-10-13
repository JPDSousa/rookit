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
package org.rookit.mongodb.query.ops;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.query.AlbumQuery;
import org.rookit.api.storage.query.ArtistQuery;
import org.rookit.api.storage.query.GenreQuery;
import org.rookit.api.storage.query.PlaylistQuery;
import org.rookit.storage.query.QueryOps;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.query.MongoQueryFactory;

@SuppressWarnings("MethodMayBeStatic")
public final class QueryOpsModule extends AbstractModule {

    private static final Module MODULE = new QueryOpsModule();

    public static Module getModule() {
        return MODULE;
    }

    private QueryOpsModule() {}

    @Override
    protected void configure() {

    }

    @Singleton
    @Provides
    QueryOps<Track, TrackQuery> trackQueryOps(final Failsafe failsafe,
                                              final MongoQueryFactory<Track, TrackQuery> factory) {
        return new MongoQueryOps<>(factory, failsafe);
    }

    @Singleton
    @Provides
    QueryOps<Artist, ArtistQuery> artistQueryOps(final Failsafe failsafe,
                                                 final MongoQueryFactory<Artist, ArtistQuery> factory) {
        return new MongoQueryOps<>(factory, failsafe);
    }

    @Singleton
    @Provides
    QueryOps<Album, AlbumQuery> albumQueryOps(final Failsafe failsafe,
                                              final MongoQueryFactory<Album, AlbumQuery> factory) {
        return new MongoQueryOps<>(factory, failsafe);
    }

    @Singleton
    @Provides
    QueryOps<Genre, GenreQuery> genreQueryOps(final Failsafe failsafe,
                                              final MongoQueryFactory<Genre, GenreQuery> factory) {
        return new MongoQueryOps<>(factory, failsafe);
    }

    @Singleton
    @Provides
    QueryOps<Playlist, PlaylistQuery> playlistQueryOps(final Failsafe failsafe,
                                                       final MongoQueryFactory<Playlist, PlaylistQuery> factory) {
        return new MongoQueryOps<>(factory, failsafe);
    }
}
