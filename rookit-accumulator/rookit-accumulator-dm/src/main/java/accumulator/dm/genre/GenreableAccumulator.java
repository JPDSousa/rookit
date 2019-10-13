package accumulator.dm.genre;

import accumulator.collection.growonly.GrowOnlyCollectionAccumulator;
import accumulator.dm.play.able.PlayableAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.Genreable;

import java.util.Collection;

public interface GenreableAccumulator<A extends GenreableAccumulator<A, T>, T extends Genreable>
        extends PlayableAccumulator<A, T>, Genreable {

    @AccumulatorAcessor
    GrowOnlyCollectionAccumulator<ModeAccumulator<Genre>, Genre> getGenresAccumulator();

    @Override
    default Collection<Genre> genres() {
        return getGenresAccumulator().get();
    }
}
