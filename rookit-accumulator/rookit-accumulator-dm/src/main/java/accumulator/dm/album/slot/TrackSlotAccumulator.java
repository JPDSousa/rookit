package accumulator.dm.album.slot;

import accumulator.SingleAccumulator;
import accumulator.dm.DataModelAccumulatorFactory;
import accumulator.dm.album.AlbumAccumulator;
import accumulator.dm.track.EffectiveTrackAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.album.slot.TrackSlot;
import org.rookit.api.dm.track.Track;

import java.util.Optional;

public interface TrackSlotAccumulator extends TrackSlot,
        SingleAccumulator<TrackSlotAccumulator, TrackSlot, DataModelAccumulatorFactory> {

    @AccumulatorAcessor
    EffectiveTrackAccumulator getTrackAccumulator();

    @Override
    default Optional<Track> track() {
        return getTrackAccumulator().get();
    }

    @AccumulatorAcessor
    EffectiveTrackAccumulator getHiddenTrackAccumulator();

    @Override
    default Optional<Track> getHiddenTrack() {
        return getHiddenTrackAccumulator().get();
    }

    @AccumulatorAcessor
    AlbumAccumulator getAlbumAccumulator();

    TrackSlotAccumulator createTrackStage();

    TrackSlotAccumulator createAlbumStage();

    void setNumber(final int number);

    void setDisc(final CharSequence disc);

}
