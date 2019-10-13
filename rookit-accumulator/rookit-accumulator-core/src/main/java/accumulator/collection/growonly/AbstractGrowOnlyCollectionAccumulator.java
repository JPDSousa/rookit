
package accumulator.collection.growonly;

import accumulator.SingleAccumulator;
import accumulator.validator.AccumulatorValidator;
import one.util.streamex.StreamEx;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

abstract class AbstractGrowOnlyCollectionAccumulator<A extends SingleAccumulator<A, T, ?>, T>
        implements GrowOnlyCollectionAccumulator<A, T> {

    protected static final Validator VALIDATOR = AccumulatorValidator.getInstance();

    private static final Logger logger = VALIDATOR.getLogger(AbstractGrowOnlyCollectionAccumulator.class);
    private static final String REMOVE_MESSAGE = "Remove operations have no effect in grow-only accumulators";
    private static final long serialVersionUID = -7370064689656395138L;

    protected abstract StreamEx<T> materializeAsStream();
    
    @SuppressWarnings("NumericCastThatLosesPrecision")
    @Override
    public int size() {
        return (int) materializeAsStream()
                .limit(Integer.MAX_VALUE)
                .count();
    }

    @Override
    public Object[] toArray() {
        return materializeAsStream()
                .toArray();
    }

    @Override
    public <E> E[] toArray(final E[] array) {
        VALIDATOR.checkArgument().isNotNull(array, "array");
        return materializeAsStream()
                .toArray(array);
    }
    

    @Override
    public Collection<T> get() {
        return materializeAsStream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeIf(final Predicate<? super T> filter) {
        logger.warn(REMOVE_MESSAGE);
        return false;
    }

    @Override
    public boolean remove(final Object o) {
        logger.warn(REMOVE_MESSAGE);
        return false;
    }

    @Override
    public boolean removeAll(final Collection<?> collection) {
        logger.warn(REMOVE_MESSAGE);
        return false;
    }

    @Override
    public boolean retainAll(final Collection<?> collection) {
        logger.warn(REMOVE_MESSAGE);
        return false;
    }
}
