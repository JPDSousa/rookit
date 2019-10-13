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
package org.rookit.api.dm.album;

/**
 * Enumeration that represents the various types of album (in terms of
 * configurations). Each album release must have a class associated.
 *
 * <p>
 * note: do not confuse this enumeration with {@link TypeRelease}.
 *
 */
public enum TypeAlbum {

    /**
     * Album released by a defined withProperty of artists. The album may have other
     * artists associated, but will requires a non empty withProperty of authors.
     */
    ARTIST("Artist Album"),
    /**
     * Various Artists album. This release of album is not associated with any
     * particular artist.
     */
    VA("VA");

    private final String name;

    TypeAlbum(final String name) {
        this.name = name;
    }

    /**
     * Returns the official of the release
     * 
     * @return official of the album release
     */
    public String getName() {
        return this.name;
    }

}
