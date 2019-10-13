package accumulator.dm.artist;

import org.rookit.api.dm.artist.Musician;

public interface MusicianAccumulator extends ArtistAccumulator<MusicianAccumulator, Musician>,
        UnsafeMusicianAccumulator {
}
