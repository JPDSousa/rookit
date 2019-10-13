package accumulator.string;

import accumulator.AccumulatorFactory;
import accumulator.SingleAccumulator;

import java.util.Optional;

public interface StringBufferAccumulator extends SingleAccumulator<StringBufferAccumulator, String, AccumulatorFactory> {

    @Override
    default Optional<String> get() {
        return Optional.of(getValues())
                .map(StringBuffer::toString);
    }

    StringBuffer getValues();
}
