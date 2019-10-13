package accumulator.dm.artist;

import org.rookit.api.dm.artist.Artist;

public interface EffectiveArtistAccumulator extends ArtistAccumulator<EffectiveArtistAccumulator, Artist>,
        UnsafeMusicianAccumulator, UnsafeGroupArtisAccumulator {
}
