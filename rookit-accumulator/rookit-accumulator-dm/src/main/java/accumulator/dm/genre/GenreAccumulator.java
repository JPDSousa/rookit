package accumulator.dm.genre;

import accumulator.dm.play.able.PlayableAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import accumulator.string.StringBufferAccumulator;
import org.rookit.api.dm.genre.Genre;

import java.util.Optional;

public interface GenreAccumulator extends PlayableAccumulator<GenreAccumulator, Genre>, Genre {

    @AccumulatorAcessor
    StringBufferAccumulator getDescriptionAccumulator();

    @Override
    default Optional<String> description() {
        return getDescriptionAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<String> getNameAccumulator();

}
