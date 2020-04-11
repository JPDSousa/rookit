package org.rookit.api.dm.track.title;

import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.Optional;

@PropertyContainer
public interface Title {

    @Property(mutable = true)
    String title();

    Optional<String> artists();

    Optional<String> extras();

    Optional<String> features();

    Optional<String> hiddenTrack();
}
