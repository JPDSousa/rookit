package org.rookit.crawler.legacy;

import static org.rookit.crawler.AvailableServices.values;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.rookit.api.dm.ExternalMetadataModelSetter;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.dm.track.Track;
import org.rookit.crawler.legacy.result.CrawlerResult;
import org.rookit.dm.similarity.SimilarityProvider;
import org.rookit.dm.similarity.calculator.SimilarityMeasure;
import org.rookit.dm.similarity.calculator.SimilarityPlaceholder;

import com.google.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

@SuppressWarnings("javadoc")
public class RookitCrawler implements Closeable {

	private static final Logger LOGGER = Logger.getLogger(RookitCrawler.class.getName());

	private final ServiceProvider provider;
	private final SimilarityProvider measures;

	@Inject
	private RookitCrawler(ServiceProvider provider, SimilarityProvider measures) {
		this.provider = provider;
		this.measures = measures;
	}
	
	private <T extends ExternalMetadataModelSetter> Observable<CrawlerResult<T>> search(T source, boolean ignoreAlreadyFound,
			Function<MusicService, Observable<T>> searchFunction) {
		return Observable.fromArray(values())
				.map(provider::getService)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(service -> ignoreAlreadyFound 
						|| source.getExternalMetadata(service.getName()) != null)
				.flatMap(service -> searchFunction.apply(service)
						.map(result -> CrawlerResult.create(service.getKey(), result)));
	}
	
	public Observable<CrawlerResult<Track>> search(Track source) {
		return search(source, true);
	}
	
	public Observable<CrawlerResult<Track>> search(Track source, boolean ignoreAlreadyFound) {
		return search(source, ignoreAlreadyFound, service -> search(service, source));
	}
	
	public Observable<CrawlerResult<Artist>> search(Artist source) {
		return search(source, true);
	}
	
	public Observable<CrawlerResult<Artist>> search(Artist source, boolean ignoreAlreadyFound) {
		return search(source, ignoreAlreadyFound, service -> search(service, source));
	}
	
	public Observable<CrawlerResult<Album>> search(Album source) {
		return search(source, true);
	}
	
	public Observable<CrawlerResult<Album>> search(Album source, boolean ignoreAlreadyFound) {
		return search(source, ignoreAlreadyFound, service -> search(service, source));
	}
	
	public Observable<CrawlerResult<Genre>> search(Genre source) {
		return search(source, true);
	}
	
	public Observable<CrawlerResult<Genre>> search(Genre source, boolean ignoreAlreadyFound) {
		return search(source, ignoreAlreadyFound, service -> search(service, source));
	}
	
	public Observable<CrawlerResult<Playlist>> search(Playlist source) {
		return search(source, true);
	}
	
	public Observable<CrawlerResult<Playlist>> search(Playlist source, boolean ignoreAlreadyFound) {
		return search(source, ignoreAlreadyFound, service -> search(service, source));
	}
	
	private MusicService checkServiceExists(AvailableServices service) {
		final Optional<MusicService> serviceOpt = provider.getService(service);
		if(!serviceOpt.isPresent()) {
			throw new RuntimeException(service + " is not available");
		}
		return serviceOpt.get();
	}


	public Maybe<Track> singleSearch(AvailableServices service, Track track) {
		final SimilarityMeasure<Track> measure = measures.getMeasure(Track.class, track);
		final MusicService serviceManager = checkServiceExists(service);
		
		return bestScore(measure, search(serviceManager, track));
	}


	public Maybe<Album> singleSearch(AvailableServices service, Album album) {
		final SimilarityMeasure<Album> measure = measures.getMeasure(Album.class, album);
		final MusicService serviceManager = checkServiceExists(service);
		
		return bestScore(measure, search(serviceManager, album));
	}
	
	public Maybe<Artist> singleSearch(AvailableServices service, Artist artist) {
		final SimilarityMeasure<Artist> measure = measures.getMeasure(Artist.class, artist);
		final MusicService serviceManager = checkServiceExists(service);
		
		return bestScore(measure, search(serviceManager, artist));
	}
	
	public Maybe<Genre> singleSearch(AvailableServices service, Genre genre) {
		final SimilarityMeasure<Genre> measure = measures.getMeasure(Genre.class, genre);
		final MusicService serviceManager = checkServiceExists(service);
		
		return bestScore(measure, search(serviceManager, genre));
	}
	
	public Maybe<Playlist> singleSearch(AvailableServices service, Playlist playlist) {
		final SimilarityMeasure<Playlist> measure = measures.getMeasure(Playlist.class, playlist);
		final MusicService serviceManager = checkServiceExists(service);
		
		return bestScore(measure, search(serviceManager, playlist));
	}
	
	private <T> Maybe<T> bestScore(SimilarityMeasure<T> measure, Observable<T> source) {
		return source
				.map(measure::measure)
				.filter(ph -> ph.getDistance() < 1)
				.reduce(this::bestScore)
				.map(SimilarityPlaceholder::getTarget);
	}
	
	private Observable<Track> search(MusicService service, Track track) {
		LOGGER.info(new StringBuilder("[")
				.append(service.getName())
				.append("] Searching for: ")
				.append(track.longFullTitle())
				.toString());
		return service.searchTrack(track);
	}
	
	private Observable<Album> search(MusicService service, Album album) {
		LOGGER.info(new StringBuilder("[")
				.append(service.getName())
				.append("] Searching for: ")
				.append(album.fullTitle())
				.toString());
		return service.searchAlbum(album);
	}
	
	private Observable<Artist> search(MusicService service, Artist artist) {
		LOGGER.info(new StringBuilder("[")
				.append(service.getName())
				.append("] Searching for: ")
				.append(artist.name())
				.toString());
		return service.searchArtist(artist);
	}
	
	private Observable<Genre> search(MusicService service, Genre genre) {
		LOGGER.info(new StringBuilder("[")
				.append(service.getName())
				.append("] Searching for: ")
				.append(genre.name())
				.toString());
		return service.searchGenre(genre);
	}
	
	private Observable<Playlist> search(MusicService service, Playlist playlist) {
		LOGGER.info(new StringBuilder("[")
				.append(service.getName())
				.append("] Searching for: ")
				.append(playlist.name())
				.toString());
		return service.searchPlaylist(playlist);
	}
	
	public Observable<Track> search(AvailableServices service, Track track) {
		final MusicService serviceManager = checkServiceExists(service);
		return search(serviceManager, track);
	}
	
	public Observable<Album> search(AvailableServices service, Album album) {
		final MusicService serviceManager = checkServiceExists(service);
		return search(serviceManager, album);
	}
	
	public Observable<Artist> search(AvailableServices service, Artist artist) {
		final MusicService serviceManager = checkServiceExists(service);
		return search(serviceManager, artist);
	}
	
	public Observable<Genre> search(AvailableServices service, Genre genre) {
		final MusicService serviceManager = checkServiceExists(service);
		return search(serviceManager, genre);
	}
	
	public Observable<Playlist> search(AvailableServices service, Playlist playlist) {
		final MusicService serviceManager = checkServiceExists(service);
		return search(serviceManager, playlist);
	}
	
	private <T> SimilarityPlaceholder<T> bestScore(SimilarityPlaceholder<T> ph1, SimilarityPlaceholder<T> ph2) {
		return ph1.getDistance() < ph2.getDistance() ? ph1 : ph2;
	}

	@Override
	public void close() throws IOException {
		provider.close();
	}

}
