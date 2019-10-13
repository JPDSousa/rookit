package accumulator.dm.artist;

import accumulator.opaque.ModeAccumulator;
import org.rookit.accumulator.staging.AccumulatorAcessor;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeGender;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.api.dm.artist.key.MusicianKeySetter;

public interface UnsafeMusicianAccumulator extends Musician, MusicianKeySetter<Void> {

    @Override
    default String getFullName() {
        return getFullNameAccumulator().getOrFallback(this::name,
                "Cannot resolve full official. Falling back to official.");
    }

    @Override
    default TypeGender getGender() {
        return getGenderAccumulator().getOrFallback(() -> ArtistKey.DEFAULT_GENDER,
                "Cannot resolve gender. Falling back to {}'s default", ArtistKey.class);
    }

    @AccumulatorAcessor
    ModeAccumulator<String> getFullNameAccumulator();

    @AccumulatorAcessor
    ModeAccumulator<TypeGender> getGenderAccumulator();
}
