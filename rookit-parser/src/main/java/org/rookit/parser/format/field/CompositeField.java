package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.rookit.parser.config.ParserConfiguration;

import java.util.Collection;
import java.util.List;

final class CompositeField implements Field {

    private final int initialScore;
    private final Collection<Field> subFields;
    private final Joiner joiner;

    CompositeField(final InitialScore initialScore, final Collection<Field> subFields, final Joiner joiner) {
        this.initialScore = initialScore.getScore();
        this.subFields = ImmutableList.copyOf(subFields);
        this.joiner = joiner;
    }

    @Override
    public int getInitialScore() {
        return this.initialScore;
    }

    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        return this.subFields.stream()
                .mapToInt(field -> field.getScore(value, context))
                .sum();
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        this.subFields.forEach(field -> field.setField(trackSlotAccumulator, values));
    }

    @Override
    public boolean isValidValue(final String value) {
        return this.subFields.stream()
                .allMatch(field -> field.isValidValue(value));
    }

    @Override
    public String getName() {
        return this.joiner.join(Iterables.transform(this.subFields, Field::getName));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("initialScore", this.initialScore)
                .add("subFields", this.subFields)
                .add("joiner", this.joiner)
                .toString();
    }
}
