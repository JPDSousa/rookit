
package org.rookit.dm.play;

import com.google.common.base.MoreObjects;
import one.util.streamex.StreamEx;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.failsafe.Failsafe;

@SuppressWarnings("javadoc")
final class TrackQueryDynamicPlaylistImpl extends AbstractPlaylist implements DynamicPlaylist {

    private final TrackQuery trackQuery;


    TrackQueryDynamicPlaylistImpl(final Genreable genreable,
                                  final String name,
                                  final TrackQuery trackQuery,
                                  final BiStream picture,
                                  final Failsafe failsafe) {
        super(genreable, name, picture, failsafe);
        this.trackQuery = trackQuery;
    }

    @Override
    public StreamEx<Track> streamTracks() {
        return this.trackQuery
                .execute()
                .stream();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("trackQuery", this.trackQuery)
                .toString();
    }

    @Override
    public TypePlaylist type() {
        return TypePlaylist.DYNAMIC;
    }
}
