package accumulator.dm.artist;

import accumulator.collection.growonly.GrowOnlyCollectionAccumulator;
import accumulator.opaque.ModeAccumulator;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.api.dm.artist.key.GroupArtistKeySetter;

import java.util.Collection;

public interface UnsafeGroupArtisAccumulator extends GroupArtist, GroupArtistKeySetter<Void> {

    ModeAccumulator<TypeGroup> getGroupTypeAccumulator();

    @Override
    default TypeGroup getGroupType() {
        return getGroupTypeAccumulator().getOrFallback(() -> ArtistKey.DEFAULT_GROUP,
                "Cannot resolve group release. Falling back to {}'s default", ArtistKey.class);
    }

    GrowOnlyCollectionAccumulator<MusicianAccumulator, Musician> getMembersAccumulator();

    @Override
    default Collection<Musician> getMembers() {
        return getMembersAccumulator().get();
    }
}
