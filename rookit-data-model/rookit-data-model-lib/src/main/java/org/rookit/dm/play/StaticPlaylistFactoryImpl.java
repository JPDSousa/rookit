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
package org.rookit.dm.play;

import com.google.inject.Inject;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.genre.able.GenreableFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.play.factory.StaticPlaylistFactory;
import org.rookit.api.dm.play.key.PlaylistKey;
import org.rookit.dm.play.factory.PlaylistBiStream;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class StaticPlaylistFactoryImpl implements StaticPlaylistFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(StaticPlaylistFactoryImpl.class);

    private final Failsafe failsafe;
    private final BiStreamFactory<Key> biStreamFactory;
    private final GenreableFactory genreableFactory;

    @Inject
    private StaticPlaylistFactoryImpl(final GenreableFactory genreableFactory,
                                      @PlaylistBiStream final BiStreamFactory<Key> biStreamFactory,
                                      final Failsafe failsafe) {
        this.failsafe = failsafe;
        this.biStreamFactory = biStreamFactory;
        this.genreableFactory = genreableFactory;
    }

    @Override
    public StaticPlaylist create(final String name) {
        this.failsafe.checkArgument().string().isNotBlank(logger, name, "official");

        final BiStream image = this.biStreamFactory.createEmpty();
        final Genreable genreable = this.genreableFactory.create();
        return new StaticPlaylistImpl(genreable, name, image, this.failsafe);
    }

    @Override
    public StaticPlaylist create(final PlaylistKey key) {
        return create(key.name());
    }

    @Override
    public StaticPlaylist createEmpty() {
        return this.failsafe.handleException()
                .runtimeException("Create an empty static playlist is not allowed.");
    }

    @Override
    public String toString() {
        return "StaticPlaylistFactoryImpl{" +
                "injector=" + this.failsafe +
                ", biStreamFactory=" + this.biStreamFactory +
                ", genreableFactory=" + this.genreableFactory +
                "}";
    }
}
