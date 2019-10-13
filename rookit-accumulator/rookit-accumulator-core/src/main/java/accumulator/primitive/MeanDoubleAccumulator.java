package accumulator.primitive;

import accumulator.AccumulatorFactory;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.rookit.utils.stage.Stageable;

import java.io.Serializable;
import java.util.OptionalDouble;
import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

public interface MeanDoubleAccumulator extends Stageable<MeanDoubleAccumulator, AccumulatorFactory>, Serializable {

    default OptionalDouble get() {
        if (isEmpty()) {
            return OptionalDouble.empty();
        }

        final DoubleList values = getValues();
        return OptionalDouble.of(DoubleStream.of(values.toDoubleArray()).sum() / values.size());
    }

    DoubleList getValues();

    double getOrFallback(final DoubleSupplier supplier, final String fallbackMessage, final Object... args);

    void accumulate(double source);

    void accumulate(MeanDoubleAccumulator accumulator);

    default boolean isEmpty() {
        return !get().isPresent();
    }
}
