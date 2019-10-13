package org.rookit.spotify.model.api.image;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.OptionalInt;

@SuppressWarnings("javadoc")
@Value.Immutable
@Gson.TypeAdapters
public interface Image {

    OptionalInt width();

    String url();

    OptionalInt height();

}
