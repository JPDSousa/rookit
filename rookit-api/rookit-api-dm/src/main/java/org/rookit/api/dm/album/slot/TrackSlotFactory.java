package org.rookit.api.dm.album.slot;

import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.Track;

public interface TrackSlotFactory extends RookitFactory<TrackSlot, Key> {

    TrackSlot copyOf(TrackSlot slot);

    TrackSlot createEmpty(String disc, int number);

    TrackSlot createWithTrack(String disc, int number, Track track);
}
