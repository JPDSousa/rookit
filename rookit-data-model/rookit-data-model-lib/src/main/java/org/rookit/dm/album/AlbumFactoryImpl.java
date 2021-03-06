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

package org.rookit.dm.album;

import com.google.inject.Inject;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.genre.able.GenreableFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.factory.AlbumBiStream;
import org.rookit.dm.album.release.MutableReleaseFactory;
import org.rookit.dm.album.tracks.MutableAlbumTracksFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that provides methods for creating {@link Album} objects. This class
 * implements the factory pattern.
 *
 * @author Joao Sousa (jpd.sousa@campus.fct.unl.pt)
 *
 */
final class AlbumFactoryImpl implements AlbumFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AlbumFactoryImpl.class);

    private final Failsafe failsafe;
    private final AlbumFactory singleArtistAlbumFactory;
    private final AlbumFactory variousArtistsAlbumFactory;

    @Inject
    AlbumFactoryImpl(final Failsafe failsafe,
                     @AlbumBiStream final BiStreamFactory<Key> biStreamFactory,
                     final MutableAlbumTracksFactory albumTracksFactory,
                     final MutableReleaseFactory releaseFactory,
                     final OptionalUtils optionalUtils,
                     final GenreableFactory genreableFactory) {
        this.failsafe = failsafe;
        this.singleArtistAlbumFactory = new SingleArtistAlbumFactoryImpl(
                this.failsafe, releaseFactory,
                biStreamFactory,
                albumTracksFactory,
                genreableFactory,
                optionalUtils);
        this.variousArtistsAlbumFactory = new VariousArtistsAlbumFactoryImpl(failsafe,
                releaseFactory,
                biStreamFactory,
                albumTracksFactory,
                optionalUtils,
                genreableFactory);
    }

    @Override
    public Album create(final AlbumKey albumKey) {
        this.failsafe.checkArgument().isNotNull(logger, albumKey, "albumKey");
        
        final TypeAlbum albumType = albumKey.type();
        switch (albumType) {
            case ARTIST :
                return this.singleArtistAlbumFactory.create(albumKey);
            case VA :
                return this.variousArtistsAlbumFactory.create(albumKey);
        }
        return this.failsafe.handleException()
                .unsupportedOperation("Unrecognized album release: " + albumType);
    }

    @Override
    public Album createEmpty() {
        return this.failsafe.handleException()
                .unsupportedOperation("Cannot create an empty album");
    }

    @Override
    public String toString() {
        return "AlbumFactoryImpl{" +
                "injector=" + this.failsafe +
                ", singleArtistAlbumFactory=" + this.singleArtistAlbumFactory +
                ", variousArtistsAlbumFactory=" + this.variousArtistsAlbumFactory +
                "}";
    }
}
