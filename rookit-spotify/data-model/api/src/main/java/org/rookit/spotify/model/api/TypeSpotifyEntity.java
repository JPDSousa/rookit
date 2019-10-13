package org.rookit.spotify.model.api;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("javadoc")
public enum TypeSpotifyEntity {

    @SerializedName("album")
    ALBUM("album"),
    @SerializedName("track")
    TRACK("track"),
    @SerializedName("artist")
    ARTIST("artist"),
    @SerializedName("user")
    USER("user"),
    @SerializedName("playlist")
    PLAYLIST("playlist"),
    @SerializedName("category")
    CATEGORY("category");

    public final String type;

    TypeSpotifyEntity(final String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "TypeSpotifyEntity{" +
                "type='" + this.type + '\'' +
                "} " + super.toString();
    }
}
