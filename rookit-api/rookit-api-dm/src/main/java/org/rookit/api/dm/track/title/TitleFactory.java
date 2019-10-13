package org.rookit.api.dm.track.title;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

public interface TitleFactory extends RookitFactory<Title, Key> {

    Title create(String title);

}
