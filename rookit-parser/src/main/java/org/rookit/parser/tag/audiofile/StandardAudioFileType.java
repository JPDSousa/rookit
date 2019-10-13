package org.rookit.parser.tag.audiofile;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("javadoc")
public enum StandardAudioFileType implements AudioFileType {

    MPEG("MPG"),
    MPEG1_2_LAYER_I("MPG1"),
    MPEG1_2_LAYER_II("MPG2"),
    MPEG1_2_LAYER_III("MPG3"),
    MPEG2_5("MPG2.5"),
    AAC("MPGAAC"),
    VQF("VQF"),
    PCM("PCM");

    private final String code;

    private StandardAudioFileType(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static Optional<AudioFileType> parseByCode(final String code) {
        if (Objects.isNull(code)) {
            return Optional.empty();
        }

        return Arrays.stream(StandardAudioFileType.values())
                .filter(type -> type.getCode().equalsIgnoreCase(code))
                .map(AudioFileType.class::cast)
                .findFirst();
    }

}
