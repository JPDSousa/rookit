package org.rookit.crawler.legacy;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.Request;
import com.wrapper.spotify.methods.albums.AlbumsRequest;
import com.wrapper.spotify.methods.audiofeatures.AudioFeaturesRequest;
import com.wrapper.spotify.methods.tracks.TracksRequest;
import com.wrapper.spotify.models.album.SimpleAlbum;
import com.wrapper.spotify.models.artist.SimpleArtist;
import com.wrapper.spotify.models.audio.AudioFeature;
import com.wrapper.spotify.models.authentication.ClientCredentials;
import com.wrapper.spotify.models.playlist.PlaylistTrack;
import com.wrapper.spotify.models.track.SimpleTrack;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.mapdb.DB;
import org.rookit.api.dm.ExternalMetadataModel;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.crawler.legacy.config.MusicServiceConfig;
import org.rookit.crawler.legacy.config.SpotifyConfig;
import org.rookit.crawler.legacy.factory.SpotifyFactory;
import org.rookit.crawler.legacy.utils.spotify.PageObservable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.rookit.crawler.AvailableServices.SPOTIFY;

@SuppressWarnings("javadoc")
public class Spotify implements MusicService {

    private static final Logger LOGGER = Logger.getLogger(Spotify.class.getName());

    private final Api api;
    private final SpotifyFactory factory;
    private final Scheduler requestScheduler;

    public Spotify(final RookitFactories factories, final MusicServiceConfig config,
            final DB cache) {
        if(cache == null) {
            LOGGER.warning("No cache provided");
        }
        final SpotifyConfig sConfig = config.spotify();
        final RateLimiter rateLimiter = RateLimiter.create(sConfig.getRateLimit());
        final ClientCredentials credentials;
        this.requestScheduler = Schedulers.io();
        this.factory = new SpotifyFactory(factories, config);
        try {
            credentials = Api.builder()
                    .clientId(sConfig.getClientId())
                    .clientSecret(sConfig.getClientSecret())
                    .build()
                    .clientCredentialsGrant()
                    .build()
                    .exec();
            this.api = Api.builder()
                    .accessToken(credentials.getAccessToken())
                    .cache(cache)
                    .rateLimiter(rateLimiter)
                    .build();
            LOGGER.info("Spotify access token: " + credentials.getAccessToken());
            LOGGER.info("Spotify token expires in " + credentials.getExpiresIn() + " seconds");
        } catch (final IOException e) {
            throw new RuntimeException("Cannot connect...", e);
        }
        LOGGER.info("Spotify crawler created");
    }

    public Scheduler getRequestScheduler() {
        return this.requestScheduler;
    }

    @Override
    public String getName() {
        return getKey().name();
    }

    @Override
    public Observable<Track> searchTrack(final Track track) {
        final Artist artist = Iterables.getFirst(track.mainArtists(), null);
        final String query = new StringBuilder("track:")
                .append(track.title().toString())
                .append(" artist:")
                .append(artist != null ? artist.name() : "*")
                .toString();
        LOGGER.info("Searching for track '" + track.longFullTitle() + "' with auto: " + query);

        return Observable.create(PageObservable.create(this.api, this.api.searchTracks(query).build()))
                .buffer(AudioFeaturesRequest.MAX_IDS)
                .doAfterNext(tracks -> LOGGER.info("More " + tracks.size() + " results for track: " + track.getIdAsString()))
                .flatMap(this::getAudioFeatures);
    }

    private Observable<Track> getAudioFeatures(final List<com.wrapper.spotify.models.track.Track> tracks) {
        try {
            final Map<String, com.wrapper.spotify.models.track.Track> groupedTracks = tracks.stream()
                    .collect(Collectors.toMap(com.wrapper.spotify.models.track.Track::getId, track -> track));
            final List<String> ids = Lists.newArrayList(groupedTracks.keySet());
            final Map<String, AudioFeature> audioFeatures = this.api.getAudioFeatures(ids).build().exec().stream()
                    .collect(Collectors.toMap(AudioFeature::getId, audioFeature -> audioFeature));
            return Observable.fromIterable(groupedTracks.keySet())
                    .map(id -> this.factory.toTrack(groupedTracks.get(id), audioFeatures.get(id)));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Observable<Track> getArtistTracks(final Artist artist) {
        final String id = getId(artist);
        if(id == null) {
            throw new RuntimeException("Cannot find id for artist: " + artist.name());
        }
        LOGGER.info("Fetching for artist tracks: " + id);

        return Observable.create(PageObservable.create(this.api, this.api.getAlbumsForArtist(id).build()))
                .observeOn(getRequestScheduler())
                .map(SimpleAlbum::getId)
                .distinctUntilChanged()
                .buffer(AlbumsRequest.MAX_IDS)
                .map(ids -> this.api.getAlbums(ids).build())
                .flatMap(this::asyncRequest)
                .flatMap(Observable::fromIterable)
                .map(com.wrapper.spotify.models.album.Album::getTracks)
                .flatMap(page -> Observable.create(PageObservable.create(this.api, page))
                        .observeOn(getRequestScheduler()))
                .filter(t -> containsArtist(t, id))
                .map(SimpleTrack::getId)
                .buffer(TracksRequest.MAX_IDS)
                .map(ids -> this.api.getTracks(ids).build())
                .flatMap(this::asyncRequest)
                .flatMap(Observable::fromIterable)
                .map(this.factory::toTrack);
    }

    private boolean containsArtist(final SimpleTrack t, final String id) {
        return t.getArtists().stream()
                .map(SimpleArtist::getId)
                .anyMatch(i -> Objects.equal(i, id));
    }

    private String getId(final ExternalMetadataModel element) {
        final Map<String, Object> doc = element.getExternalMetadata(getName());
        return doc != null ? (String) doc.get(ID) : null;
    }

    @Override
    public Observable<Artist> searchArtist(final Artist artist) {
        final String query = artist.name();
        LOGGER.info("Searching artist with auto: " + query);
        return Observable.create(PageObservable.create(this.api, this.api.searchArtists(query).build()))
                .observeOn(getRequestScheduler())
                .map(this.factory::toArtist)
                .flatMap(Observable::fromIterable);
    }

    @Override
    public Observable<Artist> searchRelatedArtists(final Artist artist) {
        final String id = getId(artist);
        if(id == null) {
            throw new RuntimeException("Cannot find id for artist: " + artist.name());
        }
        LOGGER.info("Searching for related artists of artist: " + id);
        return Observable.just(this.api.getArtistRelatedArtists(id).build())
                .flatMap(this::asyncRequest)
                .flatMap(Observable::fromIterable)
                .map(this.factory::toArtist)
                .flatMap(Observable::fromIterable);
    }

    @Override
    public Observable<Album> searchAlbum(final Album album) {
        final Artist artist = Iterables.getFirst(album.artists(), null);
        final StringBuilder query = new StringBuilder("album:")
                .append(album.title())
                .append(" artist:")
                .append(artist != null ? artist.name() : "*");
        LOGGER.info("Searching for albums with auto: " + query);
        return Observable.create(PageObservable.create(this.api, this.api.searchAlbums(query.toString()).build()))
                .observeOn(getRequestScheduler())
                .map(SimpleAlbum::getId)
                .buffer(AlbumsRequest.MAX_IDS)
                .map(ids -> this.api.getAlbums(ids).build())
                .flatMap(this::asyncRequest)
                .flatMap(Observable::fromIterable)
                .map(this.factory::toAlbum);
    }

    @Override
    public Observable<Track> getAlbumTracks(final Album album) {
        final String id = getId(album);
        if(id == null) {
            throw new RuntimeException("Cannot find id for album: " + album.title());
        }
        LOGGER.info("Searching for related artists of artist: " + id);
        return Observable.just(this.api.getAlbum(id).build())
                .flatMap(this::asyncRequest)
                .flatMap(a -> Observable.create(PageObservable.create(this.api, a.getTracks()))
                        .observeOn(getRequestScheduler()))
                .map(SimpleTrack::getId)
                .buffer(TracksRequest.MAX_IDS)
                .map(ids -> this.api.getTracks(ids).build())
                .flatMap(this::asyncRequest)
                .flatMap(Observable::fromIterable)
                .map(this.factory::toTrack);
    }

    private <T> ObservableSource<T> asyncRequest(final Request<T> request) {
        return Observable.fromCallable(() -> request.exec())
                .observeOn(getRequestScheduler());
    }

    @Override
    public Observable<Genre> searchGenre(final Genre genre) {
        LOGGER.info("Searching genres is disabled in spotify crawler");
        return Observable.empty();
    }

    @Override
    public Observable<Genre> searchRelatedGenres(final Genre genre) {
        LOGGER.info("Searching related genres is disabled in spotify crawler");
        return Observable.empty();
    }

    @Override
    public Observable<Track> topTracks() {
        LOGGER.info("Fetching top tracks");
        return Observable.just(this.api.getPlaylist("spotify", "37i9dQZF1DXcBWIGoYBM5M").build())
                .flatMap(this::asyncRequest)
                .map(com.wrapper.spotify.models.playlist.Playlist::getTracks)
                .flatMap(page -> Observable.create(PageObservable.create(this.api, page))
                        .observeOn(getRequestScheduler()))
                .map(PlaylistTrack::getTrack)
                .map(this.factory::toTrack);
    }

    @Override
    public Observable<Artist> topArtists() {
        LOGGER.info("Fetching top artists");
        return Observable.just(this.api.getPlaylist("spotifycharts", "37i9dQZEVXbMDoHDwVN2tF").build())
                .flatMap(this::asyncRequest)
                .map(com.wrapper.spotify.models.playlist.Playlist::getTracks)
                .flatMap(page -> Observable.create(PageObservable.create(this.api, page))
                        .observeOn(getRequestScheduler()))
                .map(PlaylistTrack::getTrack)
                .map(this.factory::toTrack)
                .map(Track::mainArtists)
                .flatMap(Observable::fromIterable);
    }

    @Override
    public Observable<Album> topAlbums() {
        LOGGER.info("Fetching top albums");
        return Observable.just(this.api.getPlaylist("spotify", "37i9dQZF1DX0rV7skaITBo").build())
                .flatMap(this::asyncRequest)
                .map(com.wrapper.spotify.models.playlist.Playlist::getTracks)
                .flatMap(page -> Observable.create(PageObservable.create(this.api, page))
                        .observeOn(getRequestScheduler()))
                .map(PlaylistTrack::getTrack)
                .map(com.wrapper.spotify.models.track.Track::getAlbum)
                .map(SimpleAlbum::getId)
                .buffer(AlbumsRequest.MAX_IDS)
                .map(ids -> this.api.getAlbums(ids).build())
                .flatMap(this::asyncRequest)
                .flatMap(Observable::fromIterable)
                .map(this.factory::toAlbum);
    }

    @Override
    public AvailableServices getKey() {
        return SPOTIFY;
    }

    @Override
    public Observable<Playlist> searchPlaylist(final Playlist playlist) {
        throw new UnsupportedOperationException("Not implemented yet :/");
    }

}
