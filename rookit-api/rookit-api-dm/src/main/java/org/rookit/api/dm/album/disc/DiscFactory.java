package org.rookit.api.dm.album.disc;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

public interface DiscFactory extends RookitFactory<Disc, Key> {

    Disc create(String name);

}
