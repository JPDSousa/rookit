
package accumulator.collection.growonly;

import accumulator.AccumulatorFactory;
import accumulator.SingleAccumulator;
import accumulator.collection.CollectionAccumulator;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("javadoc")
public interface GrowOnlyCollectionAccumulator<A extends SingleAccumulator<A, T, ?>, T>
        extends CollectionAccumulator<GrowOnlyCollectionAccumulator<A, T>, T, AccumulatorFactory> {
    
    Map<T, A> getCollectionMap();

    Set<T> getAsSet();

}
