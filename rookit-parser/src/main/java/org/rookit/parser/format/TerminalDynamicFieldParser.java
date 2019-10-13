package org.rookit.parser.format;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.rookit.parser.format.field.Field;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;

import java.util.Optional;

final class TerminalDynamicFieldParser extends AbstractFieldParser {

    private final Field field;

    TerminalDynamicFieldParser(final TrackSlotParserResultFactory accumulatorFactory,
                               final Field field) {
        super(accumulatorFactory);
        this.field = field;
    }

    @Override
    protected Optional<TrackSlotParserResult> parseFromBaseResult(final String context,
                                                                  final TrackSlotParserResult baseResult) {
        final TrackSlotParserResult clone = baseResult.createResultStage(getAccumulatorFactory());
        this.field.setField(clone, ImmutableList.of(context));

        return Optional.of(clone);
    }

    @Override
    public boolean isValid(final String context) {
        return !context.isEmpty();
    }

    @Override
    public String toExpression() {
        return this.field.toExpression();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("field", this.field)
                .toString();
    }
}
