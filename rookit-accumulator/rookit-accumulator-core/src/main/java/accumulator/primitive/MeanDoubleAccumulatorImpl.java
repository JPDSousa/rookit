package accumulator.primitive;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;
import org.apache.logging.log4j.Logger;
import accumulator.validator.AccumulatorValidator;

import java.util.OptionalDouble;
import java.util.function.DoubleSupplier;

class MeanDoubleAccumulatorImpl implements MeanDoubleAccumulator {

    private static final Validator VALIDATOR = AccumulatorValidator.getInstance();

    private static final Logger logger = VALIDATOR.getLogger(MeanDoubleAccumulatorImpl.class);
    private static final long serialVersionUID = -8662099898124325066L;

    private final DoubleList values;

    MeanDoubleAccumulatorImpl() {
        this.values = new DoubleArrayList();
    }

    @Override
    public DoubleList getValues() {
        return DoubleLists.unmodifiable(this.values);
    }

    @Override
    public double getOrFallback(final DoubleSupplier supplier, final String fallbackMessage, final Object... args) {
        final OptionalDouble result = get();
        if (result.isPresent()) {
            return result.getAsDouble();
        }

        logger.info(fallbackMessage, args);
        return supplier.getAsDouble();
    }

    @Override
    public void accumulate(final double source) {
        this.values.add(source);
    }

    @Override
    public void accumulate(final MeanDoubleAccumulator accumulator) {
        this.values.addAll(accumulator.getValues());
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("values", this.values)
                .toString();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public MeanDoubleAccumulator createStage(final AccumulatorFactory factory) {
        return new StagedMeanDoubleAccumulatorImpl(this);
    }
}
