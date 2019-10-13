package org.rookit.parser.tag;

import org.junit.jupiter.api.Test;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;
import org.rookit.parser.tag.processor.TagProcessorFactory;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.junit.categories.UnitTest;

import java.nio.file.Path;

import static org.mockito.Mockito.*;

@UnitTest
class TagParserUnitTest extends AbstractUnitTest<TagParser> {

    private TrackSlotParserResultFactory mockedFactory;
    private TagProcessorFactory mockedProcessorFactory;

    @Override
    public TagParser createTestResource() {
        this.mockedFactory = mock(TrackSlotParserResultFactory.class);
        this.mockedProcessorFactory = mock(TagProcessorFactory.class);

        return new TagParser(this.mockedFactory, this.mockedProcessorFactory);
    }

    @Test
    public final void testParseUsesDefaultBaseResult() {
        final Path mockedPath = mock(Path.class);

        this.testResource.parse(mockedPath);
        verify(this.mockedFactory, times(1)).createTrackSlotParserResult();
        verify(this.mockedProcessorFactory, times(1)).create(mockedPath);
    }


}