package org.rookit.spotify.model.api.authentication;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@SuppressWarnings("javadoc")
@Gson.TypeAdapters
@Value.Immutable
public interface AuthorizationCodeCredentials extends Credentials {

    String refreshToken();

}
