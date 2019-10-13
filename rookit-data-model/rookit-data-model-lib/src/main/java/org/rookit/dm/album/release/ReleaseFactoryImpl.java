package org.rookit.dm.album.release;

import com.google.inject.Inject;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.release.Release;
import org.rookit.api.dm.album.release.ReleaseFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.release.config.ReleaseConfig;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

final class ReleaseFactoryImpl implements ReleaseFactory {

    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ReleaseFactoryImpl.class);

    private final Failsafe failsafe;
    private final ReleaseConfig config;
    private final OptionalFactory optionalFactory;

    @Inject
    private ReleaseFactoryImpl(final Failsafe failsafe,
                               final ReleaseConfig config,
                               final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.config = config;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Release releaseOf(final TypeRelease release) {
        return new MutableReleaseImpl(release, this.failsafe, this.optionalFactory);
    }

    @Override
    public Release released(final TypeRelease type, final LocalDate date) {
        return new MutableReleaseImpl(type, date, this.failsafe, this.optionalFactory);
    }

    @Override
    public Release releasedToday(final TypeRelease type) {
        return released(type, LocalDate.now());
    }

    @Override
    public Release create(final Key key) {
        logger.warn("Creation by key is not supported. Falling back to empty creation.");
        return createEmpty();
    }

    @SuppressWarnings("RedundantTypeArguments")
    @Override
    public Release createEmpty() {
        return this.config.defaultType()
                .map(this::releaseOf)
                .orElseThrow(() -> this.failsafe.handleException()
                        .<RuntimeException>runtimeException(
                                "Cannot create an empty Release as no default type is withProperty"));
    }

    @Override
    public String toString() {
        return "ReleaseFactoryImpl{" +
                "injector=" + this.failsafe +
                ", config=" + this.config +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
