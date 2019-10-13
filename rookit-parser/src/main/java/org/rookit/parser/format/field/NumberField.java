package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.utils.string.StringUtils;

import java.util.List;

/**
 * Track primitive
 */
final class NumberField extends AbstractField {

    static final String NAME = "NUMBER";

    @Inject
    private NumberField(final StringUtils stringUtils) {
        super(stringUtils, InitialScore.L2_VALUE);
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        values.stream()
                .mapToInt(Integer::parseInt)
                .forEach(trackSlotAccumulator::setNumber);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        boolean isNumber = true;
        for(int i = 0; (i < value.length()) && isNumber; i++){
            if(!Character.isDigit(value.charAt(i))){
                isNumber = false;
            }
        }

        return super.getScore(value, context) + (isNumber ? 0 : SEVERE);
    }
}
