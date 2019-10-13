package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.parser.config.ParserConfiguration;

import java.util.List;

final class IgnoreField extends AbstractField {

    static final String NAME = "IGNORE";
    private final StorageManager storageManager;

    @Inject
    private IgnoreField(final StorageManager storageManager) {
        super(stringUtils, InitialScore.L0_VALUE);
        this.storageManager = storageManager;
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        // look at me, ignoring the values :D
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        final int occurrences = this.storageManager.getIgnoredOccurrences(value);

        return super.getScore(value, context) + occurrences;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("storageManager", this.storageManager)
                .toString();
    }
}
