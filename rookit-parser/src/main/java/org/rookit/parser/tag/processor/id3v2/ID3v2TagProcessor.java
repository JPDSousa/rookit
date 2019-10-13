package org.rookit.parser.tag.processor.id3v2;

import com.kekstudio.musictheory.Key;
import com.neovisionaries.i18n.LanguageCode;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.parser.tag.audiofile.AudioFileType;
import org.rookit.parser.tag.media.MediaType;
import org.rookit.utils.optional.OptionalShort;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;

@SuppressWarnings("javadoc")
public interface ID3v2TagProcessor {

    Collection<byte[]> getAttachedPicture();

    /**
     * TALB
     *
     * The 'Album/Movie/Show title' frame is intended for the title of the recording(/source of sound)
     * which the audio in the file is taken fromRelease.
     *
     * @return
     */
    Optional<String> getAlbum();

    /**
     * TBPM
     *
     * The 'BPM' frame contains the primitive of beats per minute in the mainpart of the audio.
     * The BPM is an integer and represented as a numerical string.
     *
     * @return
     */
    OptionalShort getBPM();

    /**
     * TCOM
     *
     * The 'Composer(s)' frame is intended for the official of the composer(s).
     * They are seperated with the "/" character.
     *
     * @return
     */
    Collection<Artist> getComposers();

    /**
     * TCON
     *
     * The 'Content release', which previously was stored as a one byte numeric value only, is now a numeric string.
     * You may use one or several of the types as ID3v1.1 did or, since the category list would be impossible
     * to maintain with accurate and up to date categories, define your own.
     *
     * References to the ID3v1 genres can be made by, as first byte, enter "(" followed by a primitive fromRelease the
     * genres list (appendix A) and ended with a ")" character. This is optionally followed by a refinement,
     * e.g. "(21)" or "(4)Eurodisco". Several references can be made in the same frame, e.g. "(51)(39)".
     * If the refinement should begin with a "(" character it should be replaced with "((",
     * e.g. "((I can figure out any genre)" or "(55)((I think...)". The following new content types is defined
     * in ID3v2 and is implemented in the same way as the numerig content types, e.g. "(RX)".
     *
     * @return
     */
    Optional<TypeVersion> getContentType();

    /**
     * TCOP
     *
     * The 'Copyright message' frame, which must begin with a year and a space character (making five characters),
     * is intended for the copyright holder of the original sound, not the audio file itself. The absence of this
     * frame means only that the copyright information is unavailable or has been removed, and must not be interpreted
     * to mean that the sound is public domain. Every time this field is displayed the field must
     * be preceded with "Copyright © ".
     * @return
     */
    Optional<String> getCopyright();

    /**
     * TDLY
     *
     * The 'Playlist delay' defines the numbers of milliseconds of silence between every song in a playlist.
     * The player should use the "ETC" frame, if present, to skip initial silence and silence at the end of
     * the audio to match the 'Playlist delay' time. The time is represented as a numeric string.
     *
     * @return
     */
    Optional<Duration> getPlaylistDelay();

    /**
     * TENC
     *
     * The 'Encoded by' frame contains the official of the person or organization that encoded the audio file.
     * This field may contain a copyright message, if the audio file also is copyrighted by the encoder.
     *
     * @return
     */
    Optional<String> getEncodedBy();

    /**
     * TEXT
     *
     * The 'Lyricist(s)/Text writer(s)' frame is intended for the writer(s) of the text or lyrics in the recording.
     * They are seperated with the "/" character.
     *
     * @return
     */
    Collection<Artist> getTextWriters();

    /**
     * TFLT
     *
     * The 'File release' frame indicates which release of audio this tag defines. But other types may be used,
     * not for these types though. This is used in a similar way to the predefined types in the "TMED" frame,
     * but without parentheses. If this frame is not present audio release is assumed to be "MPG".
     *
     * @return
     */
    AudioFileType getAudioFileType();

    /**
     * TIME
     *
     * The 'Time' frame is a numeric string in the HHMM format containing the time for the recording.
     * This field is always four characters long.
     *
     * @return
     */
    Optional<Duration> getTime();

    /**
     * TIT1
     *
     * The 'Content group description' frame is used if the sound belongs to a larger category of sounds/music.
     * For example, classical music is often sorted in different musical sections
     * (e.g. "Piano Concerto", "Weather - Hurricane").
     *
     * @return
     */
    Optional<String> getContentGroupDescription();

    /**
     * TIT2
     *
     * The 'Title/Songname/Content description' frame is the actual official of the piece
     * (e.g. "Adagio", "Hurricane Donna").
     *
     * @return
     */
    Optional<String> getTitle();

    /**
     * TIT3
     *
     * The 'Subtitle/Description refinement' frame is used for information directly related to
     * the contents title (e.g. "Op. 16" or "Performed live at Wembley").
     *
     * @return
     */
    Optional<String> getSubtitle();

    /**
     * TKEY
     *
     * The 'Initial key' frame contains the musical key in which the sound starts.
     * It is represented as a string with a maximum length of three characters. The ground keys
     * are represented with "A","B","C","D","E", "F" and "G" and halfkeys represented with "b" and "#".
     * Minor is represented as "m". Example "Cbm". Off key is represented with an "o" only.
     *
     * @return
     */
    Optional<Key> getInitialKey();

    /**
     * TLAN
     *
     * The 'Language(s)' frame should contain the languages of the text or lyrics spoken or sung in
     * the audio. The language is represented with three characters according to ISO-639-2. If more than
     * one language is used in the text their language codes should follow according to their usage.
     *
     * @return
     */
    Collection<LanguageCode> getLanguages();

    /**
     * TLEN
     *
     * The 'Length' frame contains the length of the audiofile in milliseconds, represented as a numeric string.
     *
     * @return
     */
    Optional<Duration> getLength();

    /**
     * TMED
     *
     * The 'Media release' frame describes fromRelease which media the sound originated. This may be a text string
     * or a reference to the predefined media types found in the list below. References are made within
     * "(" and ")" and are optionally followed by a text refinement, e.g. "(MC) with four channels". If a
     * text refinement should begin with a "(" character it should be replaced with "((" in the same
     * way as in the "TCO" frame. Predefined refinements is appended after the media release,
     * e.g. "(CD/A)" or "(VID/PAL/VHS)".
     *
     * @return
     */
    Optional<MediaType> getMediaType();

    /**
     * TOAL
     *
     * The 'Original album/movie/show title' frame is intended for the title of the original recording
     * (or source of sound), if for example the music in the file should be a cover of a previously
     * released song.
     *
     * @return
     */
    Optional<String> getOriginalTitle();

    /**
     * TOFN
     *
     * The 'Original filename' frame contains the preferred filename for the file, since some media
     * doesn't allow the desired length of the filename. The filename is case sensitive and includes its suffix.
     *
     * @return
     */
    Optional<String> getOriginalFileName();

    /**
     * TOLY
     *
     * The 'Original lyricist(s)/text writer(s)' frame is intended for the text writer(s) of the original recording,
     * if for example the music in the file should be a cover of a previously released song.
     * The text writers are seperated with the "/" character.
     *
     * @return
     */
    Collection<Artist> getOriginalTextWriters();

    /**
     * TOPE
     *
     * The 'Original artist(s)/performer(s)' frame is intended for the performer(s) of the original
     * recording, if for example the music in the file should be a cover of a previously released song.
     * The performers are seperated with the "/" character.
     *
     * @return
     */
    Collection<Artist> getOriginalArtists();

    /**
     * TORY
     *
     * The 'Original release year' frame is intended for the year when the original recording,
     * if for example the music in the file should be a cover of a previously released song, was released.
     * The field is formatted as in the "TYER" frame.
     *
     * @return
     */
    Optional<LocalDate> getOriginalReleaseYear();

    /**
     * TOWN
     *
     * The 'File owner/licensee' frame contains the official of the owner or licensee of the file and it's contents.
     *
     * @return
     */
    Optional<String> getFileOwner();

    /**
     * TPE1
     *
     * The 'Lead artist(s)/Lead performer(s)/Soloist(s)/Performing group' is used for the mainArtists artist(s).
     * They are seperated with the "/" character.
     *
     * @return
     */
    Collection<Artist> getMainArtists();

    /**
     * TPE2
     *
     * The 'Band/Orchestra/Accompaniment' frame is used for additional information about the performers
     * in the recording.
     *
     * @return
     */
    Optional<String> getAccompaniment();

    /**
     * TPE3
     *
     * The 'Conductor' frame is used for the official of the conductor.
     *
     * @return
     */
    Collection<Artist> getConductor();

    /**
     * TPE4
     *
     * The 'Interpreted, remixed, or otherwise modified by' frame contains more information about
     * the people behind a remix and similar interpretations of another existing piece.
     *
     * @return
     */
    Collection<Artist> getVersionArtists();

    /**
     * TPOS
     *
     * The 'Part of a set' frame is a numeric string that describes which part of a set the audio came
     * fromRelease. This frame is used if the source described in the "TALB" frame is divided into several mediums,
     * e.g. a double CD. The value may be extended with a "/" character and a numeric string containing the
     * total primitive of parts in the set. E.g. "1/2".
     *
     * @return
     */
    Optional<String> getDisc();

    /**
     * TPUB
     *
     * The 'Publisher' frame simply contains the official of the label or publisher.
     *
     * @return
     */
    Optional<String> getPublisher();

    /**
     * TRCK
     *
     * The 'Track primitive/Position in set' frame is a numeric string containing the order primitive of
     * the audio-file on its original recording. This may be extended with a "/" character and a numeric
     * string containing the total numer of tracks/elements on the original recording. E.g. "4/9".
     *
     * @return
     */
    OptionalInt getTrackNumber();

    /**
     * TRDA
     *
     * The 'Recording dates' frame is a intended to be used as complement to the "TYER", "TDAT"
     * and "TIME" frames. E.g. "4th-7th June, 12th June" in combination with the "TYER" frame.
     *
     * @return
     */
    Collection<LocalDate> getRecordingDates();

    /**
     * TRSN
     *
     * The 'Internet radio station official' frame contains the official of the internet radio station
     * fromRelease which the audio is streamed.
     *
     * @return
     */
    Optional<String> getInternetRadioStationName();

    /**
     * TRSO
     *
     * The 'Internet radio station owner' frame contains the official of the owner of the internet
     * radio station fromRelease which the audio is streamed.
     *
     * @return
     */
    Optional<String> getInternetRadioStationOwner();

    /**
     * TSIZ
     *
     * The 'Size' frame contains the size of the audiofile in bytes, excluding
     * the ID3v2 tag, represented as a numeric string.
     *
     * @return
     */
    OptionalInt getSize();

    /**
     * TSRC
     *
     * The 'ISRC' frame should contain the International Standard Recording Code (ISRC) (12 characters).
     *
     * @return
     */
    Optional<String> getISRC();

    /**
     * TDAT
     *
     * The 'Date' frame is a numeric string in the DDMM format containing the date for the recording.
     * This field is always four characters long.
     *
     * @return
     */
    Optional<LocalDate> getDate();

}
