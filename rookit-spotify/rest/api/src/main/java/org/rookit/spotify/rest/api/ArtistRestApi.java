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

import com.google.common.collect.Collections2;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.spotify.model.api.album.SimpleAlbum;
import org.rookit.spotify.model.api.album.TypeAlbum;
import org.rookit.spotify.model.api.artist.Artist;
import org.rookit.spotify.model.api.artist.Artists;
import org.rookit.spotify.model.api.page.Page;
import org.rookit.spotify.model.api.track.Track;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Collection;

@SuppressWarnings("HardcodedFileSeparator")
public interface ArtistRestApi {

    String BASE_URL = "/v1/artists";

    @GET(BASE_URL + "/{id}")
    Call<Artist> getArtist(@Path("id") String id);

    @GET(BASE_URL)
    Call<Artists> getArtists(@Query("ids") String ids);

    default Call<Artists> getArtists(final Collection<String> ids) {
        return getArtists(join(ids));
    }

    @GET(BASE_URL + "/{id}/related-artists")
    Call<Artists> getArtistRelatedArtists(@Path("id") String artistId);

    @GET(BASE_URL + "/{id}/albums")
    Call<Page<SimpleAlbum>> getAlbumsForArtist(@Path("id") String artistId,
                                               @Query("include_groups") String albumTypes,
                                               @Query("market") CountryCode market,
                                               @Query("limit") Integer limit,
                                               @Query("offset") Integer offset);

    default Call<Page<SimpleAlbum>> getAlbumsForArtist(final String artistId,
                                                       final Collection<TypeAlbum> albumTypes,
                                                       final CountryCode market,
                                                       final Integer limit,
                                                       final Integer offset) {
        return getAlbumsForArtist(artistId, join(albumTypes), market, limit, offset);
    }

    @GET(BASE_URL + "/{id}/top-tracks")
    Call<Collection<Track>> getTopTracksForArtist(@Path("id") String artistId,
                                                  @Query("market") CountryCode countryCode);

    default String join(final Collection<?> ids) {
        final Collection<String> asStrings = Collections2.transform(ids, Object::toString);
        return String.join(",", asStrings);
    }
}
