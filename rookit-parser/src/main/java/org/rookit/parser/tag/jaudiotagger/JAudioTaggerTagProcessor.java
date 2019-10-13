
package org.rookit.parser.tag.jaudiotagger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.tag.processor.AbstractTagProcessor;
import org.rookit.parser.tag.processor.TagProcessor;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

class JAudioTaggerTagProcessor extends AbstractTagProcessor {

    private static final Logger logger = VALIDATOR.getLogger(JAudioTaggerTagProcessor.class);

    private final Collection<TagProcessor> subProcessors;

    JAudioTaggerTagProcessor(final Path path,
                             final ArtistFactory artistFactory,
                             final GenreFactory genreFactory, final TagProcessorConfiguration configuration) {
        super(artistFactory, configuration);
        final AudioFile audioFile = JAudioTaggerUtils.readPath(path);

        if (audioFile instanceof MP3File) {
            this.subProcessors = ImmutableSet.copyOf(loadMp3TagProcessors((MP3File) audioFile, genreFactory));
        } else {
            logger.warn("Path {} is not an MP3File, thus the correspondent tag processor is a no-op", path);
            this.subProcessors = ImmutableSet.of();
        }
    }

    @Override
    public TrackSlotParserResult process(final TrackSlotParserResult trackSlotAccumulator) {
        for (final TagProcessor tagProcessor : this.subProcessors) {
            tagProcessor.process(trackSlotAccumulator);
        }

        return trackSlotAccumulator;
    }

    private Collection<TagProcessor> loadMp3TagProcessors(final MP3File mp3, final GenreFactory genreFactory) {
        final ImmutableSet.Builder<TagProcessor> builder = ImmutableSet.builder();
        final ID3v1Tag id3v1Tag = mp3.getID3v1Tag();
        final ID3v24Tag id3v24Tag = mp3.getID3v2TagAsv24();

        if (Objects.nonNull(id3v1Tag)) {
            builder.add(new JAudioTaggerID3v1TagProcessor(getArtistFactory(),
                    getConfig(),
                    id3v1Tag,
                    genreFactory));
        }
        if (Objects.nonNull(id3v24Tag)) {
            builder.add(new JAudioTaggerID3v24TagProcessor(getArtistFactory(), getConfig(), id3v24Tag));
        }

        return builder.build();
    }

    @Override
    public void close() throws IOException {
        for (final TagProcessor subProcessor : this.subProcessors) {
            subProcessor.close();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("subProcessors", this.subProcessors)
                .toString();
    }
}
