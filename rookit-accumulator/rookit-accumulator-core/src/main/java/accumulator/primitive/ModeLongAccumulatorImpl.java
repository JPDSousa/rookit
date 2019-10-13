package accumulator.primitive;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.longs.Long2IntArrayMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import org.apache.logging.log4j.Logger;
import accumulator.validator.AccumulatorValidator;
import org.rookit.utils.log.validator.Validator;

import java.util.OptionalLong;
import java.util.function.LongSupplier;

class ModeLongAccumulatorImpl implements ModeLongAccumulator {

    private static final Validator VALIDATOR = AccumulatorValidator.getInstance();

    private static final Logger logger = VALIDATOR.getLogger(ModeLongAccumulatorImpl.class);
    private static final long serialVersionUID = -6192414479213943936L;

    private final Long2IntMap map;

    ModeLongAccumulatorImpl() {
        this.map = new Long2IntArrayMap();
    }

    @Override
    public Long2IntMap getValues() {
        return Long2IntMaps.unmodifiable(this.map);
    }

    @Override
    public long getOrFallback(final LongSupplier fallback, final String fallbackMessage, final Object... messageArgs) {
        final OptionalLong result = get();

        if (result.isPresent()) {
            return result.getAsLong();
        }

        logger.info(fallbackMessage, messageArgs);
        return fallback.getAsLong();
    }

    @Override
    public void accumulate(final long source) {
        accumulate(source, 1);
    }

    private void accumulate(final long source, final int times) {
        final int cardinality = this.map.get(source);
        this.map.put(source, cardinality + times);
    }

    @Override
    public void accumulate(final ModeLongAccumulator accumulator) {
        final Long2IntMap values = accumulator.getValues();
        for (final long key : values.keySet().toLongArray()) {
            final int cardinality = values.getOrDefault(key, 0);
            accumulate(key, cardinality);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public ModeLongAccumulator createStage(final AccumulatorFactory factory) {
        return new StagedModeLongAccumulatorImpl(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("map", this.map)
                .toString();
    }
}
