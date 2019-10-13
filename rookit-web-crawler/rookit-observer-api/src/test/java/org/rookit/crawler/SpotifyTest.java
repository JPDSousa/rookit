package org.rookit.crawler;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.factory.AlbumFactory;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.factory.ArtistFactory;
import org.rookit.api.dm.track.Track;
import org.rookit.crawler.legacy.Spotify;
import org.rookit.crawler.legacy.config.MusicServiceConfig;
import org.rookit.dm.guice.RookitDataModelModule;
import org.rookit.dm.test.DataModelTestFactory;
import org.rookit.dm.utils.DataModelPrintUtils;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class SpotifyTest {

	private static DataModelTestFactory factory;
	private static DB cache;
	private static Spotify spotify;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		final Injector injector = Guice.createInjector(
				new TestModule(), new RookitDataModelModule());
		factory = injector.getInstance(DataModelTestFactory.class);
		cache = DBMaker.memoryDB().make();
		spotify = new Spotify(factory.getFactories(), 
				injector.getInstance(MusicServiceConfig.class), cache);
	}

	@AfterClass
	public static void tearDown() {
		cache.close();
	}

	@Test
	public final void testSearchTrack() {
		final ArtistFactory artistFactory = factory.getFactories().getArtistFactory();
		final Track track = factory.createRandomTrack("hey brother");
		final Set<Artist> artists = Sets.newLinkedHashSet();
		artists.add(artistFactory.createArtist(TypeArtist.GROUP, "Avicii"));
		track.setMainArtists(artists);
		final long count = spotify.searchTrack(track)
				.count()
				.blockingGet();
		assertThat(count)
		.isPositive();
	}
	
	@Test
	public final void testSearchArtistTracks() {
		final ArtistFactory artistFactory = factory.getFactories().getArtistFactory();
		final Artist artist = spotify.searchArtist(artistFactory.createArtist(TypeArtist.GROUP, "Madeon"))
				.firstOrError()
				.blockingGet();
		
		final long count = spotify.getArtistTracks(artist).count().blockingGet();
		assertThat(count)
		.isPositive();
	}
	
	@Test
	public final void testSearchArtist() {
		final ArtistFactory artistFactory = factory.getFactories().getArtistFactory();
		final Artist artist = artistFactory.createArtist(TypeArtist.GROUP, "Green Day");
		final long count = spotify.searchArtist(artist)
				.count()
				.blockingGet();
		
		assertThat(count)
		.isPositive();
	}
	
	@Test
	public final void testSearchAlbum() {
		final ArtistFactory artistFactory = factory.getFactories().getArtistFactory();
		final AlbumFactory albumFactory = factory.getFactories().getAlbumFactory();
		final Artist macklemore = artistFactory.createArtist(TypeArtist.GROUP, "Macklemore");
		final Artist ryan = artistFactory.createArtist(TypeArtist.GROUP, "Ryan Lewis");
		final Set<Artist> artists = Sets.newLinkedHashSet(Arrays.asList(macklemore, ryan));
		final Album album = albumFactory.createSingleArtistAlbum("The Heist", artists);
		final long count = spotify.searchAlbum(album)
				.map(DataModelPrintUtils::album)
				.doAfterNext(System.out::println)
				.count()
				.blockingGet();
		
		assertThat(count)
		.isPositive();
	}
	
	@Test
	public final void testTopTracks() {
		final long count = spotify.topTracks()
				.map(DataModelPrintUtils::track)
				.doAfterNext(System.out::println)
				.count()
				.blockingGet();
		
		assertThat(count)
		.isPositive();
	}
	
}
