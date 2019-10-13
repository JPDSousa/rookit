package org.rookit.parser.format;

import com.google.common.base.MoreObjects;
import one.util.streamex.StreamEx;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.format.result.TrackSlotParserResultFactory;

import java.util.Optional;

final class EnhanceFieldParser extends AbstractFieldParser {

    private final FieldParser innerParser;
    private final String[][] enhancements;

    EnhanceFieldParser(final TrackSlotParserResultFactory accumulatorFactory, final FieldParser innerParser) {
        super(accumulatorFactory);
        this.innerParser = innerParser;
        this.enhancements = new String[][]{/*{"_", " "},*/
                {"  ", " "},
                {"�", "-"}};
    }

    @Override
    protected Optional<TrackSlotParserResult> parseFromBaseResult(final String context,
                                                                  final TrackSlotParserResult baseResult) {
        return this.innerParser.parse(enhanceInput(context), baseResult);
    }

    private String enhanceInput(final String context){
        String enhancedContext = context;
        for(final String[] enhancement : this.enhancements){
            enhancedContext = enhancedContext.replace(enhancement[0], enhancement[1]);
        }
        return enhancedContext.trim();
    }

    @Override
    protected StreamEx<TrackSlotParserResult> parseAllFromBaseResult(final String context,
                                                                     final TrackSlotParserResult baseResult) {
        return super.parseAllFromBaseResult(enhanceInput(context), baseResult);
    }

    @Override
    public boolean isValid(final String context) {
        return this.innerParser.isValid(context);
    }

    @Override
    public String toExpression() {
        return this.innerParser.toExpression();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("innerParser", this.innerParser)
                .add("enhancements", this.enhancements)
                .toString();
    }
}
