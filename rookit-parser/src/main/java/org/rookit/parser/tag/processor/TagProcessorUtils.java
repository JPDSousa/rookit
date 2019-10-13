package org.rookit.parser.tag.processor;

import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.Logger;
import org.rookit.parser.utils.ParserValidator;
import org.rookit.utils.print.BaseFormats;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

@SuppressWarnings("javadoc")
public final class TagProcessorUtils {
    
    private static final Validator VALIDATOR = ParserValidator.getDefault();
    
    /**
     * Logger for TagProcessorUtils.
     */
    private static final Logger logger = VALIDATOR.getLogger(TagProcessorUtils.class);
    
    
    private TagProcessorUtils() {}
    
    public static OptionalInt parseTrackNumber(final String trackNumber) {
        if (Objects.isNull(trackNumber) || trackNumber.isEmpty()) {
            return OptionalInt.empty();
        }
        final int index = trackNumber.indexOf('/');

        final String trackNumberStr = (index > 0)
                ? trackNumber.substring(0, index)
                : trackNumber;
        return OptionalInt.of(Integer.parseInt(trackNumberStr));
    }
    
    public static StreamEx<String> splitTag(final String content) {
        if (Objects.isNull(content) || content.isEmpty()) {
            return StreamEx.empty();
        }
        
        return StreamEx.of(content.split(BaseFormats.TAG.getSeparator() + "|/"))
                .map(String::trim)
                .filter(StringUtils::isNotBlank);
    }
    
    public static Optional<String> prepareTitle(final String title) {
        return prepareString(title)
                .map(WordUtils::capitalizeFully);
    }
    
    public static Optional<String> prepareString(final String value) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(StringUtils::isNotBlank);
    }
    
    public static Optional<Duration> parseDuration(final String duration) {
        if (duration.length() == 4) {
            final short hour = Short.parseShort(duration.substring(0, 2));
            final short minute = Short.parseShort(duration.substring(2));

            return Optional.of(Duration.ofMinutes(hour * 60 + minute));
        }

        logger.warn("Could not parse duration: {}", duration);
        return Optional.empty();
    }

}
