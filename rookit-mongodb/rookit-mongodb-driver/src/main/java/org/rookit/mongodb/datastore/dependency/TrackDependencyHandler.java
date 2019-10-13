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
package org.rookit.mongodb.datastore.dependency;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.artist.TrackArtists;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.DependencyHandler;
import org.rookit.utils.primitive.VoidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

final class TrackDependencyHandler implements DependencyHandler<Track> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TrackDependencyHandler.class);

    private final GenreDataStore genreDataStore;
    private final ArtistDataStore artistDataStore;
    private final Provider<TrackDataStore> trackDataStoreProvider;
    private final Failsafe failsafe;
    private final VoidUtils voidUtils;

    @Inject
    private TrackDependencyHandler(final GenreDataStore genreDataStore,
                           final ArtistDataStore artistDataStore,
                           final Provider<TrackDataStore> trackDataStore,
                           final Failsafe failsafe,
                           final VoidUtils voidUtils) {
        this.genreDataStore = genreDataStore;
        this.artistDataStore = artistDataStore;
        this.trackDataStoreProvider = trackDataStore;
        this.failsafe = failsafe;
        this.voidUtils = voidUtils;
    }

    @Override
    public CompletableFuture<Void> handleDependenciesFor(final Track track) {
        this.failsafe.checkArgument().isNotNull(logger, track, "track");

        handleArtists(track);

        return this.voidUtils.completeVoid();
    }

    private void handleArtists(final Track track) {
        final TrackArtists artists = track.artists();
        track.genres().forEach(this.genreDataStore::insert);
        artists.mainArtists().forEach(this.artistDataStore::insert);
        artists.features().forEach(this.artistDataStore::insert);
        artists.producers().forEach(this.artistDataStore::insert);
    }

    @Override
    public String toString() {
        return "TrackDependencyHandler{" +
                "genreDataStore=" + this.genreDataStore +
                ", artistDataStore=" + this.artistDataStore +
                ", trackDataStoreProvider=" + this.trackDataStoreProvider +
                ", injector=" + this.failsafe +
                ", voidUtils=" + this.voidUtils +
                "}";
    }
}
