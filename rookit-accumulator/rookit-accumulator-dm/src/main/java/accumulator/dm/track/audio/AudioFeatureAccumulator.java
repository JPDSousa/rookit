package accumulator.dm.track.audio;

import com.kekstudio.musictheory.Key;
import accumulator.SingleAccumulator;
import accumulator.dm.DataModelAccumulatorFactory;
import accumulator.primitive.MeanDoubleAccumulator;
import accumulator.primitive.ModeShortAccumulator;
import accumulator.opaque.ModeAccumulator;
import accumulator.primitive.TrueWinsAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalShort;

import java.util.Optional;
import java.util.OptionalDouble;

public interface AudioFeatureAccumulator
        extends SingleAccumulator<AudioFeatureAccumulator, AudioFeature, DataModelAccumulatorFactory>, AudioFeature {

    @AccumulatorAcessor
    ModeShortAccumulator getBpmAccumulator();

    @Override
    default OptionalShort getBpm() {
        return getBpmAccumulator().get();
    }

    @AccumulatorAcessor
    MeanDoubleAccumulator getDanceabilityAccumulator();

    @Override
    default OptionalDouble getDanceability() {
        return getDanceabilityAccumulator().get();
    }

    @AccumulatorAcessor
    MeanDoubleAccumulator getEnergyAccumulator();

    @Override
    default OptionalDouble getEnergy() {
        return getEnergyAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<Key> getTrackKeyAccumulator();

    @Override
    default Optional<Key> getTrackKey() {
        return getTrackKeyAccumulator().get();
    }

    @AccumulatorAcessor
    MeanDoubleAccumulator getValenceAccumulator();

    @Override
    default OptionalDouble getValence() {
        return getValenceAccumulator().get();
    }

    @AccumulatorAcessor
    TrueWinsAccumulator getAcousticAccumulator();

    @Override
    default OptionalBoolean isAcoustic() {
        return getAcousticAccumulator().get();
    }

    @AccumulatorAcessor
    TrueWinsAccumulator getInstrumentalAccumulator();

    @Override
    default OptionalBoolean isInstrumental() {
        return getInstrumentalAccumulator().get();
    }

    @AccumulatorAcessor
    TrueWinsAccumulator getLiveAccumulator();

    @Override
    default OptionalBoolean isLive() {
        return getLiveAccumulator().get();
    }
}
