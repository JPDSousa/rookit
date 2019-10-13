/**
 * Copyright (C) 2017 Spotify AB
 */
package org.rookit.spotify.model.api.track;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.time.LocalDate;

@Value.Immutable
@Gson.TypeAdapters
public interface LibraryTrack {

    LocalDate addedAt();

    Track track();

}
