package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.apache.commons.text.WordUtils;
import org.rookit.utils.string.StringUtils;

import java.util.List;

final class AlbumField extends AbstractField {

    static final String NAME = "ALBUM";

    @Inject
    private AlbumField(final StringUtils stringUtils) {
        super(stringUtils, InitialScore.L2_VALUE);
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        for (final String value : values) {
            final String title = WordUtils.capitalizeFully(value);

            trackSlotAccumulator.getTrackAccumulator().setTitle(title);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
