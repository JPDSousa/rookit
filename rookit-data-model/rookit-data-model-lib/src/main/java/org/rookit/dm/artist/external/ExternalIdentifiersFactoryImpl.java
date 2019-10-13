package org.rookit.dm.artist.external;

import com.google.inject.Inject;
import org.rookit.api.dm.artist.external.ExternalIdentifiers;
import org.rookit.api.dm.artist.external.ExternalIdentifiersFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.LoggerFactory;

final class ExternalIdentifiersFactoryImpl implements ExternalIdentifiersFactory {

    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExternalIdentifiersFactoryImpl.class);

    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;
    
    @Inject
    private ExternalIdentifiersFactoryImpl(final Failsafe failsafe, final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public ExternalIdentifiers create(final String isni) {
        this.failsafe.checkArgument().string().isNotBlank(logger, isni, "isni");
        return new MutableExternalIdentifiersImpl(this.failsafe, this.optionalFactory, isni);
    }

    @Override
    public ExternalIdentifiers create(final String isni, final String ipi) {
        this.failsafe.checkArgument().string().isNotBlank(logger, isni, "isni");
        this.failsafe.checkArgument().string().isNotBlank(logger, ipi, "ipi");
        final MutableExternalIdentifiers externalIdentifiers = new MutableExternalIdentifiersImpl(this.failsafe,
                this.optionalFactory, isni);
        externalIdentifiers.setIpi(isni);
        return externalIdentifiers;
    }

    @Override
    public ExternalIdentifiers create(final Key key) {
        logger.info("Creation by key is not supported, creating empty instead");
        return createEmpty();
    }

    @Override
    public ExternalIdentifiers createEmpty() {
        final String errorMessage = "Cannot create an empty externalIdentifiers as ISNI is required";
        return this.failsafe.handleException().unsupportedOperation(errorMessage);
    }

    @Override
    public String toString() {
        return "ExternalIdentifiersFactoryImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
