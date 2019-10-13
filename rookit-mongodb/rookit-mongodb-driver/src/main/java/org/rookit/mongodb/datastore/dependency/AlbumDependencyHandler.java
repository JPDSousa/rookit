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
import org.rookit.api.dm.album.Album;
import org.rookit.mongodb.DependencyHandler;
import org.rookit.utils.primitive.VoidUtils;

import java.util.concurrent.CompletableFuture;

final class AlbumDependencyHandler implements DependencyHandler<Album> {

    private final TrackDataStore trackDataStore;
    private final ArtistDataStore artistDataStore;
    private final GenreDataStore genreDataStore;
    private final VoidUtils voidUtils;

    @Inject
    private AlbumDependencyHandler(final TrackDataStore trackDataStore,
                                   final ArtistDataStore artistDataStore,
                                   final GenreDataStore genreDataStore,
                                   final VoidUtils voidUtils) {
        this.trackDataStore = trackDataStore;
        this.artistDataStore = artistDataStore;
        this.genreDataStore = genreDataStore;
        this.voidUtils = voidUtils;
    }

    @Override
    public CompletableFuture<Void> handleDependenciesFor(final Album element) {
        element.tracks().forEach(this.trackDataStore::insert);
        element.artists().forEach(this.artistDataStore::insert);
        element.genres().forEach(this.genreDataStore::insert);
        return this.voidUtils.completeVoid();
    }

    @Override
    public String toString() {
        return "AlbumDependencyHandler{" +
                "trackDataStore=" + this.trackDataStore +
                ", artistDataStore=" + this.artistDataStore +
                ", genreDataStore=" + this.genreDataStore +
                ", voidUtils=" + this.voidUtils +
                "}";
    }
}