package accumulator.dm.play.able;

import accumulator.dm.RookitModelAccumulator;
import accumulator.primitive.ModeLongAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.play.able.Playable;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

public interface PlayableAccumulator<A extends PlayableAccumulator<A, T>, T extends Playable>
        extends RookitModelAccumulator<A, T>, Playable {

    @AccumulatorAcessor
    ModeAccumulator<Duration> getDurationAccumulator();

    @Override
    default Optional<Duration> duration() {
        return getDurationAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<LocalDate> getLastPlayedAccumulator();

    @Override
    default Optional<LocalDate> getLastPlayed() {
        return getLastPlayedAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<LocalDate> getLastSkippedAccumulator();

    @Override
    default Optional<LocalDate> getLastSkipped() {
        return getLastSkippedAccumulator().get();
    }

    @AccumulatorAcessor
    ModeLongAccumulator getPlaysAccumulator();

    @Override
    default long getPlays() {
        return getPlaysAccumulator().get().orElse(0);
    }

    @AccumulatorAcessor
    ModeLongAccumulator getSkippedAccumulator();

    @Override
    default long getSkipped() {
        return getSkippedAccumulator().get().orElse(0);
    }
}
