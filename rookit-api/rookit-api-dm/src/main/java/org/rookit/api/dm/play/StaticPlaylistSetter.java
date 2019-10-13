package org.rookit.api.dm.play;

import org.rookit.api.dm.track.Track;

import java.util.Collection;

public interface StaticPlaylistSetter {

    boolean addTrack(Track track);

    boolean addTracks(Collection<? extends Track> tracks);

    void clear();

    boolean removeTrack(Track o);

    boolean removeTracks(Collection<? extends Track> tracks);

    void setTracks(Collection<? extends Track> tracks);
}
