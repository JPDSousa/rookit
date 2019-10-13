package org.rookit.spotify.model.api.audio;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@SuppressWarnings("javadoc")
@Gson.TypeAdapters
@Value.Immutable
public interface TrackAnalysis extends GenericSection {

    int numSamples();

    String sampleMd5();

    int offsetSeconds();

    int windowSeconds();

    int analysisSampleRate();

    int analysisChannels();

    double endOfFadeIn();

    double startOfFadeOut();

    String codeString();

    double codeVersion();

    String echoPrintString();

    double echoPrintVersion();

    String synchString();

    double synchVersion();

    String rythmString();

    double rythmVersion();

}
