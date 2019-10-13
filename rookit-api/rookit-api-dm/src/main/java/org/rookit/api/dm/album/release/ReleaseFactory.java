package org.rookit.api.dm.album.release;

import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

import java.time.LocalDate;

public interface ReleaseFactory extends RookitFactory<Release, Key> {

    Release releaseOf(TypeRelease release);

    Release released(TypeRelease type, LocalDate date);

    Release releasedToday(TypeRelease release);
}
