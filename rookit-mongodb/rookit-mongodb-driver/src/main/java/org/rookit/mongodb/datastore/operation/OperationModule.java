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
package org.rookit.mongodb.datastore.operation;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.client.MongoCollection;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.query.AlbumQuery;
import org.rookit.api.storage.query.ArtistQuery;
import org.rookit.api.storage.query.GenreQuery;
import org.rookit.api.storage.query.PlaylistQuery;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.api.storage.update.AlbumUpdateQuery;
import org.rookit.api.storage.update.ArtistUpdateQuery;
import org.rookit.api.storage.update.GenreUpdateQuery;
import org.rookit.api.storage.update.PlaylistUpdateQuery;
import org.rookit.api.storage.update.TrackUpdateQuery;
import org.rookit.api.storage.update.filter.AlbumUpdateFilterQuery;
import org.rookit.api.storage.update.filter.ArtistUpdateFilterQuery;
import org.rookit.api.storage.update.filter.GenreUpdateFilterQuery;
import org.rookit.api.storage.update.filter.PlaylistUpdateFilterQuery;
import org.rookit.api.storage.update.filter.TrackUpdateFilterQuery;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.DependencyHandler;
import org.rookit.mongodb.datastore.MongoIndexManager;
import org.rookit.mongodb.datastore.MongoReadOperationManager;
import org.rookit.mongodb.datastore.MongoWriteOperationManager;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;
import org.rookit.mongodb.update.MongoUpdateQueryFactory;

@SuppressWarnings("MethodMayBeStatic")
public final class OperationModule extends AbstractModule {

    private static final Module MODULE = new OperationModule();

    public static Module getModule() {
        return MODULE;
    }

    private OperationModule() {}

    @Override
    protected void configure() {

    }

    @Singleton
    @Provides
    MongoWriteOperationManager<Album, AlbumUpdateQuery, AlbumUpdateFilterQuery>
    albumWriteOperation(final Failsafe failsafe,
                        final DependencyHandler<Album> dependencyHandler,
                        final MongoCollection<Album> collection,
                        final MongoIndexManager<Album> indexManager,
                        final MongoUpdateQueryFactory<AlbumUpdateQuery, AlbumUpdateFilterQuery> updateFactory) {
        return new BaseWriteOperationManager<>(failsafe, dependencyHandler, collection, indexManager, updateFactory);
    }

    @Singleton
    @Provides
    MongoWriteOperationManager<Artist, ArtistUpdateQuery, ArtistUpdateFilterQuery>
    artistWriteOperation(final Failsafe failsafe,
                         final DependencyHandler<Artist> dependencyHandler,
                         final MongoCollection<Artist> collection,
                         final MongoIndexManager<Artist> indexManager,
                         final MongoUpdateQueryFactory<ArtistUpdateQuery, ArtistUpdateFilterQuery> updateFactory) {
        return new BaseWriteOperationManager<>(failsafe, dependencyHandler, collection, indexManager, updateFactory);
    }

    @Singleton
    @Provides
    MongoWriteOperationManager<Genre, GenreUpdateQuery, GenreUpdateFilterQuery>
    genreWriteOperation(final Failsafe failsafe,
                        final DependencyHandler<Genre> dependencyHandler,
                        final MongoCollection<Genre> collection,
                        final MongoIndexManager<Genre> indexManager,
                        final MongoUpdateQueryFactory<GenreUpdateQuery, GenreUpdateFilterQuery> updateFactory) {
        return new BaseWriteOperationManager<>(failsafe, dependencyHandler, collection, indexManager, updateFactory);
    }

    @Singleton
    @Provides
    MongoWriteOperationManager<Track, TrackUpdateQuery, TrackUpdateFilterQuery>
    trackWriteOperation(final Failsafe failsafe,
                        final DependencyHandler<Track> dependencyHandler,
                        final MongoCollection<Track> collection,
                        final MongoIndexManager<Track> indexManager,
                        final MongoUpdateQueryFactory<TrackUpdateQuery, TrackUpdateFilterQuery> updateFactory) {
        return new BaseWriteOperationManager<>(failsafe, dependencyHandler, collection, indexManager, updateFactory);
    }

    @Singleton
    @Provides
    MongoWriteOperationManager<Playlist, PlaylistUpdateQuery, PlaylistUpdateFilterQuery>
    playlistWriteOperation(final Failsafe failsafe,
                           final DependencyHandler<Playlist> dependencyHandler,
                           final MongoCollection<Playlist> collection,
                           final MongoIndexManager<Playlist> indexManager,
                           final MongoUpdateQueryFactory<PlaylistUpdateQuery, PlaylistUpdateFilterQuery> updateFactory) {
        return new BaseWriteOperationManager<>(failsafe, dependencyHandler, collection, indexManager, updateFactory);
    }

    @Singleton
    @Provides
    MongoReadOperationManager<Album, AlbumQuery>
    albumReadOperation(final MongoQueryResultFactory<Album> resultFactory,
                       final MongoQueryFactory<Album, AlbumQuery> queryFactory) {
        return new BaseReadOperationManager<>(resultFactory, queryFactory);
    }

    @Singleton
    @Provides
    MongoReadOperationManager<Artist, ArtistQuery>
    artistReadOperation(final MongoQueryResultFactory<Artist> resultFactory,
                       final MongoQueryFactory<Artist, ArtistQuery> queryFactory) {
        return new BaseReadOperationManager<>(resultFactory, queryFactory);
    }

    @Singleton
    @Provides
    MongoReadOperationManager<Genre, GenreQuery>
    genreReadOperation(final MongoQueryResultFactory<Genre> resultFactory,
                       final MongoQueryFactory<Genre, GenreQuery> queryFactory) {
        return new BaseReadOperationManager<>(resultFactory, queryFactory);
    }

    @Singleton
    @Provides
    MongoReadOperationManager<Track, TrackQuery>
    trackReadOperation(final MongoQueryResultFactory<Track> resultFactory,
                       final MongoQueryFactory<Track, TrackQuery> queryFactory) {
        return new BaseReadOperationManager<>(resultFactory, queryFactory);
    }

    @Singleton
    @Provides
    MongoReadOperationManager<Playlist, PlaylistQuery>
    playlistReadOperation(final MongoQueryResultFactory<Playlist> resultFactory,
                       final MongoQueryFactory<Playlist, PlaylistQuery> queryFactory) {
        return new BaseReadOperationManager<>(resultFactory, queryFactory);
    }

}
