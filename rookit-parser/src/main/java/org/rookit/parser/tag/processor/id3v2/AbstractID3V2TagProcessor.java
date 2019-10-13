package org.rookit.parser.tag.processor.id3v2;

import accumulator.dm.album.AlbumAccumulator;
import accumulator.dm.track.EffectiveTrackAccumulator;
import accumulator.dm.track.audio.AudioFeatureAccumulator;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.tag.processor.AbstractTagProcessor;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractID3V2TagProcessor extends AbstractTagProcessor implements ID3v2TagProcessor {

    protected AbstractID3V2TagProcessor(final ArtistFactory artistFactory,
                                        final TagProcessorConfiguration configuration) {
        super(artistFactory, configuration);
    }

    @Override
    public TrackSlotParserResult process(final TrackSlotParserResult result) {
        final AlbumAccumulator albumAccumulator = result.getAlbumAccumulator();
        final EffectiveTrackAccumulator trackAccumulator = result.getTrackAccumulator();

        getAlbum().ifPresent(albumAccumulator::setTitle);
        getAttachedPicture().stream().findFirst().ifPresent(albumAccumulator::setCover);
        getDate().ifPresent(albumAccumulator::setReleaseDate);
        getOriginalReleaseYear().ifPresent(albumAccumulator::setReleaseDate);

        getTime().ifPresent(trackAccumulator::setDuration);
        getLength().ifPresent(trackAccumulator::setDuration);

        final Optional<String> originalTitleOrNone = getOriginalTitle();
        originalTitleOrNone.ifPresent(trackAccumulator::setTitle);
        originalTitleOrNone.ifPresent(albumAccumulator::setTitle);

        final Optional<String> titleOrNone = getTitle();
        titleOrNone.ifPresent(trackAccumulator::setTitle);
        titleOrNone.ifPresent(albumAccumulator::setTitle);

        final Collection<Artist> mainArtists = getMainArtists();
        albumAccumulator.addArtists(mainArtists);
        trackAccumulator.addMainArtists(mainArtists);

        final Collection<Artist> originalArtists = getOriginalArtists();
        albumAccumulator.addArtists(originalArtists);
        trackAccumulator.addMainArtists(originalArtists);

        trackAccumulator.setVersionArtists(getVersionArtists());
        getContentType().ifPresent(trackAccumulator::setVersionType);

        getDisc().ifPresent(result::setDisc);
        getTrackNumber().ifPresent(result::setNumber);

        final AudioFeatureAccumulator audioFeatures = trackAccumulator.getAudioFeaturesAccumulator();
        getBPM().ifPresent(audioFeatures::setBpm);
        getInitialKey().ifPresent(audioFeatures::setTrackKey);

        // getISRC()

        trackAccumulator.addProducers(getComposers());

        return result;
    }

}
