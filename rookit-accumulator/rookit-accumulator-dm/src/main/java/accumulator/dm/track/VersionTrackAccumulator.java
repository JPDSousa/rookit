package accumulator.dm.track;

import org.rookit.api.dm.track.VersionTrack;

public interface VersionTrackAccumulator
        extends TrackAccumulator<VersionTrackAccumulator, VersionTrack>, UnsafeVersionTrackAccumulator {
}
