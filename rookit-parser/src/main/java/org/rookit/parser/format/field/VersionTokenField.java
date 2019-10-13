package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.storage.VersionTrackDataStore;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.utils.string.StringUtils;

import java.util.List;
import java.util.Optional;

final class VersionTokenField extends AbstractField {

    static final String NAME = "VTOKEN";

    private final VersionTrackDataStore dataStore;

    @Inject
    private VersionTokenField(final StringUtils stringUtils,
                              final VersionTrackDataStore dataStore) {
        super(stringUtils, InitialScore.L0_VALUE);
        this.dataStore = dataStore;
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        for (final String value : values) {
            trackSlotAccumulator.getTrackAccumulator().setVersionToken(value);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }


    @Override
    public int getScore(final String value, final ParserConfiguration config) {
        final Optional<VersionTrack> trackOrNone = this.dataStore.query()
                .withVersionToken(value)
                .execute()
                .first();
        final int dbScore = trackOrNone.isPresent() ? 10 : LOW;

        return super.getScore(value, config) + dbScore;
    }

}
