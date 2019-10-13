package org.rookit.api.dm.artist.timeline;

import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.Optional;

import java.time.LocalDate;

@PropertyContainer
public interface Timeline {

    @Property
    Optional<LocalDate> begin();

    @Property
    Optional<LocalDate> end();
}
