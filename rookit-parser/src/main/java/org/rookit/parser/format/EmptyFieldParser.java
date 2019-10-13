package org.rookit.parser.format;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;

import java.util.Optional;

final class EmptyFieldParser extends AbstractFieldParser {

    private final boolean allowNonEmptyStrings;

    EmptyFieldParser(final TrackSlotParserResultFactory accumulatorFactory,
                     final boolean allowNonEmptyStrings) {
        super(accumulatorFactory);
        this.allowNonEmptyStrings = allowNonEmptyStrings;
    }


    @Override
    protected Optional<TrackSlotParserResult> parseFromBaseResult(final String context,
                                                                  final TrackSlotParserResult baseResult) {
        return Optional.of(baseResult.createResultStage(getAccumulatorFactory()));
    }

    @Override
    public boolean isValid(final String context) {
        return this.allowNonEmptyStrings || context.isEmpty();
    }

    @Override
    public String toExpression() {
        return StringUtils.EMPTY;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("allowNonEmptyStrings", this.allowNonEmptyStrings)
                .toString();
    }
}
