package accumulator.primitive;

import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.longs.Long2IntArrayMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;

class StagedModeLongAccumulatorImpl extends ModeLongAccumulatorImpl {

    private static final long serialVersionUID = -1554111605918983812L;
    private final ModeLongAccumulator previousStage;

    StagedModeLongAccumulatorImpl(final ModeLongAccumulator previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.previousStage.isEmpty();
    }

    @Override
    public Long2IntMap getValues() {
        final Long2IntMap values = new Long2IntArrayMap(super.getValues());

        final Long2IntMap previousStageValues = this.previousStage.getValues();
        for (final long key : previousStageValues.keySet().toLongArray()) {
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
