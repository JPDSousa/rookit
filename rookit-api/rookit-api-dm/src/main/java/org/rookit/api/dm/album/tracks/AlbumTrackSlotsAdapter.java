package org.rookit.api.dm.album.tracks;

import org.rookit.api.dm.album.slot.TrackSlot;

import java.util.Collection;

public interface AlbumTrackSlotsAdapter {

    boolean contains(final TrackSlot slot);

    /**
     * Returns the list of tracks with all the tracks from all the discs.
     *
     * @return a list of tracks with all the tracks of the album
     */
    Collection<TrackSlot> asCollection();

    /**
     * Returns the withProperty of tracks from the disc passed as parameter.
     *
     * @param cd
     *            disc official
     * @return the list of tracks from the disc passed as parameter
     */
    Collection<TrackSlot> asCollection(String cd);

    TrackSlot getTrack(String discName, int number);
}
