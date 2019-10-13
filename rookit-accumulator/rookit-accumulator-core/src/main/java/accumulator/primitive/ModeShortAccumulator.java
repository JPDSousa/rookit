package accumulator.primitive;

import accumulator.AccumulatorFactory;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import org.rookit.utils.optional.OptionalShort;
import org.rookit.utils.stage.Stageable;
import org.rookit.utils.supplier.ShortSupplier;

import java.io.Serializable;

public interface ModeShortAccumulator extends Stageable<ModeShortAccumulator, AccumulatorFactory>, Serializable {

    default OptionalShort get() {
        if (isEmpty()) {
            return OptionalShort.empty();
        }

        final Short2IntMap values = getValues();
        boolean isSet = false;
        short max = 0;
        int maxCardinality = 0;

        for (final short key : values.keySet().toShortArray()) {
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

        return OptionalShort.of(max);
    }

    Short2IntMap getValues();

    short getOrFallback(final ShortSupplier supplier, final String fallbackMessage, final Object... args);

    void accumulate(short source);

    void accumulate(ModeShortAccumulator accumulator);

    default boolean isEmpty() {
        return !get().isPresent();
    }
}
