package org.rookit.api.dm.album.release;

import org.immutables.value.Value;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.Optional;

import java.time.LocalDate;

@PropertyContainer
@Value.Immutable
@Value.Style(canBuild = "isBuildable")
public interface Release {

    @Property(isSettable = true)
    TypeRelease type();

    @Property
    Optional<LocalDate> date();

}
