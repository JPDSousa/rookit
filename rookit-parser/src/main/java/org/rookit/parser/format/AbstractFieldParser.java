package org.rookit.parser.format;

import com.google.common.base.MoreObjects;
import org.rookit.parser.AbstractParser;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;

public abstract class AbstractFieldParser extends AbstractParser<String, TrackSlotParserResult> implements FieldParser {

    private final TrackSlotParserResultFactory accumulatorFactory;

    AbstractFieldParser(final TrackSlotParserResultFactory accumulatorFactory) {
        this.accumulatorFactory = accumulatorFactory;
    }

    @Override
    protected TrackSlotParserResult getDefaultBaseResult() {
        return this.accumulatorFactory.createTrackSlotParserResult();
    }

    protected TrackSlotParserResultFactory getAccumulatorFactory() {
        return this.accumulatorFactory;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accumulatorFactory", this.accumulatorFactory)
                .toString();
    }
}
