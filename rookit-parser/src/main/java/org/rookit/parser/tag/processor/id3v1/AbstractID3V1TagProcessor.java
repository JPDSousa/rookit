package org.rookit.parser.tag.processor.id3v1;

import accumulator.dm.album.AlbumAccumulator;
import accumulator.dm.track.EffectiveTrackAccumulator;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.genre.Genre;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.tag.processor.AbstractTagProcessor;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;

import java.util.Collection;

public abstract class AbstractID3V1TagProcessor extends AbstractTagProcessor implements ID3V1TagProcessor {

    protected AbstractID3V1TagProcessor(final ArtistFactory artistFactory, final TagProcessorConfiguration config) {
        super(artistFactory, config);
    }

    @Override
    public TrackSlotParserResult process(final TrackSlotParserResult result) {
        final EffectiveTrackAccumulator trackAccumulator = result.getTrackAccumulator();
        final AlbumAccumulator albumAccumulator = result.getAlbumAccumulator();

        getAlbumTitle().ifPresent(albumAccumulator::setTitle);

        final Collection<Artist> artists = getArtists();
        trackAccumulator.addMainArtists(artists);
        albumAccumulator.addArtists(artists);

        final Collection<Genre> genres = genres();
        trackAccumulator.addGenres(genres);
        albumAccumulator.addGenres(genres);

        getTitle().ifPresent(trackAccumulator::setTitle);
        getYear().ifPresent(albumAccumulator::setReleaseDate);
        // getComment();

        return result;
    }
}
