package org.rookit.crawler.legacy.factory;

import java.util.Set;

import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;

@SuppressWarnings("javadoc")
public interface ModelFactory<Ar, Al, Tr> {
	
	Track toTrack(Tr source);
	
	Album toAlbum(Al source);
	
	Artist toArtist(Ar source, String originalName);
	
	Set<Artist> toArtist(Ar source);
	
}
