
package org.rookit.parser.tag.jaudiotagger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.kekstudio.musictheory.Key;
import com.neovisionaries.i18n.LanguageCode;
import one.util.streamex.StreamEx;
import org.apache.logging.log4j.Logger;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.ID3v24FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.parser.tag.processor.id3v2.AbstractID3V2TagProcessor;
import org.rookit.parser.tag.processor.TagProcessorUtils;
import org.rookit.parser.tag.audiofile.AudioFileType;
import org.rookit.parser.tag.audiofile.StandardAudioFileType;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;
import org.rookit.parser.tag.media.MediaType;
import org.rookit.parser.tag.media.StandardMediaType;
import org.rookit.utils.optional.OptionalUtilsImpl;
import org.rookit.utils.primitive.ShortUtils;
import org.rookit.utils.optional.OptionalShort;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;

import static org.rookit.parser.tag.processor.TagProcessorUtils.*;

class JAudioTaggerID3v24TagProcessor extends AbstractID3V2TagProcessor {

    /**
     * Logger for JAudioTaggerID3v2TagProcessor.
     */
    private static final Logger logger = VALIDATOR.getLogger(JAudioTaggerID3v24TagProcessor.class);

    private final ID3v24Tag tags;

    JAudioTaggerID3v24TagProcessor(final ArtistFactory artistFactory,
                                   final TagProcessorConfiguration configuration,
                                   final ID3v24Tag tags) {
        super(artistFactory, configuration);
        this.tags = tags;
    }

    @Override
    public Optional<String> getAccompaniment() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_ACCOMPANIMENT));
    }

    @Override
    public Optional<String> getAlbum() {
        return prepareTitle(this.tags.getFirst(ID3v24Frames.FRAME_ID_ALBUM));
    }

    @Override
    public Collection<byte[]> getAttachedPicture() {
        return StreamEx.of(this.tags.getArtworkList())
                .filter(artwork -> !artwork.isLinked())
                .map(Artwork::getBinaryData)
                .toImmutableList();
    }

    @Override
    public AudioFileType getAudioFileType() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_FILE_TYPE))
                .flatMap(StandardAudioFileType::parseByCode)
                .orElse(StandardAudioFileType.MPEG);
    }

    @Override
    public OptionalShort getBPM() {
        final Optional<String> bpmOrNone = prepareString(this.tags.getFirst(ID3v24FieldKey.BPM))
                .filter(ShortUtils::isCastable);

        return OptionalUtilsImpl.mapToShort(bpmOrNone, Short::parseShort);
    }

    @Override
    public Collection<Artist> getComposers() {
        return createArtists(this.tags.getFirst(ID3v24FieldKey.COMPOSER));
    }

    @Override
    public Collection<Artist> getConductor() {
        return createArtists(this.tags.getFirst(ID3v24Frames.FRAME_ID_CONDUCTOR));
    }

    @Override
    public Optional<String> getContentGroupDescription() {
        return prepareTitle(this.tags.getFirst(ID3v24FieldKey.GROUPING));
    }

    @Override
    public Optional<TypeVersion> getContentType() {
        // TODO Resolve TypeVersion and Genre
        logger.warn("This field is not implemented yet. It lacks the proper logic to"
                + " split release versions fromRelease genre");
        return Optional.empty();
    }

    @Override
    public Optional<String> getCopyright() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_COPYRIGHTINFO));
    }

    @Override
    public Optional<LocalDate> getDate() {
        return prepareString(this.tags.getFirst("TDAT"))
                .flatMap(this::parseDate);
    }

    @Override
    public Optional<String> getDisc() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_SET));
    }

    @Override
    public Optional<String> getEncodedBy() {
        return prepareString(this.tags.getFirst(ID3v24FieldKey.ENCODER));
    }

    @Override
    public Optional<String> getFileOwner() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_FILE_OWNER));
    }

    @Override
    public Optional<Key> getInitialKey() {
        final String tagValue = this.tags.getFirst(ID3v24FieldKey.KEY);
        // TODO parse the key

        logger.warn("This is not implemented. Raw value is: {}", tagValue);
        return Optional.empty();
    }

    @Override
    public Optional<String> getInternetRadioStationName() {
        return prepareTitle(this.tags.getFirst(ID3v24Frames.FRAME_ID_RADIO_NAME));
    }

    @Override
    public Optional<String> getInternetRadioStationOwner() {
        return prepareTitle(this.tags.getFirst(ID3v24Frames.FRAME_ID_RADIO_OWNER));
    }

    @Override
    public Optional<String> getISRC() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_ISRC));
    }

    @Override
    public Collection<LanguageCode> getLanguages() {
        return splitTag(this.tags.getFirst(ID3v24Frames.FRAME_ID_LANGUAGE))
                .map(LanguageCode::getByCodeIgnoreCase)
                .toImmutableSet();
    }

    @Override
    public Optional<Duration> getLength() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_LENGTH))
                .flatMap(TagProcessorUtils::parseDuration);
    }

    @Override
    public Collection<Artist> getMainArtists() {
        return createArtists(this.tags.getFirst(ID3v24Frames.FRAME_ID_ARTIST));
    }

    @Override
    public Optional<MediaType> getMediaType() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_MEDIA_TYPE))
                .flatMap(StandardMediaType::parseByCode);
    }

    @Override
    public Collection<Artist> getOriginalArtists() {
        return createArtists(this.tags.getFirst(ID3v24Frames.FRAME_ID_ORIGARTIST));
    }

    @Override
    public Optional<String> getOriginalFileName() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_ORIG_FILENAME));
    }

    @Override
    public Optional<LocalDate> getOriginalReleaseYear() {
        return prepareString(this.tags.getFirst("TORY"))
                .flatMap(this::parseDate);
    }

    @Override
    public Collection<Artist> getOriginalTextWriters() {
        return createArtists(this.tags.getFirst(ID3v24Frames.FRAME_ID_ORIG_LYRICIST));
    }

    @Override
    public Optional<String> getOriginalTitle() {
        return prepareTitle(this.tags.getFirst(ID3v24Frames.FRAME_ID_ORIG_TITLE));
    }

    @Override
    public Optional<Duration> getPlaylistDelay() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_PLAYLIST_DELAY))
                .flatMap(TagProcessorUtils::parseDuration);
    }

    @Override
    public Optional<String> getPublisher() {
        return prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_PUBLISHER));
    }

    @Override
    public Collection<LocalDate> getRecordingDates() {
        return prepareString(this.tags.getFirst("TRDA"))
                .flatMap(this::parseDate)
                .map(ImmutableSet::of)
                .orElse(ImmutableSet.of());
    }

    @Override
    public OptionalInt getSize() {
        return OptionalUtilsImpl.mapToInt(prepareString(this.tags.getFirst("TSIZ")), Integer::parseInt);
    }

    @Override
    public Optional<String> getSubtitle() {
        return prepareTitle(this.tags.getFirst(ID3v24Frames.FRAME_ID_TITLE_REFINEMENT));
    }

    @Override
    public Collection<Artist> getTextWriters() {
        return createArtists(this.tags.getFirst(ID3v24FieldKey.LYRICIST));
    }

    @Override
    public Optional<Duration> getTime() {
        return prepareString(this.tags.getFirst("TIME"))
                .flatMap(TagProcessorUtils::parseDuration);
    }

    @Override
    public Optional<String> getTitle() {
        return prepareTitle(this.tags.getFirst(ID3v24FieldKey.TITLE));
    }

    @Override
    public OptionalInt getTrackNumber() {
        return OptionalUtilsImpl.mapToInt(prepareString(this.tags.getFirst(ID3v24Frames.FRAME_ID_TRACK)),
                Integer::parseInt);
    }

    @Override
    public Collection<Artist> getVersionArtists() {
        return createArtists(this.tags.getFirst(ID3v24Frames.FRAME_ID_REMIXED));
    }

    @Override
    public void close() {
        logger.trace("Nothing to close");
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tags", this.tags)
                .toString();
    }
}
