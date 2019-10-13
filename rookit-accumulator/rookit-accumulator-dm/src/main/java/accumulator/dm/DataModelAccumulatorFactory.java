package accumulator.dm;

import accumulator.AccumulatorFactory;
import accumulator.dm.album.slot.TrackSlotAccumulator;

public interface DataModelAccumulatorFactory extends AccumulatorFactory {

    TrackSlotAccumulator createTrackSlotAccumulator();

}
