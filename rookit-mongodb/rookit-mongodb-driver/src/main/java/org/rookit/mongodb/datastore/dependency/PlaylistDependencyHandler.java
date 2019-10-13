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
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.DependencyHandler;
import org.rookit.utils.primitive.VoidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

final class PlaylistDependencyHandler implements DependencyHandler<Playlist> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlaylistDependencyHandler.class);

    private final Failsafe failsafe;
    private final TrackDataStore trackDataStore;
    private final VoidUtils voidUtils;

    @Inject
    private PlaylistDependencyHandler(final Failsafe failsafe,
                                      final TrackDataStore trackDataStore,
                                      final VoidUtils voidUtils) {
        this.failsafe = failsafe;
        this.trackDataStore = trackDataStore;
        this.voidUtils = voidUtils;
    }

    @Override
    public CompletableFuture<Void> handleDependenciesFor(final Playlist playlist) {
        this.failsafe.checkArgument().isNotNull(logger, playlist, "playlist");
        if (playlist instanceof StaticPlaylist) {
            ((StaticPlaylist) playlist).tracks().forEach(this.trackDataStore::insert);
        }
        return this.voidUtils.completeVoid();
    }

    @Override
    public String toString() {
        return "PlaylistDependencyHandler{" +
                "injector=" + this.failsafe +
                ", trackDataStore=" + this.trackDataStore +
                ", voidUtils=" + this.voidUtils +
                "}";
    }
}
