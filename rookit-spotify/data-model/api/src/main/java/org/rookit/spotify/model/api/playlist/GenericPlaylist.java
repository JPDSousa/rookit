package org.rookit.spotify.model.api.playlist;

import com.google.gson.annotations.SerializedName;
import org.rookit.spotify.model.api.SpotifyEntity;
import org.rookit.spotify.model.api.image.ImageHolder;
import org.rookit.spotify.model.api.user.SimpleUser;

import java.util.Optional;

public interface GenericPlaylist extends ImageHolder, SpotifyEntity {

    boolean collaborative();

    SimpleUser owner();

    String name();

    @SerializedName("public")
    Optional<Boolean> publicAccess();

}
