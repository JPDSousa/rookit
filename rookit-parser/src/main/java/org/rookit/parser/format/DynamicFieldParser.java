package org.rookit.parser.format;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import one.util.streamex.StreamEx;
import org.rookit.parser.format.field.Field;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;
import org.rookit.utils.string.tokenizer.StringTokenizer;
import org.rookit.utils.string.tokenizer.Token;

import java.util.Optional;

final class DynamicFieldParser extends AbstractFieldParser {

    private final Field field;
    private final String delimiter;
    private final FieldParser nextParser;

    DynamicFieldParser(final TrackSlotParserResultFactory accumulatorFactory,
                       final Field field,
                       final String delimiter,
                       final FieldParser nextParser) {
        super(accumulatorFactory);
        this.field = field;
        this.delimiter = delimiter;
        this.nextParser = nextParser;
    }

    @Override
    protected Optional<TrackSlotParserResult> parseFromBaseResult(final String context,
                                                                  final TrackSlotParserResult baseResult) {
        return parseAll(context, baseResult)
                .maxByInt(TrackSlotParserResult::getScore);
    }

    @Override
    protected StreamEx<TrackSlotParserResult> parseAllFromBaseResult(final String context,
                                                                    final TrackSlotParserResult baseResult) {
        return StreamEx.of(StringTokenizer.create(context, this.delimiter))
                .filter(stringToken -> this.nextParser.isValid(stringToken.payload()))
                .flatMap(stringToken -> parseToken(stringToken, baseResult));
    }

    private StreamEx<TrackSlotParserResult> parseToken(final Token stringToken,
                                                       final TrackSlotParserResult baseResult) {
        return this.nextParser.parseAll(stringToken.payload(), baseResult)
                .peek(result -> this.field.setField(result, ImmutableList.of(stringToken.token())));
    }

    @Override
    public boolean isValid(final String context) {
        return context.indexOf(this.delimiter) > 0;
    }

    @Override
    public String toExpression() {
        return this.field.toExpression() + this.delimiter + this.nextParser.toExpression();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("field", this.field)
                .add("delimiter", this.delimiter)
                .add("nextParser", this.nextParser)
                .toString();
    }
}
