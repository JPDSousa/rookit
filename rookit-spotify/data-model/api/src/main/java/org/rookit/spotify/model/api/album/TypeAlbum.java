package org.rookit.spotify.model.api.album;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("javadoc")
public enum TypeAlbum {

    @SerializedName(value = "album", alternate = "ALBUM")
    ALBUM("album"),
    @SerializedName(value = "single", alternate = "SINGLE")
    SINGLE("single"),
    @SerializedName(value = "appears_on", alternate = "APPEARS_ON")
    APPEARS_ON("appears_on"),
    @SerializedName(value = "compilation", alternate = "COMPILATION")
    COMPILATION("compilation");

    private final String type;

    TypeAlbum(final String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
