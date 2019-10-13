package accumulator.primitive;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.Logger;
import accumulator.validator.AccumulatorValidator;
import org.rookit.utils.optional.OptionalBoolean;

import java.util.function.BooleanSupplier;

class TrueWinsAccumulatorImpl implements TrueWinsAccumulator {

    private static final long serialVersionUID = 6008440840367901967L;
    private static final Validator VALIDATOR = AccumulatorValidator.getInstance();

    private static final Logger logger = VALIDATOR.getLogger(TrueWinsAccumulatorImpl.class);

    private boolean isSet;
    private boolean value;

    TrueWinsAccumulatorImpl() {
        this.isSet = false;
        this.value = false;
    }

    @Override
    public OptionalBoolean getValue() {
        return this.isSet ? OptionalBoolean.of(this.value) : OptionalBoolean.empty();
    }

    @Override
    public double getOrFallback(final BooleanSupplier supplier, final String fallbackMessage, final Object... args) {
        return 0;
    }

    @SuppressWarnings("BooleanParameter")
    @Override
    public void accumulate(final boolean source) {
        this.isSet = true;
        if (!this.value) {
            this.value = source;
        }
    }

    @Override
    public void accumulate(final TrueWinsAccumulator accumulator) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(accumulator, "accumulator");
        accumulator.getValue().ifPresent(this::accumulate);
    }

    @Override
    public boolean isEmpty() {
        return !this.isSet;
    }

    @Override
    public TrueWinsAccumulator createStage(final AccumulatorFactory factory) {
        return new StagedTrueWinsAccumulatorImpl(this);
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("isSet", this.isSet)
                .add("value", this.value)
                .toString();
    }
}
