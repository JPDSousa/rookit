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
package org.rookit.mongodb.query.result;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.mongodb.query.MongoQueryResultFactory;

@SuppressWarnings("MethodMayBeStatic")
public final class QueryResultModule extends AbstractModule {

    private static final Module MODULE = new QueryResultModule();

    public static Module getModule() {
        return MODULE;
    }

    private QueryResultModule() {}

    @Override
    protected void configure() {

    }

    @Singleton
    @Provides
    MongoQueryResultFactory<Track> trackQueryResult(final MongoCollection<Track> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Provides
    @Singleton
    MongoQueryResultFactory<VersionTrack> versionTrackQueryResult(final MongoCollection<VersionTrack> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Singleton
    @Provides
    MongoQueryResultFactory<Artist> artistQueryResult(final MongoCollection<Artist> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Provides
    @Singleton
    MongoQueryResultFactory<GroupArtist> groupArtistQueryResult(final MongoCollection<GroupArtist> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Provides
    @Singleton
    MongoQueryResultFactory<Musician> musicianQueryResult(final MongoCollection<Musician> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Singleton
    @Provides
    MongoQueryResultFactory<Album> albumQueryResult(final MongoCollection<Album> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Singleton
    @Provides
    MongoQueryResultFactory<Genre> genreQueryResult(final MongoCollection<Genre> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Singleton
    @Provides
    MongoQueryResultFactory<Playlist> playlistQueryResult(final MongoCollection<Playlist> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Provides
    @Singleton
    MongoQueryResultFactory<DynamicPlaylist> dynamicPlaylistQueryResult(
            final MongoCollection<DynamicPlaylist> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }

    @Provides
    @Singleton
    MongoQueryResultFactory<StaticPlaylist> staticPlaylistQueryResult(
            final MongoCollection<StaticPlaylist> collection) {
        return new MongoQueryResultFactoryImpl<>(collection);
    }
}
