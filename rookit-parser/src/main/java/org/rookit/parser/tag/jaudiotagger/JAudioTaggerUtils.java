
package org.rookit.parser.tag.jaudiotagger;

import java.io.IOException;
import java.nio.file.Path;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.rookit.parser.utils.ParserValidator;

@SuppressWarnings("javadoc")
public final class JAudioTaggerUtils {

    private static final ParserValidator VALIDATOR = ParserValidator.getDefault();

    public static AudioFile readPath(final Path path) {
        try {
            return AudioFileIO.read(path.toFile());
        } catch (final CannotReadException | IOException | TagException | ReadOnlyFileException
                | InvalidAudioFrameException e) {
            return VALIDATOR.handleException().runtimeException(e);
        }
    }

    private JAudioTaggerUtils() {
    }

}
