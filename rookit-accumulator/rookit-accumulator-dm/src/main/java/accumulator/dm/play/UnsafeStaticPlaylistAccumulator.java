package accumulator.dm.play;

import accumulator.collection.growonly.GrowOnlyCollectionAccumulator;
import accumulator.dm.track.EffectiveTrackAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.play.StaticPlaylist;
import org.rookit.api.dm.track.Track;

import java.util.Collection;
import java.util.stream.Stream;

public interface UnsafeStaticPlaylistAccumulator extends StaticPlaylist {

    @AccumulatorAcessor
    GrowOnlyCollectionAccumulator<EffectiveTrackAccumulator, Track> getTracksAccumulator();

    @Override
    default boolean contains(final Track track) {
        return getTracksAccumulator().contains(track);
    }

    @Override
    default Collection<Track> tracks() {
        return getTracksAccumulator().get();
    }

    @Override
    default int size() {
        return getTracksAccumulator().size();
    }

    @Override
    default Stream<Track> streamTracks() {
        return getTracksAccumulator().stream();
    }
}
