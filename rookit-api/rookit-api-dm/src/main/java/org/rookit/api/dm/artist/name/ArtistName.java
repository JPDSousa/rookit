package org.rookit.api.dm.artist.name;

import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;

import java.util.Collection;

@PropertyContainer
public interface ArtistName extends Comparable<ArtistName> {

    @Property(isSettable = true)
    String official();

    @Property
    Collection<String> aliases();

}
