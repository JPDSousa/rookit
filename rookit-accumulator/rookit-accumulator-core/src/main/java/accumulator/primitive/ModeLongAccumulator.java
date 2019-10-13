package accumulator.primitive;

import accumulator.AccumulatorFactory;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import org.rookit.utils.stage.Stageable;

import java.io.Serializable;
import java.util.OptionalLong;
import java.util.function.LongSupplier;

public interface ModeLongAccumulator extends Stageable<ModeLongAccumulator, AccumulatorFactory>, Serializable {

    default OptionalLong get() {
        if (isEmpty()) {
            return OptionalLong.empty();
        }

        final Long2IntMap values = getValues();
        boolean isSet = false;
        long max = 0;
        int maxCardinality = 0;

        for (final long key : values.keySet().toLongArray()) {
            final int cardinality = values.getOrDefault(key, 0);
            if (!isSet) {
                isSet = true;
                max = key;
                maxCardinality = cardinality;
            } else if (cardinality > maxCardinality) {
                max = key;
                maxCardinality = cardinality;
            }
        }

        return OptionalLong.of(max);
    }

    Long2IntMap getValues();

    long getOrFallback(final LongSupplier supplier, final String fallbackMessage, final Object... args);

    void accumulate(long source);

    void accumulate(ModeLongAccumulator accumulator);

    default boolean isEmpty() {
        return !get().isPresent();
    }
}
