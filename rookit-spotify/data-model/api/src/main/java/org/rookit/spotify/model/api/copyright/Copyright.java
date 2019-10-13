package org.rookit.spotify.model.api.copyright;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public interface Copyright {

    String text();

    String type();

}
