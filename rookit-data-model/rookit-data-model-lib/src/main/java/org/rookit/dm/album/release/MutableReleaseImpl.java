package org.rookit.dm.album.release;

import org.rookit.api.dm.album.TypeRelease;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.LocalDate;

final class MutableReleaseImpl implements MutableRelease {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableReleaseImpl.class);

    private final TypeRelease type;

    @Nullable
    private LocalDate date;
    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    MutableReleaseImpl(final TypeRelease type, final Failsafe failsafe, final OptionalFactory optionalFactory) {
        this(type, null, failsafe, optionalFactory);
    }

    MutableReleaseImpl(final TypeRelease type,
                       final LocalDate date,
                       final Failsafe failsafe,
                       final OptionalFactory optionalFactory) {
        this.type = type;
        this.date = date;
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public void setReleaseDate(final LocalDate releaseDate) {
        this.failsafe.checkArgument().isNotNull(logger, releaseDate, "releaseDate");
        this.date = releaseDate;
    }

    @Override
    public TypeRelease type() {
        return this.type;
    }

    @Override
    public Optional<LocalDate> date() {
        return this.optionalFactory.ofNullable(this.date);
    }

    @Override
    public String toString() {
        return "MutableReleaseImpl{" +
                "type=" + this.type +
                ", date=" + this.date +
                ", injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
