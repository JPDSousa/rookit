package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.WordUtils;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.utils.string.StringUtils;

import java.util.Arrays;
import java.util.List;

abstract class AbstractTitleField extends AbstractField {

    private final String[] suspiciousNameTokens;

    AbstractTitleField(final StringUtils stringUtils, final InitialScore score, final String[] suspiciousNameTokens) {
        super(stringUtils, score);
        this.suspiciousNameTokens = ArrayUtils.clone(suspiciousNameTokens);
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        values.stream()
                .map(WordUtils::capitalizeFully)
                .forEach(title -> applyTitle(trackSlotAccumulator, title));
    }

    protected abstract void applyTitle(final TrackSlotAccumulator trackSlotAccumulator, final String title);

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        final boolean isValid = Arrays.stream(this.suspiciousNameTokens)
                .noneMatch(value::contains);
        final int score = super.getScore(value, context);

        return isValid ? score : (score + LOW);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suspiciousNameTokens", this.suspiciousNameTokens)
                .toString();
    }
}
