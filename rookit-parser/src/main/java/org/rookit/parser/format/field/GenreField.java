package org.rookit.parser.format.field;

import accumulator.dm.album.slot.TrackSlotAccumulator;
import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.api.storage.GenreDataStore;
import org.rookit.parser.config.ParserConfiguration;
import org.rookit.utils.string.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class GenreField extends AbstractField {

    static final String NAME = "GENRE";
    private final GenreDataStore genreDataStore;
    private final GenreFactory factory;

    @Inject
    private GenreField(final StringUtils stringUtils,
                       final GenreDataStore genreDataStore,
                       final GenreFactory factory) {
        super(stringUtils, InitialScore.L1_VALUE);
        this.genreDataStore = genreDataStore;
        this.factory = factory;
    }

    @Override
    public void setField(final TrackSlotAccumulator trackSlotAccumulator,
                         final List<String> values) {
        final Collection<Genre> genres = values.stream()
                .map(this.factory::createGenre)
                .collect(Collectors.toSet());

        trackSlotAccumulator.getTrackAccumulator().addGenres(genres);
        trackSlotAccumulator.getAlbumAccumulator().addGenres(genres);
    }

    @Override
    public String getName() {
        return NAME;
    }


    @Override
    public int getScore(final String value, final ParserConfiguration context) {
        final Optional<Genre> genreOrNone = this.genreDataStore
                .query()
                .withName(value)
                .execute()
                .first();
        final int dbScore = genreOrNone.isPresent() ? 10 : -1;

        return super.getScore(value, context) + dbScore;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("genreDataStore", this.genreDataStore)
                .add("factory", this.factory)
                .toString();
    }
}
