package org.rookit.api.dm.artist.external;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

public interface ExternalIdentifiersFactory extends RookitFactory<ExternalIdentifiers, Key> {

    ExternalIdentifiers create(String isni);

    ExternalIdentifiers create(String isni, String ipi);
}
