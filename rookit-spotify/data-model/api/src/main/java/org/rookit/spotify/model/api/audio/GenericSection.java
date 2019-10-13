package org.rookit.spotify.model.api.audio;

@SuppressWarnings("javadoc")
public interface GenericSection extends GenericInterval {

    double loudness();

    double tempo();

    double tempoConfidence();

    int key();

    double keyConfidence();

    int mode();

    double modeConfidence();

    int timeSignature();

    double timeSignatureConfidence();

}
