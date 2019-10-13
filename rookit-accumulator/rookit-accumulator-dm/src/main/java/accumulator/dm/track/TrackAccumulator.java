package accumulator.dm.track;

import accumulator.dm.genre.GenreableAccumulator;
import accumulator.dm.track.audio.AudioFeatureAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TrackTitle;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.audio.AudioFeature;
import org.rookit.api.dm.track.key.TrackKeySetter;
import org.rookit.utils.optional.OptionalBoolean;

import java.util.Collection;
import java.util.Optional;

public interface TrackAccumulator<A extends TrackAccumulator<A, T>, T extends Track>
        extends GenreableAccumulator<A, T>, Track, TrackKeySetter<Void> {

    @AccumulatorAcessor
    AudioFeatureAccumulator getAudioFeaturesAccumulator();

    @Override
    AudioFeature audioFeatures();

    @Override
    Optional<VersionTrack> asVersionTrack();

    @Override
    BiStream audio();

    @Override
    Collection<Artist> features();

    @Override
    TrackTitle fullTitle();

    @Override
    TrackTitle longFullTitle();

    @Override
    Optional<String> lyrics();

    @Override
    Collection<Artist> mainArtists();

    @Override
    Collection<Artist> producers();

    @Override
    TrackTitle title();

    @Override
    TypeTrack type();

    @Override
    OptionalBoolean isExplicit();

    @Override
    boolean isVersionTrack();
}
