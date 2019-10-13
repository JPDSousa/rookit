package org.rookit.spotify.model.api.playlist;

import org.immutables.gson.Gson;
import org.immutables.value.Value;
import org.rookit.spotify.model.api.track.Track;
import org.rookit.spotify.model.api.user.User;

import java.time.LocalDate;
import java.util.Optional;

@SuppressWarnings("javadoc")
@Value.Immutable
@Gson.TypeAdapters
public interface PlaylistTrack {

    Optional<LocalDate> addedAt();

    Optional<User> addedBy();

    Track track();

}
