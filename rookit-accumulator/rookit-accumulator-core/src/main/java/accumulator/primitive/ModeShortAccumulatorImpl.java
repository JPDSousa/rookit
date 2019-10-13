package accumulator.primitive;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.shorts.Short2IntArrayMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMaps;
import org.apache.logging.log4j.Logger;
import accumulator.validator.AccumulatorValidator;
import org.rookit.utils.optional.OptionalShort;
import org.rookit.utils.supplier.ShortSupplier;

class ModeShortAccumulatorImpl implements ModeShortAccumulator {

    private static final Validator VALIDATOR = AccumulatorValidator.getInstance();

    private static final Logger logger = VALIDATOR.getLogger(ModeShortAccumulatorImpl.class);
    private static final long serialVersionUID = -4629252226400511858L;

    private final Short2IntMap values;

    ModeShortAccumulatorImpl() {
        this.values = new Short2IntArrayMap();
    }

    @Override
    public Short2IntMap getValues() {
        return Short2IntMaps.unmodifiable(this.values);
    }

    @Override
    public short getOrFallback(final ShortSupplier supplier, final String fallbackMessage, final Object... args) {
        final OptionalShort result = get();
        if (result.isPresent()) {
            return result.getAsShort();
        }

        logger.info(fallbackMessage, args);
        return supplier.getAsShort();
    }

    @Override
    public void accumulate(final short source) {
        accumulate(source, 1);
    }

    private void accumulate(final short value, final int times) {
        final int cardinality = this.values.get(value);
        this.values.put(value, times + cardinality);
    }

    @Override
    public void accumulate(final ModeShortAccumulator accumulator) {
        final Short2IntMap values = accumulator.getValues();
        for (final short key : values.keySet().toShortArray()) {
            final int cardinality = values.getOrDefault(key, 0);
            accumulate(key, cardinality);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public ModeShortAccumulator createStage(final AccumulatorFactory factory) {
        return new StagedModeShortAccumulatorImpl(this);
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("values", this.values)
                .toString();
    }
}
