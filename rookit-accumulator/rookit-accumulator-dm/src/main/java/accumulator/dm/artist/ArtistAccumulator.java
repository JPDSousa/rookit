package accumulator.dm.artist;

import com.neovisionaries.i18n.CountryCode;
import accumulator.collection.growonly.GrowOnlyCollectionAccumulator;
import accumulator.dm.genre.GenreableAccumulator;
import accumulator.fww.FirstWriterWinsAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.key.ArtistKeySetter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface ArtistAccumulator<A extends ArtistAccumulator<A, T>, T extends Artist>
        extends GenreableAccumulator<A, T>, Artist, ArtistKeySetter<Void> {

    @AccumulatorAcessor
    GrowOnlyCollectionAccumulator<ModeAccumulator<String>, String> getAliasesAccumulator();

    @Override
    default Collection<String> aliases() {
        return getAliasesAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<LocalDate> getBeginDateAccumulator();

    @Override
    default Optional<LocalDate> getBeginDate() {
        return getBeginDateAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<LocalDate> getEndDateAccumulator();

    @Override
    default Optional<LocalDate> getEndDate() {
        return getEndDateAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<String> getIPIAccumulator();

    @Override
    default Optional<String> profile().externalIdentifiers().ipi() {
        return getIPIAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<String> getISNIAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<String> getNameAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<CountryCode> getOriginAccumulator();

    @Override
    default Optional<CountryCode> origin() {
        return getOriginAccumulator().get();
    }

    @AccumulatorAcessor
    FirstWriterWinsAccumulator<BiStream> getPictureAccumulator();


    @AccumulatorAcessor
    GrowOnlyCollectionAccumulator<EffectiveArtistAccumulator, Artist> getRelatedArtistsAccumulator();

    @Override
    default Collection<Artist> relatedArtists() {
        return getRelatedArtistsAccumulator().get();
    }

}
