package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.storage.ArtistDataStore;
import org.rookit.parser.utils.artist.ArtistNameInterpreter;
import org.rookit.utils.string.StringUtils;

import java.util.Collection;

import static org.rookit.parser.format.field.InitialScore.L2_VALUE;

final class ProducerField extends AbstractArtistField {

    static final String NAME = "PRODUCER";

    @Inject
    private ProducerField(final StringUtils stringUtils,
                          final ArtistDataStore artistDataStore,
                          final ArtistFactory artistFactory,
                          final ArtistNameInterpreter artistNameParser,
                          final String[] suspiciousNameTokens) {
        super(stringUtils, L2_VALUE, artistDataStore, artistFactory, artistNameParser, suspiciousNameTokens);
    }

    @Override
    protected void applyArtists(final TrackSlotAccumulator trackSlotAccumulator, final Collection<Artist> artists) {
        trackSlotAccumulator.getTrackAccumulator().addProducers(artists);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
