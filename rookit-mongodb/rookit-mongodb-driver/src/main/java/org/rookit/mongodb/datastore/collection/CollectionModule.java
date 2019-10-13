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
package org.rookit.mongodb.datastore.collection;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.MusicianMetaType;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.DynamicPlaylistMetaType;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.StaticPlaylistMetaType;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.VersionTrackMetaType;
import org.rookit.mongodb.MongoCollectionNameResolver;

@SuppressWarnings("MethodMayBeStatic")
public final class CollectionModule extends AbstractModule {

    private static final Module MODULE = new CollectionModule();

    public static Module getModule() {
        return MODULE;
    }

    private CollectionModule() {}

    @Override
    protected void configure() {

    }

    private <T> MongoCollection<T> getCollection(final MongoDatabase database,
                                                 final MongoCollectionNameResolver resolver,
                                                 final Class<T> clazz) {
        return database.getCollection(resolver.getName(clazz), clazz);
    }

    @Provides
    @Singleton
    MongoCollection<Artist> artistCollection(final MongoDatabase database, final MongoCollectionNameResolver resolver) {
        return getCollection(database, resolver, Artist.class);
    }

    @Provides
    @Singleton
    MongoCollection<GroupArtist> groupArtistCollection(final MongoCollection<Artist> collection) {
        return collection.withDocumentClass(GroupArtist.class);
    }

    @Provides
    @Singleton
    MongoCollection<Musician> musicianCollection(final MongoCollection<Artist> collection,
                                                 final MusicianMetaType properties) {
        final Bson filter = Filters.eq(properties.type().propertyName(), TypeArtist.MUSICIAN);
        return new BaseFilterCollection<>(collection.withDocumentClass(Musician.class), filter);
    }

    @Provides
    @Singleton
    MongoCollection<Album> albumCollection(final MongoDatabase database, final MongoCollectionNameResolver resolver) {
        return getCollection(database, resolver, Album.class);
    }

    @Provides
    @Singleton
    MongoCollection<Genre> genreCollection(final MongoDatabase database, final MongoCollectionNameResolver resolver) {
        return getCollection(database, resolver, Genre.class);
    }

    @Provides
    @Singleton
    MongoCollection<Track> trackCollection(final MongoDatabase database, final MongoCollectionNameResolver resolver) {
        return getCollection(database, resolver, Track.class);
    }

    @Provides
    @Singleton
    MongoCollection<VersionTrack> versionTrackCollection(final MongoCollection<Track> collection,
                                                         final VersionTrackMetaType properties) {
        final Bson filter = Filters.eq(properties.type().propertyName(), TypeTrack.VERSION);
        return new BaseFilterCollection<>(collection.withDocumentClass(VersionTrack.class), filter);
    }

    @Provides
    @Singleton
    MongoCollection<Playlist> playlistCollection(final MongoDatabase database,
                                                 final MongoCollectionNameResolver resolver) {
        return getCollection(database, resolver, Playlist.class);
    }

    @Provides
    @Singleton
    MongoCollection<DynamicPlaylist> dynamicPlaylistCollection(final MongoCollection<Playlist> playlistCollection,
                                                               final DynamicPlaylistMetaType properties) {
        final Bson filter = Filters.eq(properties.type().propertyName(), TypePlaylist.DYNAMIC);
        return new BaseFilterCollection<>(playlistCollection.withDocumentClass(DynamicPlaylist.class), filter);
    }

    @Provides
    @Singleton
    MongoCollection<StaticPlaylist> staicPlaylistCollection(final MongoCollection<Playlist> playlistCollection,
                                                            final StaticPlaylistMetaType properties) {
        final Bson filter = Filters.eq(properties.type().propertyName(), TypePlaylist.STATIC);
        return new BaseFilterCollection<>(playlistCollection.withDocumentClass(StaticPlaylist.class), filter);
    }


}
