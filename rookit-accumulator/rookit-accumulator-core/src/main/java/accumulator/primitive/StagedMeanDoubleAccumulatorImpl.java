package accumulator.primitive;

import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

class StagedMeanDoubleAccumulatorImpl extends MeanDoubleAccumulatorImpl {

    private final MeanDoubleAccumulator previousStage;

    StagedMeanDoubleAccumulatorImpl(final MeanDoubleAccumulator previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public DoubleList getValues() {
        final DoubleList values = new DoubleArrayList(super.getValues());
        values.addAll(this.previousStage.getValues());

        return values;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.previousStage.isEmpty();
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("previousStage", this.previousStage)
                .toString();
    }
}
