package org.rookit.crawler;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.factory.RookitFactories;
import org.rookit.api.dm.track.Track;
import org.rookit.crawler.legacy.RookitCrawler;
import org.rookit.dm.guice.RookitDataModelModule;
import org.rookit.mongodb.guice.MongoDbModule;
import org.rookit.parser.result.dm.album.AlbumBuilder;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class RookitCrawlerTest {

	private static RookitCrawler guineaPig;
	private static RookitFactories factories;

	@BeforeClass
	public static void setUpBeforeClass() {
		final Injector injector = Guice.createInjector(
				new MongoDbModule(),
				new TestModule(), new RookitDataModelModule());
		guineaPig = injector.getInstance(RookitCrawler.class);
		factories = injector.getInstance(RookitFactories.class);
	}
	
	@AfterClass
	public static final void tearDownAfterClass() throws IOException {
		guineaPig.close();
	}

	@Test
	public final void testFillTrack() {
		final Artist u2 = factories.getArtistFactory().createArtist(TypeArtist.GROUP, "U2");
		final Set<Artist> artists = Sets.newHashSet(u2);
		final Track track = AlbumBuilder.set(factories)
				.withTitle("One")
				.withMainArtists(artists)
				.buildTrack();
		assertThat(guineaPig.search(track).count().blockingGet())
		.isPositive();
	}
}
