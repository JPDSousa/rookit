package accumulator.opaque;

import accumulator.AccumulatorFactory;
import org.apache.commons.collections4.Bag;
import accumulator.SingleAccumulator;
import org.rookit.utils.collection.BagUtils;

import java.util.Optional;

public interface ModeAccumulator<T> extends SingleAccumulator<ModeAccumulator<T>, T, AccumulatorFactory> {

    @Override
    default Optional<T> get() {
        return BagUtils.getHighestCount(getValues());
    }

    Bag<T> getValues();
}
