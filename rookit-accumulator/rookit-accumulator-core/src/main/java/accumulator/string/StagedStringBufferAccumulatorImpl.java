package accumulator.string;

import com.google.common.base.MoreObjects;

class StagedStringBufferAccumulatorImpl extends StringBufferAccumulatorImpl {

    private final StringBufferAccumulator previousStage;

    StagedStringBufferAccumulatorImpl(final StringBufferAccumulator previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public StringBuffer getValues() {
        final StringBuffer values = new StringBuffer(super.getValues());
        values.append(this.previousStage.getValues());

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
