package org.rookit.spotify.model.api.playlist;

import org.immutables.gson.Gson;
import org.immutables.value.Value;
import org.rookit.spotify.model.api.followers.Followers;
import org.rookit.spotify.model.api.page.Page;

@SuppressWarnings("javadoc")
@Value.Immutable
@Gson.TypeAdapters
public interface Playlist extends GenericPlaylist {

    String description();

    Followers followers();

    Page<PlaylistTrack> tracks();

}
