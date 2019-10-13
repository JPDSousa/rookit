package org.rookit.dm.artist.external;

import com.google.inject.Inject;
import org.dozer.Mapper;
import org.rookit.api.dm.artist.external.ExternalIdentifiers;
import org.rookit.api.dm.artist.external.ExternalIdentifiersFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;

final class MutableExternalIdentifiersFactoryImpl implements MutableExternalIdentifiersFactory {

    private final ExternalIdentifiersFactory factory;
    private final Mapper mapper;
    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    @Inject
    MutableExternalIdentifiersFactoryImpl(final ExternalIdentifiersFactory factory,
                                          final Mapper mapper,
                                          final Failsafe failsafe,
                                          final OptionalFactory optionalFactory) {
        this.factory = factory;
        this.mapper = mapper;
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    private MutableExternalIdentifiers fromExternalIdentifiers(final ExternalIdentifiers identifiers) {
        if (identifiers instanceof  MutableExternalIdentifiers) {
            return (MutableExternalIdentifiers) identifiers;
        }
        final MutableExternalIdentifiers mutableIdentifiers = new MutableExternalIdentifiersImpl(this.failsafe,
                this.optionalFactory, identifiers.isni());
        this.mapper.map(identifiers, mutableIdentifiers);
        return mutableIdentifiers;
    }

    @Override
    public MutableExternalIdentifiers create(final String isni) {
        return fromExternalIdentifiers(this.factory.create(isni));
    }

    @Override
    public MutableExternalIdentifiers create(final String isni, final String ipi) {
        return fromExternalIdentifiers(this.factory.create(isni, ipi));
    }

    @Override
    public MutableExternalIdentifiers create(final Key key) {
        return fromExternalIdentifiers(this.factory.create(key));
    }

    @Override
    public MutableExternalIdentifiers createEmpty() {
        return fromExternalIdentifiers(this.factory.createEmpty());
    }

    @Override
    public String toString() {
        return "MutableExternalIdentifiersFactoryImpl{" +
                "factory=" + this.factory +
                ", mapper=" + this.mapper +
                ", injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
