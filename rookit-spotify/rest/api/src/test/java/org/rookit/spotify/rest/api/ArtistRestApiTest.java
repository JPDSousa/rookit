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

package org.rookit.spotify.rest.api;

import com.google.common.collect.ImmutableList;
import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import org.rookit.spotify.model.api.album.TypeAlbum;

import java.io.IOException;

class ArtistRestApiTest extends RetrofitTest {

    @Test
    void testGetArtist() throws IOException {
        spotifyRestApi.artists().getArtist("2BTZIqw0ntH9MvilQ3ewNY").execute();
    }

    @Test
    void testGetArtists() throws IOException {
        spotifyRestApi.artists().getArtists("0oSGxfWSnnOXhD2fKuz2Gy,3dBVyJ7JuOMt4GE9607Qin").execute();
    }

    @Test
    void testGetArtistRelatedArtists() throws IOException {
        spotifyRestApi.artists().getArtistRelatedArtists("43ZHCT0cAZBISjO8DG9PnE").execute();
    }

    @Test
    void getAlbumsForArtist() throws IOException {
        spotifyRestApi.artists().getAlbumsForArtist("1vCWHaC5f2uS3yhpwWbIA6",
                TypeAlbum.APPEARS_ON.type(),
                CountryCode.ES,
                2,
                0)
                .execute();
    }

    @Test
    void getAlbumsForArtistCollection() throws IOException {
        spotifyRestApi.artists().getAlbumsForArtist("1vCWHaC5f2uS3yhpwWbIA6",
                ImmutableList.of(TypeAlbum.APPEARS_ON),
                CountryCode.ES,
                2,
                0)
                .execute();
    }

    @Test
    void getTopTracksForArtist() {
        spotifyRestApi.artists().getTopTracksForArtist("43ZHCT0cAZBISjO8DG9PnE", CountryCode.SE);
    }
}