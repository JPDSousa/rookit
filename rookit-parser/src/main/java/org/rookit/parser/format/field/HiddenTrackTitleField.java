package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.utils.string.StringUtils;

import static org.rookit.parser.format.field.InitialScore.L2_VALUE;

final class HiddenTrackTitleField extends AbstractTitleField {

    static final String NAME = "HIDDEN";

    @Inject
    private HiddenTrackTitleField(final StringUtils stringUtils, final String[] suspiciousNameTokens) {
        super(stringUtils, L2_VALUE, suspiciousNameTokens);
    }

    @Override
    protected void applyTitle(final TrackSlotAccumulator trackSlotAccumulator, final String title) {
        trackSlotAccumulator.getHiddenTrackAccumulator().setTitle(title);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
