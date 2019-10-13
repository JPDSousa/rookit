package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.storage.ArtistDataStore;
import org.rookit.parser.format.SuspiciousTokens;
import org.rookit.parser.utils.artist.ArtistNameInterpreter;
import org.rookit.utils.string.StringUtils;

import java.util.Collection;

import static org.rookit.parser.format.field.InitialScore.L2_VALUE;

/**
 * Main artist(s)
 * <p>Note: will be automatically separated, so no need for manual
 * separation.
 */
final class ArtistField extends AbstractArtistField {

    static final String NAME = "ARTIST";

    @Inject
    private ArtistField(final StringUtils stringUtils,
                        final ArtistDataStore artistDataStore,
                        final ArtistFactory artistFactory,
                        final ArtistNameInterpreter artistNameParser,
                        @SuspiciousTokens final String[] suspiciousNameTokens) {
        super(stringUtils, L2_VALUE, artistDataStore, artistFactory, artistNameParser, suspiciousNameTokens);
    }


    @Override
    protected void applyArtists(final TrackSlotAccumulator trackSlotAccumulator, final Collection<Artist> artists) {
        trackSlotAccumulator.getTrackAccumulator().addMainArtists(artists);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
