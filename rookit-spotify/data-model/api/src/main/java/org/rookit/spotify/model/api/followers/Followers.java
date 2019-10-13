package org.rookit.spotify.model.api.followers;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.net.URI;
import java.net.URL;
import java.util.Optional;

@SuppressWarnings("javadoc")
@Value.Immutable
@Gson.TypeAdapters
public interface Followers {

    Optional<URL> href();

    int total();

}
