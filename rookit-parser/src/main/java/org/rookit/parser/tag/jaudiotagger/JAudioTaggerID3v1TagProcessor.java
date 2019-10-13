
package org.rookit.parser.tag.jaudiotagger;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.parser.tag.processor.id3v1.AbstractID3V1TagProcessor;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.rookit.parser.tag.processor.TagProcessorUtils.*;

class JAudioTaggerID3v1TagProcessor extends AbstractID3V1TagProcessor {

    private static final Logger logger = VALIDATOR.getLogger(JAudioTaggerID3v1TagProcessor.class);

    private final ID3v1Tag tags;
    private final GenreFactory genreFactory;

    JAudioTaggerID3v1TagProcessor(final ArtistFactory artistFactory,
                                  final TagProcessorConfiguration configuration,
                                  final ID3v1Tag tags,
                                  final GenreFactory genreFactory) {
        super(artistFactory, configuration);
        this.tags = tags;
        this.genreFactory = genreFactory;
    }

    @Override
    public Optional<String> getAlbumTitle() {
        return prepareTitle(this.tags.getFirst(FieldKey.ALBUM));
    }

    @Override
    public Collection<Artist> getArtists() {
        return createArtists(this.tags.getFirst(FieldKey.ARTIST));
    }

    @Override
    public Optional<String> getComment() {
        return prepareString(this.tags.getFirstComment());
    }

    @Override
    public Collection<Genre> genres() {
        return splitTag(this.tags.getFirstGenre())
                .filter(StringUtils::isNotBlank)
                .map(this.genreFactory::createGenre)
                .toImmutableSet();
    }

    @Override
    public Optional<String> getTitle() {
        return prepareTitle(this.tags.getFirst(FieldKey.TITLE));
    }

    @Override
    public Optional<LocalDate> getYear() {
        return prepareString(this.tags.getFirst(FieldKey.YEAR))
                .flatMap(this::parseDate);
    }


    @Override
    public void close() {
        logger.trace("This tag processor has no resources to close");
    }

}
