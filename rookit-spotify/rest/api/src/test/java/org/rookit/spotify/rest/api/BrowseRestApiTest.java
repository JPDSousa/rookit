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

import com.google.common.collect.ImmutableMap;
import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

class BrowseRestApiTest extends RetrofitTest {

    @Test
    void testGetAvailableGenreSeeds() throws IOException {
        spotifyRestApi.browse().getAvailableGenreSeeds().execute();
    }

    @Test
    void testGetCategory() throws IOException {
        spotifyRestApi.browse().getCategory("dinner", CountryCode.SE, "sv_SE").execute();
    }

    @Test
    void testGetCategoryPlaylist() throws IOException {
        spotifyRestApi.browse().getCategoryPlaylists("dinner", CountryCode.SE, 10, 5).execute();
    }

    @Test
    void testGetCategories() throws IOException {
        spotifyRestApi.browse().getCategories(CountryCode.SE, "sv_SE", 10, 5).execute();
    }

    @Test
    void testGetFeaturedPlaylists() throws IOException {
        final Instant timestamp = LocalDateTime.of(2014, Month.OCTOBER, 23, 9, 0)
                .toInstant(ZoneOffset.UTC);
        spotifyRestApi.browse().getFeaturedPlaylists("sv_SE",
                CountryCode.SE,
                timestamp, 10, 5)
                .execute();
    }

    @Test
    void testGetNewReleases() throws IOException {
        spotifyRestApi.browse().getNewReleases(CountryCode.SE, 10, 5).execute();
    }

    @Test
    void testGetRecommendations() throws IOException {
        spotifyRestApi.browse().getRecommendations(10, CountryCode.ES,
                "4NHQUGzhtTLFvgF5SZesLK",
                "classical,country",
                "0c6xIDDpzE81m2q797ordA",
                ImmutableMap.of())
                .execute();
    }
}