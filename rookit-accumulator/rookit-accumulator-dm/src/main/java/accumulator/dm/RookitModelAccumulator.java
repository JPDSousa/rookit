package accumulator.dm;

import accumulator.SingleAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.RookitModel;

import java.util.Optional;

public interface RookitModelAccumulator<A extends RookitModelAccumulator<A, T>, T extends RookitModel>
        extends SingleAccumulator<A, T, DataModelAccumulatorFactory>, RookitModel {

    @AccumulatorAcessor
    ModeAccumulator<String> getIdAccumulator();

    @Override
    default Optional<String> id() {
        return getIdAccumulator().get();
    }
}
