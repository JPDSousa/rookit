package org.rookit.api.dm.album.disc;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.track.Track;
import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.Optional;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Stream;

@PropertyContainer
public interface Disc extends Iterable<Track> {

    @Property(isSettable = true)
    String name();

    Optional<Track> track(int number);

    TrackSlot trackAsSlot(int number);

    int size();

    default Duration duration() {
        return asTrackCollection()
                .stream()
                .map(Track::duration)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Property(isSettable = true)
    Int2ObjectMap<Track> trackMap();

    Collection<Track> asTrackCollection();

    Collection<TrackSlot> asTrackSlotsCollection();

    boolean contains(Track track);

    boolean contains(int trackNumber);

    Stream<Track> stream();

}
