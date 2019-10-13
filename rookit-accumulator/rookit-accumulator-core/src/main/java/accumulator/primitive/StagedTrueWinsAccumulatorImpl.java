package accumulator.primitive;

import com.google.common.base.MoreObjects;
import org.rookit.utils.optional.OptionalBoolean;

class StagedTrueWinsAccumulatorImpl extends TrueWinsAccumulatorImpl {

    private static final long serialVersionUID = -857306611112176068L;
    private final TrueWinsAccumulator previousStage;

    StagedTrueWinsAccumulatorImpl(final TrueWinsAccumulator previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public OptionalBoolean getValue() {
        final OptionalBoolean superResultOrNone = super.getValue();

        final OptionalBoolean previousStageValue = this.previousStage.getValue();
        if (!superResultOrNone.isPresent()) {
            // super not present -> previous wins
            return previousStageValue;
        } else if (superResultOrNone.getAsBoolean()) {
            // super is present and true -> super wins
            return superResultOrNone;
        } else if (previousStageValue.isPresent()) {
            // super is present and false; previous is present -> previous wins
            return previousStageValue;
        }
        // super is present and false; previous is empty -> false wins
        return OptionalBoolean.of(false);
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
