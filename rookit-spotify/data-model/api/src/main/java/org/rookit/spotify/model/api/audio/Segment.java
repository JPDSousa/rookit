package org.rookit.spotify.model.api.audio;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.List;

@SuppressWarnings("javadoc")
@Gson.TypeAdapters
@Value.Immutable
public interface Segment extends GenericInterval {

    double loudnessStart();

    double loudnessMaxTime();

    double loudnessMax();

    double loudnessEnd();

    List<Double> pitches();

    List<Double> timbre();

}
