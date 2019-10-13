package org.rookit.dm.album.release;

import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.release.Release;
import org.rookit.api.dm.album.release.ReleaseFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;

import java.time.LocalDate;

final class MutableReleaseFactoryImpl implements MutableReleaseFactory {

    private final Failsafe failsafe;
    private final ReleaseFactory releaseFactory;
    private final Mapper mapper;
    private final OptionalFactory optionalFactory;

    @Inject
    private MutableReleaseFactoryImpl(final Failsafe failsafe,
                                      final ReleaseFactory releaseFactory,
                                      final Mapper mapper,
                                      final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.releaseFactory = releaseFactory;
        this.mapper = mapper;
        this.optionalFactory = optionalFactory;
    }

    @SuppressWarnings("FeatureEnvy") // due to being an adapter
    private MutableRelease fromRelease(final Release release) {
        if (release instanceof MutableRelease) {
            return (MutableRelease) release;
        }
        final MutableRelease mutableRelease = new MutableReleaseImpl(release.type(),
                this.failsafe,
                this.optionalFactory);
        this.mapper.map(release, mutableRelease);
        return mutableRelease;
    }

    @Override
    public MutableRelease releaseOf(final TypeRelease release) {
        return fromRelease(this.releaseFactory.releaseOf(release));
    }

    @Override
    public MutableRelease released(final TypeRelease type, final LocalDate date) {
        return fromRelease(this.releaseFactory.released(type, date));
    }

    @Override
    public MutableRelease releasedToday(final TypeRelease release) {
        return fromRelease(this.releaseFactory.releasedToday(release));
    }

    @Override
    public MutableRelease create(final Key key) {
        return fromRelease(this.releaseFactory.create(key));
    }

    @Override
    public MutableRelease createEmpty() {
        return fromRelease(this.releaseFactory.createEmpty());
    }

    @Override
    public String toString() {
        return "MutableReleaseFactoryImpl{" +
                "injector=" + this.failsafe +
                ", releaseFactory=" + this.releaseFactory +
                ", mapper=" + this.mapper +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
