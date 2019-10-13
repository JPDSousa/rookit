package org.rookit.parser.format;

import org.rookit.parser.Parser;
import org.rookit.parser.format.result.TrackSlotParserResult;

public interface FieldParser extends Parser<String, TrackSlotParserResult> {

    boolean isValid(String context);

    String toExpression();

}
