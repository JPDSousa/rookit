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

import org.rookit.spotify.model.api.artist.FollowedArtists;
import org.rookit.spotify.model.api.followers.TypeFollow;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("HardcodedFileSeparator")
public interface FollowRestApi {

    String BASE_URL = "/v1";

    @GET(BASE_URL + "/me/following/contains")
    Call<List<Boolean>> checkIfUserFollows(@Query("type") TypeFollow type, @Query("ids") String ids);

    default Call<List<Boolean>> checkIfUserFollows(final TypeFollow type, final Collection<String> ids) {
        return checkIfUserFollows(type, join(ids));
    }

    @GET(BASE_URL + "/playlists/{playlist_id}/followers/contains")
    Call<List<Boolean>> checkIfUserFollowsPlaylist(@Path("playlist_id") String playlistId,
                                                   @Query("ids") String userIds);

    default Call<List<Boolean>> checkIfUserFollowsPlaylist(final String playlistId,
                                                     final Collection<String> userIds) {
        return checkIfUserFollowsPlaylist(playlistId, join(userIds));
    }

    @PUT(BASE_URL + "/me/following")
    Call<Void> follow(@Query("type") TypeFollow typeFollow,
                      @Query("ids") String ids);

    default Call<Void> follow(final TypeFollow typeFollow,
                              final Collection<String> ids) {
        return follow(typeFollow, join(ids));
    }

    // TODO missing body param
    @PUT(BASE_URL + "/playlists/{playlist_id}/followers")
    Call<Void> followPlaylist(@Path("playlist_id") String id);

    @GET(BASE_URL + "/me/following?type=artist")
    Call<FollowedArtists> getFollowedArtists(@Query("limit") Integer limit,
                                             @Query("after") String lastId);

    @GET(BASE_URL + "/me/following?type=artist")
    Call<FollowedArtists> getFollowedArtists(@Query("limit") Integer limit);

    @DELETE(BASE_URL + "/me/following")
    Call<Void> unfollow(@Query("type") TypeFollow type, @Query("ids") String ids);

    default Call<Void> unfollow(final TypeFollow type, final Collection<String> ids) {
        return unfollow(type, join(ids));
    }

    @DELETE(BASE_URL + "/playlists/{playlist_id}/followers")
    Call<Void> unfollowPlaylist(@Path("playlist_id") String playlistId);

    default String join(final Collection<String> items) {
        return String.join(",", items);
    }
}
