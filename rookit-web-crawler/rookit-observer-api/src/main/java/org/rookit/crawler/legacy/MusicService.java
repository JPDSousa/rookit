package org.rookit.crawler.legacy;

import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;

import io.reactivex.Observable;

@SuppressWarnings("javadoc")
public interface MusicService {

    String ID = "id";
    String LISTENERS = "listeners";
    String POPULARITY = "popularity";
    String MARKETS = "markets";
    String URL = "url";
    String URI = "uri";
    String PREVIEW = "preview";
    String LOCATION = "location";
    String TAGS = "tags";
    String WIKI = "wiki";
    String PLAYS = "plays";

    String getName();
    AvailableServices getKey();

    Observable<Track> searchTrack(Track track);
    Observable<Track> getArtistTracks(Artist artist);

    Observable<Artist> searchArtist(Artist artist);
    Observable<Artist> searchRelatedArtists(Artist artist);

    Observable<Album> searchAlbum(Album album);

    Observable<Genre> searchGenre(Genre genre);
    Observable<Genre> searchRelatedGenres(Genre genre);

    Observable<Playlist> searchPlaylist(Playlist playlist);

    Observable<Track> topTracks();
    Observable<Artist> topArtists();
    Observable<Album> topAlbums();

    Observable<Track> getAlbumTracks(Album album);

}
