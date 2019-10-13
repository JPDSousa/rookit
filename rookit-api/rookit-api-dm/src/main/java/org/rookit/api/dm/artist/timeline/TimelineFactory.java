package org.rookit.api.dm.artist.timeline;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;

import java.time.LocalDate;

public interface TimelineFactory extends RookitFactory<Timeline, Key> {

    Timeline startAt(LocalDate date);

    Timeline endAt(LocalDate date);

    Timeline between(LocalDate begin, LocalDate end);

}
