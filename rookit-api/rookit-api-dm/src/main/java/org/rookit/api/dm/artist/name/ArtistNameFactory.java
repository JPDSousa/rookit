package org.rookit.api.dm.artist.name;

import org.rookit.api.dm.factory.RookitFactory;

public interface ArtistNameFactory extends RookitFactory<ArtistName, ArtistNameKey> {

    ArtistName create(String official);

}
