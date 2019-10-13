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
package org.rookit.mongodb.datastore.index;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumMetaType;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.ArtistMetaType;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.GenreMetaType;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.PlaylistMetaType;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackMetaType;
import org.rookit.mongodb.datastore.MongoIndex;
import org.rookit.mongodb.datastore.MongoIndexManager;
import org.rookit.mongodb.datastore.index.album.AlbumIndexModule;
import org.rookit.mongodb.datastore.index.artist.ArtistIndexModule;
import org.rookit.mongodb.datastore.index.genre.GenreIndexModule;
import org.rookit.mongodb.datastore.index.playlist.PlaylistIndexModule;
import org.rookit.mongodb.datastore.index.track.TrackIndexModule;

import java.util.Set;

@SuppressWarnings({"MethodMayBeStatic", "TypeMayBeWeakened"})
public final class MongoIndexModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new MongoIndexModule(),
            AlbumIndexModule.getModule(),
            ArtistIndexModule.getModule(),
            GenreIndexModule.getModule(),
            TrackIndexModule.getModule(),
            PlaylistIndexModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private MongoIndexModule() {}

    @Override
    protected void configure() { }

    @Provides
    @Singleton
    MongoIndexManager<Album> albumManager(final Set<MongoIndex<Album>> indices,
                                          final AlbumMetaType properties) {
        return new GenericIndexManager<>(properties.id().propertyName(), indices);
    }

    @Provides
    @Singleton
    MongoIndexManager<Artist> artistManager(final Set<MongoIndex<Artist>> indices,
                                            final ArtistMetaType properties) {
        return new GenericIndexManager<>(properties.id().propertyName(), indices);
    }

    @Provides
    @Singleton
    MongoIndexManager<Genre> genreManager(final Set<MongoIndex<Genre>> indices,
                                          final GenreMetaType properties) {
        return new GenericIndexManager<>(properties.id().propertyName(), indices);
    }

    @Provides
    @Singleton
    MongoIndexManager<Track> trackManager(final Set<MongoIndex<Track>> indices,
                                          final TrackMetaType properties) {
        return new GenericIndexManager<>(properties.id().propertyName(), indices);
    }
    @Provides
    @Singleton
    MongoIndexManager<Playlist> playlistManager(final Set<MongoIndex<Playlist>> indices,
                                                final PlaylistMetaType properties) {
        return new GenericIndexManager<>(properties.id().propertyName(), indices);
    }

}
