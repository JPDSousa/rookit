package accumulator.primitive;

import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.shorts.Short2IntArrayMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;

final class StagedModeShortAccumulatorImpl extends ModeShortAccumulatorImpl {

    private static final long serialVersionUID = 562793140337891990L;
    private final ModeShortAccumulator previousStage;

    StagedModeShortAccumulatorImpl(final ModeShortAccumulator previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.previousStage.isEmpty();
    }

    @Override
    public Short2IntMap getValues() {
        final Short2IntMap values = new Short2IntArrayMap(super.getValues());

        final Short2IntMap previousStageValues = this.previousStage.getValues();
        for (final short key : previousStageValues.keySet().toShortArray()) {
            values.put(key, previousStageValues.getOrDefault(key, 0));
        }

        return values;
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("previousStage", this.previousStage)
                .toString();
    }
}
