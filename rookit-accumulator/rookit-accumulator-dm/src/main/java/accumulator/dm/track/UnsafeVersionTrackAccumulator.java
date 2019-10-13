package accumulator.dm.track;

import accumulator.collection.growonly.GrowOnlyCollectionAccumulator;
import accumulator.dm.artist.EffectiveArtistAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.key.TrackKey;
import org.rookit.api.dm.track.key.VersionTrackKeySetter;

import java.util.Collection;

public interface UnsafeVersionTrackAccumulator extends VersionTrack, VersionTrackKeySetter<Void> {

    @AccumulatorAcessor
    GrowOnlyCollectionAccumulator<EffectiveArtistAccumulator, Artist> getVersionArtistsAccumulator();

    @Override
    default Collection<Artist> getVersionArtists() {
        return getVersionArtistsAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<String> getVersionTokenAccumulator();

    @Override
    default String getVersionToken() {
        return getVersionTokenAccumulator()
                .getOrFallback(() -> TrackKey.NO_VERSION_TOKEN,
                        "No version token found, using {}'s default: {}",
                        TrackKey.class,
                        TrackKey.NO_VERSION_TOKEN);
    }

    @AccumulatorAcessor
    EffectiveTrackAccumulator getOriginalAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<TypeVersion> getVersionTypeAccumulator();

}
