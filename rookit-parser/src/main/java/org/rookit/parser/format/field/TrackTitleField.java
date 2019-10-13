package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.utils.string.StringUtils;

import static org.rookit.parser.format.field.InitialScore.L2_VALUE;

/**
 * Track title
 */
final class TrackTitleField extends AbstractTitleField {

    static final String NAME = "TITLE";

    @Inject
    private TrackTitleField(final StringUtils stringUtils, final String[] suspiciousNameTokens) {
        super(stringUtils, L2_VALUE, suspiciousNameTokens);
    }

    @Override
    protected void applyTitle(final TrackSlotAccumulator trackSlotAccumulator, final String title) {
        trackSlotAccumulator.getTrackAccumulator().setTitle(title);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
