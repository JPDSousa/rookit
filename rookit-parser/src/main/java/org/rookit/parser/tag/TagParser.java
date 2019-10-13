package org.rookit.parser.tag;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.parser.AbstractParser;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;
import org.rookit.parser.tag.processor.TagProcessor;
import org.rookit.parser.tag.processor.TagProcessorFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

final class TagParser extends AbstractParser<Path, TrackSlotParserResult> {

    private final TrackSlotParserResultFactory accumulatorFactory;
    private final TagProcessorFactory tagProcessorFactory;

    @Inject
    TagParser(final TrackSlotParserResultFactory accumulatorFactory,
                      final TagProcessorFactory tagProcessorFactory) {
        this.accumulatorFactory = accumulatorFactory;
        this.tagProcessorFactory = tagProcessorFactory;
    }

    @Override
    protected TrackSlotParserResult getDefaultBaseResult() {
        return this.accumulatorFactory.createTrackSlotParserResult();
    }

    @Override
    protected Optional<TrackSlotParserResult> parseFromBaseResult(final Path context,
                                                                  final TrackSlotParserResult baseResult) {
        try (final TagProcessor tagProcessor = this.tagProcessorFactory.create(context)) {
            return Optional.of(tagProcessor.process(baseResult));
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accumulatorFactory", this.accumulatorFactory)
                .add("tagProcessorFactory", this.tagProcessorFactory)
                .toString();
    }
}
