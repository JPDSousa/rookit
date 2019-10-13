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

@SuppressWarnings({"AutoBoxing", "JUnitTestMethodWithNoAssertions"})
class LibraryRestApiTest extends RetrofitTest {

    @Test
    void testCheckSavedAlbum() throws IOException {
        spotifyRestApi.library().checkSavedAlbum("0pJJgBzj26qnE1nSQUxaB0,5ZAKzV4ZIa5Gt7z29OYHv0").execute();
    }

    @Test
    void testCheckSavedTracks() throws IOException {
        spotifyRestApi.library().checkSavedTracks("0udZHhCi7p1YzMlvI4fXoK,3SF5puV5eb6bgRSxBeMOk9").execute();
    }

    @Test
    void testGetSavedAlbums() throws IOException {
        spotifyRestApi.library().getSavedAlbums(CountryCode.AD, 10, 5).execute();
    }

    @Test
    void testGetSavedTracks() throws IOException {
        spotifyRestApi.library().getSavedTracks(CountryCode.ES, 10, 5).execute();
    }

    @Test
    void testRemoveSavedAlbums() {
        spotifyRestApi.library().removeSavedAlbums("6akEvsycLGftJxYudPjmqK,628oezqK2qfmCjC6eXNors");
    }

    @Test
    void testRemoveSavedTracks() {
        spotifyRestApi.library().removeSavedTracks("4iV5W9uYEdYUVa79Axb7Rh,1301WleyT98MSxVHPZCA6M");
    }

    @Test
    void testSaveAlbums() {
        spotifyRestApi.library().saveAlbums("4iV5W9uYEdYUVa79Axb7Rh,1301WleyT98MSxVHPZCA6M");
    }

    @Test
    void testSaveTracks() {
        spotifyRestApi.library().saveTracks("4iV5W9uYEdYUVa79Axb7Rh,1301WleyT98MSxVHPZCA6M");
    }
}