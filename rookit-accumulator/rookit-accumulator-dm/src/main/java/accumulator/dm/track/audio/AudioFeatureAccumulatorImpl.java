package accumulator.dm.track.audio;

import com.kekstudio.musictheory.Key;
import accumulator.AccumulatorFactory;
import accumulator.dm.AbstractDataModelAccumulator;
import accumulator.dm.DataModelAccumulatorFactory;
import accumulator.opaque.ModeAccumulator;
import accumulator.primitive.MeanDoubleAccumulator;
import accumulator.primitive.ModeShortAccumulator;
import accumulator.primitive.TrueWinsAccumulator;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.api.dm.track.audio.AudioFeatureFactory;

import java.util.Optional;

class AudioFeatureAccumulatorImpl extends AbstractDataModelAccumulator<AudioFeatureAccumulator, AudioFeature, DataModelAccumulatorFactory>
        implements AudioFeatureAccumulator {

    private final AudioFeatureFactory factory;

    private final ModeShortAccumulator bpm;
    private final MeanDoubleAccumulator danceability;
    private final MeanDoubleAccumulator energy;
    private final ModeAccumulator<Key> trackKey;
    private final MeanDoubleAccumulator valence;
    private final TrueWinsAccumulator acoustic;
    private final TrueWinsAccumulator instrumental;
    private final TrueWinsAccumulator live;

    AudioFeatureAccumulatorImpl(final AccumulatorFactory accumulatorFactory,
                                final AudioFeatureFactory factory) {
        this.factory = factory;
        this.bpm = accumulatorFactory.createModeShortAccumulator();
        this.danceability = accumulatorFactory.createMeanDoubleAccumulator();
        this.energy = accumulatorFactory.createMeanDoubleAccumulator();
        this.trackKey = accumulatorFactory.createModeAccumulator();
        this.valence = accumulatorFactory.createMeanDoubleAccumulator();
        this.acoustic = accumulatorFactory.createTrueWinsAccumulator();
        this.instrumental = accumulatorFactory.createTrueWinsAccumulator();
        this.live = accumulatorFactory.createTrueWinsAccumulator();
    }


    @Override
    public ModeShortAccumulator getBpmAccumulator() {
        return this.bpm;
    }

    @Override
    public MeanDoubleAccumulator getDanceabilityAccumulator() {
        return this.danceability;
    }

    @Override
    public MeanDoubleAccumulator getEnergyAccumulator() {
        return this.energy;
    }

    @Override
    public ModeAccumulator<Key> getTrackKeyAccumulator() {
        return this.trackKey;
    }

    @Override
    public MeanDoubleAccumulator getValenceAccumulator() {
        return this.valence;
    }

    @Override
    public TrueWinsAccumulator getAcousticAccumulator() {
        return this.acoustic;
    }

    @Override
    public TrueWinsAccumulator getInstrumentalAccumulator() {
        return this.instrumental;
    }

    @Override
    public TrueWinsAccumulator getLiveAccumulator() {
        return this.live;
    }

    @Override
    protected Optional<AudioFeature> constructResult() {
        return Optional.of(this.factory.createEmpty());
    }

    @Override
    public Void setAcoustic(final boolean isAcoustic) {
        return null;
    }

    @Override
    public Void setBpm(final short bpm) {
        return null;
    }

    @Override
    public Void setDanceability(final double danceability) {
        return null;
    }

    @Override
    public Void setEnergy(final double energy) {
        return null;
    }

    @Override
    public Void setInstrumental(final boolean isInstrumental) {
        return null;
    }

    @Override
    public Void setLive(final boolean isLive) {
        return null;
    }

    @Override
    public Void setTrackKey(final Key trackKey) {
        return null;
    }

    @Override
    public Void setValence(final double valence) {
        return null;
    }

    @Override
    public AudioFeatureAccumulator createStage(final DataModelAccumulatorFactory factory) {
        return null;
    }
}
