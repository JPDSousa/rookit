package accumulator.fww;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import accumulator.AbstractSingleAccumulator;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

@ThreadSafe
class FirstWriterWinsAccumulatorImpl<T>
        extends AbstractSingleAccumulator<FirstWriterWinsAccumulator<T>, T, AccumulatorFactory>
        implements FirstWriterWinsAccumulator<T> {

    private static final long UNINITIALIZED = -1;
    private static final long serialVersionUID = -3048426300810812713L;

    private final transient Object writeLock;

    private T item;
    private long timestamp;

    FirstWriterWinsAccumulatorImpl() {
        this.writeLock = new Object();
        this.timestamp = UNINITIALIZED;
    }

    @Override
    public Optional<T> get() {
        return Optional.ofNullable(this.item);
    }

    @Override
    public void accumulate(final T source) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(source, "source");
        final long timestamp = System.currentTimeMillis();
        accumulate(source, timestamp);
    }

    private void accumulate(final T source, final long timestamp) {
        synchronized (this.writeLock) {
            if (timestamp < this.timestamp) {
                this.item = source;
                this.timestamp = timestamp;
            }
        }
    }

    @Override
    public void accumulate(final FirstWriterWinsAccumulator<T> accumulator) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(accumulator, "accumulator");
        final Optional<T> otherResultOrNone = accumulator.get();
        final OptionalLong otherTimestampOrNone = accumulator.getTimeStamp();
        if (otherResultOrNone.isPresent() && otherTimestampOrNone.isPresent()) {
            accumulate(otherResultOrNone.get(), otherTimestampOrNone.getAsLong());
        }
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(this.item);
    }

    @Override
    public FirstWriterWinsAccumulator<T> createStage(final AccumulatorFactory factory) {
        return new StagedFirstWriterWinsAccumulatorImpl<>(this);
    }

    @Override
    public OptionalLong getTimeStamp() {
        return (this.timestamp == UNINITIALIZED)
                ? OptionalLong.empty()
                : OptionalLong.of(this.timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("writeLock", this.writeLock)
                .add("item", this.item)
                .add("timestamp", this.timestamp)
                .toString();
    }
}
