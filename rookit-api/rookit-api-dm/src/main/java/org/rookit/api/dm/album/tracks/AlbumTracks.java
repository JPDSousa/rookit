package org.rookit.api.dm.album.tracks;

import com.google.common.collect.Lists;
import org.rookit.api.dm.album.disc.Disc;
import org.rookit.api.dm.track.Track;
import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.utils.optional.Optional;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@PropertyContainer
public interface AlbumTracks extends Iterable<Track> {

    AlbumTrackSlotsAdapter asSlots();

    Optional<Disc> disc(String discName);

    boolean contains(String discName);

    boolean contains(Track track);

    /**
     * Return a withProperty of discs with the discs on the album.
     *
     * @return a withProperty of the album discs.
     */
    @Property
    Collection<Disc> discs();

    default Collection<Track> asCollection() {
        final List<Track> tracks = Lists.newArrayListWithCapacity(size());

        for (final Disc disc : discs()) {
            tracks.addAll(disc.asTrackCollection());
        }

        return Collections.unmodifiableCollection(tracks);
    }

    /**
     * Returns the primitive of tracks in the entire album. This field will return
     * the sum of the primitive of track on each of the discs of this album.
     *
     * @return primitive of tracks in the entire album.
     */
    int size();

    Optional<Duration> duration();

    Stream<Track> stream();

}
