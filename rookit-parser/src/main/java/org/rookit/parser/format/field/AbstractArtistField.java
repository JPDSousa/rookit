package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ArrayUtils;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGender;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.api.storage.ArtistDataStore;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.parser.utils.artist.ArtistNameInterpreter;
import org.rookit.utils.string.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

abstract class AbstractArtistField extends AbstractField {

    private final ArtistKey defaultKey;
    private final ArtistDataStore artistDataStore;
    private final ArtistFactory artistFactory;
    private final ArtistNameInterpreter artistNameInterpreter;
    private final String[] suspiciousNameTokens;

    AbstractArtistField(final StringUtils stringUtils,
                        final InitialScore score,
                        final ArtistDataStore artistDataStore,
                        final ArtistFactory artistFactory,
                        final ArtistNameInterpreter artistNameParser,
                        final String[] suspiciousNameTokens) {
        super(stringUtils, score);
        this.artistDataStore = artistDataStore;
        this.artistFactory = artistFactory;
        this.artistNameInterpreter = artistNameParser;
        this.suspiciousNameTokens = ArrayUtils.clone(suspiciousNameTokens);
        this.defaultKey = ImmutableArtistKey.builder()
                // TODO #26 make this configurable
                .type(TypeArtist.GROUP)
                .groupType(TypeGroup.DEFAULT)
                .gender(TypeGender.OTHER)
                .name("Unknown")
                .isni("Unknown")
                .build();
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator, final List<String> values) {
        final Collection<Artist> artists = values.stream()
                .flatMap(this.artistNameInterpreter::interpretAndStream)
                .map(this::createKey)
                .map(this.artistFactory::create)
                .collect(Collectors.toSet());

        applyArtists(trackSlotAccumulator, artists);
    }

    private ArtistKey createKey(final String name) {
        return ImmutableArtistKey.builder()
                .from(this.defaultKey)
                .name(name)
                .build();
    }

    protected abstract void applyArtists(TrackSlotAccumulator trackSlotAccumulator, final Collection<Artist> artists);

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        int dbScore = 0;
        final boolean isValid = Arrays.stream(this.suspiciousNameTokens)
                .noneMatch(value::contains);

        if(isValid) {
            final Optional<Artist> artist = this.artistDataStore.query()
                    .withProfileNameOfficial(value)
                    .execute()
                    .first();
            dbScore = artist.isPresent() ? 10 : -1;
        }

        return super.getScore(value, context)+dbScore + (isValid ? 0 : SEVERE);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("defaultKey", this.defaultKey)
                .add("artistDataStore", this.artistDataStore)
                .add("artistFactory", this.artistFactory)
                .add("artistNameInterpreter", this.artistNameInterpreter)
                .add("suspiciousNameTokens", this.suspiciousNameTokens)
                .toString();
    }
}
