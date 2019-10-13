package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import org.rookit.parser.config.ParserConfiguration;

import java.util.List;

public interface Field {

    int getInitialScore();

    /**
     * Calculates the score of the field based on the value passed as parameter.
     * The score returned can be negative, meaning that the value might not be suitable
     * for the field. E.g. Passing a non-numeric value to a numeric field
     * will result in a negative score, since this field expects numeric values.
     *
     * <p>Note: when a field has multiple values, this field is invoked independently
     * for each value.
     *
     * @param value value of the field. Use the field's value only.
     * @return An integer with the score calculated based on the value.
     */
    int getScore(final String value, final ParserConfiguration context);

    /**
     * Applies the field's values to the track passed as parameter.
     *  @param trackSlotAccumulator track object which will be affected with the values
     * passed.
     * @param values field values. These values will affect the track passed
     */
    void setField(TrackSlotAccumulator trackSlotAccumulator, List<String> values);

    boolean isValidValue(final String value);

    String getName();

    default String toExpression() {
        return String.format("<%s>", getName());
    }
}
