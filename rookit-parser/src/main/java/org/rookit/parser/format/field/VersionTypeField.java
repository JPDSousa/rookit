package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.inject.Inject;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.utils.optional.OptionalUtils;
import org.rookit.utils.optional.OptionalUtilsImpl;
import org.rookit.utils.string.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

final class VersionTypeField extends AbstractField {

    static final String NAME = "VERSION";

    private final OptionalUtils optionalUtils;

    @Inject
    private VersionTypeField(final StringUtils stringUtils, final OptionalUtils optionalUtils) {
        super(stringUtils, InitialScore.L2_VALUE);
        this.optionalUtils = optionalUtils;
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        values.stream()
                .map(TypeVersion::parse)
                .forEach(trackSlotAccumulator.getTrackAccumulator()::setVersionType);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        final Optional<String> tokenOrNone = Arrays.stream(TypeVersion.values())
                .map(TypeVersion::getTokens)
                .flatMap(Arrays::stream)
                .filter(value::equalsIgnoreCase)
                .findFirst();

        return this.optionalUtils.mapToInt(tokenOrNone, token -> super.getScore(value, context))
                .orElse(SEVERE);
    }
}
