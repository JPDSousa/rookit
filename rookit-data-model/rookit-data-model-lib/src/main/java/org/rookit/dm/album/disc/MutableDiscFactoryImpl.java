package org.rookit.dm.album.disc;

import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.album.disc.DiscFactory;
import org.rookit.api.dm.album.slot.TrackSlotFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.supplier.SupplierUtils;

final class MutableDiscFactoryImpl implements MutableDiscFactory {

    private final Failsafe failsafe;
    private final DiscFactory discFactory;
    private final TrackSlotFactory trackSlotFactory;
    private final Mapper mapper;
    private final SupplierUtils supplierUtils;
    private final OptionalFactory optionalFactory;

    @Inject
    private MutableDiscFactoryImpl(final Failsafe failsafe,
                                   final DiscFactory discFactory,
                                   final TrackSlotFactory trackSlotFactory,
                                   final Mapper mapper,
                                   final SupplierUtils supplierUtils,
                                   final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.discFactory = discFactory;
        this.trackSlotFactory = trackSlotFactory;
        this.mapper = mapper;
        this.supplierUtils = supplierUtils;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public MutableDisc create(final String discName) {
        return fromDisc(this.discFactory.create(discName));
    }

    private MutableDisc fromDisc(final Disc disc) {
        if (disc instanceof MutableDisc) {
            return (MutableDisc) disc;
        }
        final MutableDisc mutableDisc = new MutableDiscImpl(disc.name(),
                this.trackSlotFactory,
                this.optionalFactory,
                this.supplierUtils,
                this.failsafe);
        this.mapper.map(disc, mutableDisc);

        return mutableDisc;
    }

    @Override
    public MutableDisc create(final Key key) {
        return fromDisc(this.discFactory.create(key));
    }

    @Override
    public MutableDisc createEmpty() {
        return fromDisc(this.discFactory.createEmpty());
    }

    @Override
    public String toString() {
        return "MutableDiscFactoryImpl{" +
                "injector=" + this.failsafe +
                ", discFactory=" + this.discFactory +
                ", trackSlotFactory=" + this.trackSlotFactory +
                ", mapper=" + this.mapper +
                ", supplierUtils=" + this.supplierUtils +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
