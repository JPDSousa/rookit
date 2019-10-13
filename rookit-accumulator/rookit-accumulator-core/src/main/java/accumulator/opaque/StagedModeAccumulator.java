package accumulator.opaque;

import com.google.common.base.MoreObjects;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

class StagedModeAccumulator<T> extends ModeAccumulatorImpl<T> {

    private final ModeAccumulator<T> previousStage;

    StagedModeAccumulator(final ModeAccumulator<T> previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.previousStage.isEmpty();
    }

    @Override
    public Bag<T> getValues() {
        final Bag<T> values = new HashBag<>(super.getValues());

        values.addAll(this.previousStage.getValues());
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
