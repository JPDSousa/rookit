package org.rookit.api.dm.artist.external;

import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.Optional;

@PropertyContainer
public interface ExternalIdentifiers {

    @Property
    Optional<String> ipi();

    @Property(isSettable = true)
    String isni();
}
