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

import org.rookit.io.http.RetrofitApiConfig;
import retrofit2.Retrofit;

public final class RetrofitSpotifyRestApi implements SpotifyRestApi {

    public static SpotifyRestApi create(final RetrofitApiConfig config) {
        return new RetrofitSpotifyRestApi(config);
    }

    private final AlbumRestApi albumRestApi;
    private final ArtistRestApi artistRestApi;
    private final BrowseRestApi browseRestApi;
    private final PlaylistRestApi playlistRestApi;
    private final FollowRestApi followRestApi;
    private final LibraryRestApi libraryRestApi;
    private final PersonalizationRestApi personalizationRestApi;
    private final SearchRestApi searchRestApi;

    private RetrofitSpotifyRestApi(final RetrofitApiConfig config) {
        final Retrofit retrofit = config.retrofit();
        this.albumRestApi = retrofit.create(AlbumRestApi.class);
        this.artistRestApi = retrofit.create(ArtistRestApi.class);
        this.browseRestApi = retrofit.create(BrowseRestApi.class);
        this.playlistRestApi = retrofit.create(PlaylistRestApi.class);
        this.followRestApi = retrofit.create(FollowRestApi.class);
        this.libraryRestApi = retrofit.create(LibraryRestApi.class);
        this.personalizationRestApi = retrofit.create(PersonalizationRestApi.class);
        this.searchRestApi = retrofit.create(SearchRestApi.class);
    }

    @Override
    public AlbumRestApi albums() {
        return this.albumRestApi;
    }

    @Override
    public ArtistRestApi artists() {
        return this.artistRestApi;
    }

    @Override
    public BrowseRestApi browse() {
        return this.browseRestApi;
    }

    @Override
    public PlaylistRestApi playlists() {
        return this.playlistRestApi;
    }


    @Override
    public FollowRestApi follow() {
        return this.followRestApi;
    }

    @Override
    public LibraryRestApi library() {
        return this.libraryRestApi;
    }

    @Override
    public PersonalizationRestApi personalization() {
        return this.personalizationRestApi;
    }

    @Override
    public SearchRestApi search() {
        return this.searchRestApi;
    }

    @Override
    public String toString() {
        return "RetrofitSpotifyRestApi{" +
                "albumRestApi=" + this.albumRestApi +
                ", artistRestApi=" + this.artistRestApi +
                ", browseRestApi=" + this.browseRestApi +
                ", playlistRestApi=" + this.playlistRestApi +
                ", followRestApi=" + this.followRestApi +
                ", libraryRestApi=" + this.libraryRestApi +
                ", personalizationRestApi=" + this.personalizationRestApi +
                "}";
    }
}
