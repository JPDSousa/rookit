package accumulator.dm.play;

import accumulator.dm.genre.GenreableAccumulator;
import accumulator.fww.FirstWriterWinsAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.play.TypePlaylist;
import org.rookit.api.dm.play.key.PlaylistKeySetter;

public interface PlaylistAccumulator<A extends PlaylistAccumulator<A, T>, T extends Playlist>
        extends GenreableAccumulator<A, T>, Playlist, PlaylistKeySetter<Void> {

    @AccumulatorAcessor
    FirstWriterWinsAccumulator<BiStream> getImageAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<String> getNameAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<TypePlaylist> getTypeAccumulator();

}
