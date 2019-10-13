package org.rookit.dm.track.audio;

import com.kekstudio.musictheory.Key;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.api.dm.track.audio.AudioFeatureMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.OptionalDouble;

class AudioFeatureImpl implements AudioFeature {
    
    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AudioFeatureImpl.class);

    private static final short UNINITIALIZED = -1;

    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;
    private final AudioFeatureMetaType<?> properties;

    // Audio features
    private short bpm;

    private Key trackKey;

    private Boolean isInstrumental;

    private Boolean isLive;

    private Boolean isAcoustic;

    private double danceability;

    private double energy;

    private double valence;
    
    AudioFeatureImpl(final Failsafe failsafe, 
                     final OptionalFactory optionalFactory, 
                     final AudioFeatureMetaType<?> properties) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
        this.properties = properties;
        this.bpm = UNINITIALIZED;
        this.danceability = UNINITIALIZED;
        this.energy = UNINITIALIZED;
        this.valence = UNINITIALIZED;
    }

    @Override
    public OptionalShort bpm() {
        return (this.bpm == UNINITIALIZED)
                ? this.optionalFactory.emptyShort()
                : this.optionalFactory.ofShort(this.bpm);
    }

    @Override
    public OptionalDouble danceability() {
        return (this.danceability == UNINITIALIZED)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.danceability);
    }

    @Override
    public OptionalDouble energy() {
        return (this.energy == UNINITIALIZED)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.energy);
    }
    
    @Override
    public Optional<Key> trackKey() {
        return Optional.ofNullable(this.trackKey);
    }
    
    @Override
    public OptionalDouble valence() {
        return (this.valence == UNINITIALIZED)
                ? OptionalDouble.empty()
                : OptionalDouble.of(this.valence);
    }
    
    @Override
    public OptionalBoolean acoustic() {
        return this.optionalFactory.ofNullableBoolean(this.isAcoustic);
    }
    
    @Override
    public OptionalBoolean instrumental() {
        return this.optionalFactory.ofNullableBoolean(this.isInstrumental);
    }

    @Override
    public OptionalBoolean live() {
        return this.optionalFactory.ofNullableBoolean(this.isLive);
    }
    
    @SuppressWarnings({"UnnecessaryBoxing", "BooleanParameter"})
    @Override
    public void setAcoustic(final boolean isAcoustic) {
        this.isAcoustic = Boolean.valueOf(isAcoustic);
    }
    
    @Override
    public void setBpm(final short bpm) {
        this.failsafe.checkArgument().comparable().isBetween(logger,
                bpm, RANGE_BPM, this.properties.bpm().propertyName());
        this.bpm = bpm;
    }

    @Override
    public void setDanceability(final double danceability) {
        this.failsafe.checkArgument().comparable().isBetween(logger,
                danceability, RANGE_DANCEABILITY, this.properties.danceability().propertyName());
        this.danceability = danceability;
    }

    @Override
    public void setEnergy(final double energy) {
        this.failsafe.checkArgument().comparable().isBetween(logger,
                energy, RANGE_ENERGY, this.properties.energy().propertyName());
        this.energy = energy;
    }
    
    @Override
    public void setTrackKey(final Key trackKey) {
        this.failsafe.checkArgument().isNotNull(logger, trackKey, this.properties.trackKey().propertyName());
        this.trackKey = trackKey;
    }

    @Override
    public void setValence(final double valence) {
        this.failsafe.checkArgument().comparable().isBetween(logger,
                valence, RANGE_VALENCE, this.properties.valence().propertyName());
        this.valence = valence;
    }
    
    @SuppressWarnings({"BooleanParameter", "UnnecessaryBoxing"})
    @Override
    public void setInstrumental(final boolean isInstrumental) {
        this.isInstrumental = Boolean.valueOf(isInstrumental);
    }

    @SuppressWarnings({"BooleanParameter", "UnnecessaryBoxing"})
    @Override
    public void setLive(final boolean isLive) {
        this.isLive = Boolean.valueOf(isLive);
    }

    @Override
    public String toString() {
        return "AudioFeatureImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                ", properties=" + this.properties +
                ", bpm=" + this.bpm +
                ", trackKey=" + this.trackKey +
                ", isInstrumental=" + this.isInstrumental +
                ", isLive=" + this.isLive +
                ", isAcoustic=" + this.isAcoustic +
                ", danceability=" + this.danceability +
                ", energy=" + this.energy +
                ", valence=" + this.valence +
                "}";
    }
}
