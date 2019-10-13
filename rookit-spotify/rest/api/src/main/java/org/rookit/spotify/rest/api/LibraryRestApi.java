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
import org.rookit.spotify.model.api.album.SavedAlbum;
import org.rookit.spotify.model.api.page.Page;
import org.rookit.spotify.model.api.track.SavedTrack;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("HardcodedFileSeparator")
public interface LibraryRestApi {

    String BASE_URL = "/v1/me";

    @GET(BASE_URL + "/albums/contains")
    Call<List<Boolean>> checkSavedAlbum(@Query("ids") String ids);

    default Call<List<Boolean>> checkSavedAlbum(final Collection<String> ids) {
        return checkSavedAlbum(join(ids));
    }

    @GET(BASE_URL + "/tracks/contains")
    Call<List<Boolean>> checkSavedTracks(@Query("ids") String ids);

    default Call<List<Boolean>> checkSavedTracks(final Collection<String> ids) {
        return checkSavedTracks(join(ids));
    }

    @GET(BASE_URL + "/albums")
    Call<Page<SavedAlbum>> getSavedAlbums(@Query("market") CountryCode market,
                                          @Query("limit") Integer limit,
                                          @Query("offset") Integer offset);

    @GET(BASE_URL + "/tracks")
    Call<Page<SavedTrack>> getSavedTracks(@Query("market") CountryCode market,
                                          @Query("limit") Integer limit,
                                          @Query("offset") Integer offset);

    @DELETE(BASE_URL + "/albums")
    Call<Void> removeSavedAlbums(@Query("ids") String ids);

    default Call<Void> removeSavedAlbums(final Collection<String> ids) {
        return removeSavedAlbums(join(ids));
    }

    @DELETE(BASE_URL + "/tracks")
    Call<Void> removeSavedTracks(@Query("ids") String ids);

    default Call<Void> removeSavedTracks(final Collection<String> ids) {
        return removeSavedTracks(join(ids));
    }

    @PUT(BASE_URL + "/albums")
    Call<Void> saveAlbums(@Query("ids") String ids);

    default Call<Void> saveAlbums(final Collection<String> ids) {
        return saveAlbums(join(ids));
    }

    @PUT(BASE_URL + "/tracks")
    Call<Void> saveTracks(@Query("ids") String ids);

    default Call<Void> saveTracks(final Collection<String> ids) {
        return saveTracks(join(ids));
    }

    default String join(final Collection<String> items) {
        return String.join(",", items);
    }

}
