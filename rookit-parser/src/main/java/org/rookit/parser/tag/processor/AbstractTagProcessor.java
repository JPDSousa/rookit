
package org.rookit.parser.tag.processor;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.api.dm.artist.key.ImmutableArtistKey;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;
import org.rookit.parser.utils.ParserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Optional;

@SuppressWarnings("javadoc")
public abstract class AbstractTagProcessor implements TagProcessor {

    protected static final Validator VALIDATOR = ParserValidator.getDefault();

    /**
     * Logger for AbstractTagProcessor.
     */
    private static final Logger logger = VALIDATOR.getLogger(AbstractTagProcessor.class);

    private final ArtistFactory artistFactory;
    private final TagProcessorConfiguration config;

    protected AbstractTagProcessor(final ArtistFactory artistFactory, final TagProcessorConfiguration config) {
        this.artistFactory = artistFactory;
        this.config = config;
    }

    protected ArtistFactory getArtistFactory() {
        return this.artistFactory;
    }

    @Override
    public TagProcessorConfiguration getConfig() {
        return this.config;
    }

    private ArtistKey createArtistKey(final String name) {
        return ImmutableArtistKey.builder()
                .name(name)
                .build();
    }

    protected Collection<Artist> createArtists(final String tag) {
        return TagProcessorUtils.splitTag(tag)
                .filter(StringUtils::isNotBlank)
                .map(this::createArtistKey)
                .map(this.artistFactory::createGroupArtist)
                .select(Artist.class)
                .toImmutableSet();
    }

    protected Optional<LocalDate> parseDate(final CharSequence date) {
        if (this.config.extractDate()) {
            for (final String dateFormat : this.config.supportedDateFormats()) {
                try {
                    final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(dateFormat);
                    return Optional.of(LocalDate.parse(date, datePattern));
                } catch (final DateTimeParseException e) {
                    logger.trace("{} does not match {}", dateFormat, date);
                }
            }
        }
        logger.warn("Date format not recognized: {}", date);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("artistFactory", this.artistFactory)
                .add("config", this.config)
                .toString();
    }
}
