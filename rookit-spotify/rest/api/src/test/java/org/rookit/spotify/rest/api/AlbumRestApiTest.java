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
import org.rookit.spotify.model.api.album.Album;
import retrofit2.Call;

import java.io.IOException;

class AlbumRestApiTest extends RetrofitTest {

    @Test
    void testGetAlbum() throws IOException {
        final String id = "2BTZIqw0ntH9MvilQ3ewNY";
        final CountryCode code = CountryCode.GE;
        final Call<Album> albumRequest = spotifyRestApi.albums().getAlbum(id, code);

        albumRequest.execute();
    }

    @Test
    void testGetAlbums() throws IOException {
        final String ids = "382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc";
        final CountryCode code = CountryCode.ES;

        spotifyRestApi.albums().getAlbums(ids, code).execute();
    }

    @Test
    void testGetAlbumTracks() throws IOException {
        final String id = "6akEvsycLGftJxYudPjmqK";

        spotifyRestApi.albums().getAlbumTracks(id, 2, 0, CountryCode.AD).execute();
    }

}