package accumulator.fww;

import com.google.common.base.MoreObjects;

import java.util.Optional;
import java.util.OptionalLong;

final class StagedFirstWriterWinsAccumulatorImpl<T> extends FirstWriterWinsAccumulatorImpl<T> {

    private final FirstWriterWinsAccumulator<T> previousStage;

    StagedFirstWriterWinsAccumulatorImpl(final FirstWriterWinsAccumulator<T> previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    public Optional<T> get() {
        final Optional<T> psResultOrNone = this.previousStage.get();
        final OptionalLong psTimestampOrNone = this.previousStage.getTimeStamp();
        final Optional<T> resultOrNone = super.get();

        if (psResultOrNone.isPresent() && psTimestampOrNone.isPresent()) {
            final OptionalLong timestampOrNone = super.getTimeStamp();
            if (resultOrNone.isPresent() && timestampOrNone.isPresent()) {
                return (psTimestampOrNone.getAsLong() < timestampOrNone.getAsLong())
                        ? psResultOrNone
                        : resultOrNone;
            }
            return psResultOrNone;
        }

        return resultOrNone;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.previousStage.isEmpty();
    }

    @Override
    public OptionalLong getTimeStamp() {
        final OptionalLong timeStamp = super.getTimeStamp();
        return timeStamp.isPresent() ? timeStamp : this.previousStage.getTimeStamp();
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("previousStage", this.previousStage)
                .toString();
    }
}
