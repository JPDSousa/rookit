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

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@SuppressWarnings({"ExtendsUtilityClass", "JUnitTestMethodWithNoAssertions"})
class SearchRestApiTest extends RetrofitTest {

    @Test
    void testSearchArtists() throws IOException {
        spotifyRestApi.search().artists("Muse").execute();
        spotifyRestApi.search().artists("Muse", CountryCode.US).execute();
        spotifyRestApi.search().artists("Muse", CountryCode.US, 10, 5).execute();
    }

    @Test
    void testSearchAlbums() throws IOException {
        spotifyRestApi.search().albums("The Heist").execute();
        spotifyRestApi.search().albums("The Heist", CountryCode.US).execute();
        spotifyRestApi.search().albums("The Heist", CountryCode.US, 10, 5).execute();
    }

    @Test
    void testSearchTracks() throws IOException {
        spotifyRestApi.search().tracks("Tonight").execute();
        spotifyRestApi.search().tracks("Tonight", CountryCode.US).execute();
        spotifyRestApi.search().tracks("Tonight", CountryCode.US, 10, 5).execute();
    }

    @Test
    void testSearchPlaylists() throws IOException {
        spotifyRestApi.search().playlists("Best").execute();
        spotifyRestApi.search().playlists("Best", 10, 5).execute();
    }
}