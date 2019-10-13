package org.rookit.spotify.model.api.audio;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@SuppressWarnings("javadoc")
@Gson.TypeAdapters
@Value.Immutable
public interface Metadata {

    String analyzerVersion();

    String platform();

    String detailedStatus();

    int statusCode();

    long timestamp();

    double analysisTime();

    String inputProcess();

}
