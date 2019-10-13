package org.rookit.spotify.model.api.authentication;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@SuppressWarnings({"javadoc", "MarkerInterface"})
@Gson.TypeAdapters(fieldNamingStrategy = true)
@Value.Immutable
public interface ClientCredentials extends Credentials {

}
