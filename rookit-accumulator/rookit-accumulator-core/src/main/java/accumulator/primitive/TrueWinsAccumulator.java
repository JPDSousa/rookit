package accumulator.primitive;

import accumulator.AccumulatorFactory;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.stage.Stageable;

import java.io.Serializable;
import java.util.function.BooleanSupplier;

public interface TrueWinsAccumulator extends Stageable<TrueWinsAccumulator, AccumulatorFactory>, Serializable {

    default OptionalBoolean get() {
        return getValue();
    }

    OptionalBoolean getValue();

    double getOrFallback(final BooleanSupplier supplier, final String fallbackMessage, final Object... args);

    void accumulate(boolean source);

    void accumulate(TrueWinsAccumulator accumulator);

    default boolean isEmpty() {
        return !get().isPresent();
    }
}
