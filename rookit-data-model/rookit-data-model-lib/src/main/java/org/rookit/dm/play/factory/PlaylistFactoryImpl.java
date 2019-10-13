/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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

package org.rookit.dm.play.factory;

import com.google.inject.Inject;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.play.factory.DynamicPlaylistFactory;
import org.rookit.api.dm.play.factory.PlaylistFactory;
import org.rookit.api.dm.play.factory.StaticPlaylistFactory;
import org.rookit.api.dm.play.key.PlaylistKey;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("javadoc")
final class PlaylistFactoryImpl implements PlaylistFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlaylistFactoryImpl.class);

    private final Failsafe failsafe;
    private final StaticPlaylistFactory staticPlaylistFactory;
    private final DynamicPlaylistFactory dynamicPlaylistFactory;

    @Inject
    private PlaylistFactoryImpl(final Failsafe failsafe, 
                                final StaticPlaylistFactory staticFactory,
                                final DynamicPlaylistFactory dynamicFactory) {
        this.failsafe = failsafe;
        this.staticPlaylistFactory = staticFactory;
        this.dynamicPlaylistFactory = dynamicFactory;
    }

    @Override
    public Playlist create(final PlaylistKey key) {
        this.failsafe.checkArgument().isNotNull(logger, key, "key");

        final TypePlaylist playlistType = key.type();
        switch (playlistType) {
            case DYNAMIC :
                return this.dynamicPlaylistFactory.create(key);
            case STATIC :
                return this.staticPlaylistFactory.create(key);
        }
        return this.failsafe.handleException().runtimeException("Invalida release: " + playlistType);
    }

    @Override
    public Playlist createEmpty() {
        return this.failsafe.handleException().unsupportedOperation("Cannot create an empty playlist");
    }


    @Override
    public String toString() {
        return "PlaylistFactoryImpl{" +
                "injector=" + this.failsafe +
                ", staticPlaylistFactory=" + this.staticPlaylistFactory +
                ", dynamicPlaylistFactory=" + this.dynamicPlaylistFactory +
                "}";
    }
}
