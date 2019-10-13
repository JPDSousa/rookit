package accumulator.fww;

import accumulator.AccumulatorFactory;
import accumulator.SingleAccumulator;

import java.util.OptionalLong;

public interface FirstWriterWinsAccumulator<T>
        extends SingleAccumulator<FirstWriterWinsAccumulator<T>, T, AccumulatorFactory> {

    OptionalLong getTimeStamp();

}
