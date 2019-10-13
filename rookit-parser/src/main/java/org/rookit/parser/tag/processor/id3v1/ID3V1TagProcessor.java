package org.rookit.parser.tag.processor.id3v1;

import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.parser.tag.processor.TagProcessor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@SuppressWarnings("javadoc")
public interface ID3V1TagProcessor extends TagProcessor {

    Optional<String> getTitle();

    Collection<Artist> getArtists();

    Optional<String> getAlbumTitle();

    Optional<LocalDate> getYear();

    Optional<String> getComment();

    Collection<Genre> genres();

}
