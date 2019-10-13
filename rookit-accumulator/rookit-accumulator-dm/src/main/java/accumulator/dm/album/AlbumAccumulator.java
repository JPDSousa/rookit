package accumulator.dm.album;

import accumulator.collection.growonly.GrowOnlyCollectionAccumulator;
import accumulator.dm.artist.EffectiveArtistAccumulator;
import accumulator.dm.genre.GenreableAccumulator;
import accumulator.fww.FirstWriterWinsAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.TypeAlbum;
import org.rookit.api.dm.album.TypeRelease;
import org.rookit.api.dm.album.key.AlbumKey;
import org.rookit.api.dm.album.key.AlbumKeySetter;
import org.rookit.api.dm.artist.Artist;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface AlbumAccumulator extends GenreableAccumulator<AlbumAccumulator, Album>, Album, AlbumKeySetter<Void> {

    @Override
    default Optional<Duration> duration() {
        return Album.super.duration();
    }

    @AccumulatorAcessor
    FirstWriterWinsAccumulator<BiStream> getCoverAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<LocalDate> getReleaseDateAccumulator();

    @Override
    default Optional<LocalDate> getReleaseDate() {
        return getReleaseDateAccumulator().get();
    }

    @AccumulatorAcessor
    ModeAccumulator<TypeAlbum> getAlbumTypeAccumulator();

    @Override
    default TypeAlbum type() {
        return getAlbumTypeAccumulator().getOrFallback(() -> AlbumKey.DEFAULT_ALBUM_TYPE,
                "Album release not found. Falling back to {}'s default: {}.",
                AlbumKey.class,
                AlbumKey.DEFAULT_ALBUM_TYPE);
    }

    @AccumulatorAcessor
    ModeAccumulator<String> getTitleAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<TypeRelease> getTypeAccumulator();

    @Override
    default TypeRelease release() {
        return getTypeAccumulator().getOrFallback(() -> AlbumKey.DEFAULT_TYPE,
                "Type not found. Falling back to {}'s default: {}.",
                AlbumKey.class,
                AlbumKey.DEFAULT_TYPE);
    }

    GrowOnlyCollectionAccumulator<EffectiveArtistAccumulator, Artist> getArtistsAccumulator();

    @Override
    default Collection<Artist> artists() {
        return getArtistsAccumulator().getAsSet();
    }
}
