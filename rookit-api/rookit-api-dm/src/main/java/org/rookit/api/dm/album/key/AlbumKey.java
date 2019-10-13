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
package org.rookit.api.dm.album.key;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;

import java.util.Set;

@SuppressWarnings("javadoc")
@Immutable
@Value.Style(canBuild = "isBuildable")
public interface AlbumKey extends Key {

    /**
     * Returns the album release, as a {@link TypeAlbum} enumeration.
     *
     * @return album release
     */
    TypeAlbum type();

    /**
     * Returns the title of the album.
     *
     * @return title of this album.
     */
    String title();

    /**
     * Returns the release of release of the album.
     *
     * @return album's release release
     */
    TypeRelease releaseType();

    /**
     * Returns the artists that are authors of the album.
     * <p>
     * Do not confuse the withProperty returned as a withProperty of all artists involved in the
     * album. In order to get such result, one should iterate over all tracks on
     * the album and get the artists of each track.
     * </p>
     * <p>
     *     <b>Note: </b> This field's return release is bounded by Immutables (i.e. the library),
     *     as it does not recognize java.util.Collection as a collection release.
     * </p>
     *
     * @return withProperty of authors of this album
     */
    Set<Artist> artists();

}
