package org.rookit.dm.track.audio;

import com.google.inject.Inject;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.api.dm.track.audio.AudioFeatureFactory;
import org.rookit.api.dm.track.audio.AudioFeatureMetaType;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
final class AudioFeatureFactoryImpl implements AudioFeatureFactory {
    
    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AudioFeatureFactoryImpl.class);

    private final Failsafe failsafe;
    private final AudioFeatureMetaType<?> properties;
    private final OptionalFactory optionalFactory;
    
    @Inject
    private AudioFeatureFactoryImpl(final Failsafe failsafe,
                                    final AudioFeatureMetaType<?> properties,
                                    final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.properties = properties;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public AudioFeature create(final Key key) {
        logger.warn("{} creation through {}#create(Key) is ignored for key {}. Creating empty instead.",
                AudioFeature.class, getClass(), key);
        return createEmpty();
    }

    @Override
    public AudioFeature createEmpty() {
        return new AudioFeatureImpl(this.failsafe, this.optionalFactory, this.properties);
    }

}
