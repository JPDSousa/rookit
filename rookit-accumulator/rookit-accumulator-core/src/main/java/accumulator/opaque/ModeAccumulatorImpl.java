package accumulator.opaque;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import accumulator.AbstractSingleAccumulator;
import org.rookit.utils.collection.BagUtils;

class ModeAccumulatorImpl<T> extends AbstractSingleAccumulator<ModeAccumulator<T>, T, AccumulatorFactory>
        implements ModeAccumulator<T> {

    private static final long serialVersionUID = 3617581357228927423L;
    private final Bag<T> values;

    ModeAccumulatorImpl() {
        this.values = new HashBag<>();
    }

    @Override
    public void accumulate(final T source) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(source, "source");
        this.values.add(source);
    }

    @Override
    public void accumulate(final ModeAccumulator<T> accumulator) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(accumulator, "accumulator");
        this.values.addAll(accumulator.getValues());
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public ModeAccumulator<T> createStage(AccumulatorFactory factory) {
        return new StagedModeAccumulator<>(this);
    }

    @Override
    public Bag<T> getValues() {
        return BagUtils.unmodifiableBag(this.values);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("values", this.values)
                .toString();
    }
}
