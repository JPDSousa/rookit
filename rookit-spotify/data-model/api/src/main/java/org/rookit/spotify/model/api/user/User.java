package org.rookit.spotify.model.api.user;

import org.immutables.gson.Gson;
import org.immutables.value.Value;
import org.rookit.spotify.model.api.followers.Followers;

@SuppressWarnings("javadoc")
@Value.Immutable
@Gson.TypeAdapters(fieldNamingStrategy = true)
public interface User extends GenericUser {

    String email();

    Product product();

    String country();

    Followers followers();

    String birthdate();

}
