
package org.rookit.parser.tag.jaudiotagger;

import com.google.inject.Inject;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.parser.tag.processor.TagProcessor;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;

import java.nio.file.Path;

@SuppressWarnings("javadoc")
public class JAudioTaggerTagProcessorFactory extends AbstractTagProcessorFactory {

    private final TagProcessorConfiguration config;
    private final ArtistFactory artistFactory;
    private final GenreFactory genreFactory;

    @Inject
    private JAudioTaggerTagProcessorFactory(final Path tempFilesDirectory,
                                            final TagProcessorConfiguration configuration,
                                            final ArtistFactory artistFactory,
                                            final GenreFactory genreFactory) {
        super(tempFilesDirectory);
        this.config = configuration;
        this.artistFactory = artistFactory;
        this.genreFactory = genreFactory;
    }

    @Override
    public TagProcessor create(final Path path) {
        return new JAudioTaggerTagProcessor(path, this.artistFactory, this.genreFactory, this.config);
    }

}
