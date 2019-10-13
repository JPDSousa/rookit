package org.rookit.spotify.model.api.page;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@Gson.TypeAdapters
public interface Page<T> extends GenericPage<T> {

    int offset();

    Optional<String> previous();

}
