package org.rookit.dm.artist.external;

import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

final class MutableExternalIdentifiersImpl implements MutableExternalIdentifiers {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableExternalIdentifiersImpl.class);

    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;
    private final String isni;

    @Nullable
    private String ipi;

    MutableExternalIdentifiersImpl(final Failsafe failsafe,
                                   final OptionalFactory optionalFactory,
                                   final String isni) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
        this.isni = isni;
    }

    @Override
    public void setIpi(final String ipi) {
        this.failsafe.checkArgument().string().isNotBlank(logger, ipi, "ipi");
        this.ipi = ipi;
    }

    @Override
    public Optional<String> ipi() {
        return this.optionalFactory.ofNullable(this.ipi);
    }

    @Override
    public String isni() {
        return this.isni;
    }

    @Override
    public String toString() {
        return "MutableExternalIdentifiersImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                ", isni='" + this.isni + '\'' +
                ", ipi='" + this.ipi + '\'' +
                "}";
    }
}
