package org.rookit.parser.tag.processor;

import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;

import java.io.Closeable;

@SuppressWarnings("javadoc")
public interface TagProcessor extends Closeable {

    TrackSlotParserResult process(TrackSlotParserResult result);

    TagProcessorConfiguration getConfig();
}
