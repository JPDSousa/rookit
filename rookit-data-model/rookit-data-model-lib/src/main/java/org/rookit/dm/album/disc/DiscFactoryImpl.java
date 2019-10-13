package org.rookit.dm.album.disc;

import com.google.inject.Inject;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.disc.DiscFactory;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.dm.album.disc.config.DiscConfig;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.supplier.SupplierUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DiscFactoryImpl implements DiscFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DiscFactoryImpl.class);

    private final Failsafe failsafe;
    private final TrackSlotFactory trackSlotFactory;
    private final DiscConfig discConfig;
    private final SupplierUtils supplierUtils;
    private final OptionalFactory optionalFactory;

    @Inject
    private DiscFactoryImpl(final Failsafe failsafe,
                            final TrackSlotFactory trackSlotFactory,
                            final DiscConfig discConfig,
                            final SupplierUtils supplierUtils,
                            final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.trackSlotFactory = trackSlotFactory;
        this.discConfig = discConfig;
        this.supplierUtils = supplierUtils;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Disc create(final String name) {
        return new MutableDiscImpl(name, this.trackSlotFactory, this.optionalFactory, this.supplierUtils, this.failsafe);
    }

    @Override
    public Disc create(final Key key) {
        logger.warn("Key creation is not supported for discs. Creating empty instead");
        return createEmpty();
    }

    @SuppressWarnings("RedundantTypeArguments")
    @Override
    public Disc createEmpty() {
        return this.discConfig.defaultName()
                .map(this::create)
                .orElseThrow(() -> this.failsafe.handleException()
                        .<RuntimeException>runtimeException(
                                "Cannot create an empty Disc as no default propertyName is withProperty."));
    }

    @Override
    public String toString() {
        return "DiscFactoryImpl{" +
                "injector=" + this.failsafe +
                ", trackSlotFactory=" + this.trackSlotFactory +
                ", discConfig=" + this.discConfig +
                ", supplierUtils=" + this.supplierUtils +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
