/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.parser.result;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.dm.guice.RookitDataModelModule;
import org.rookit.dm.test.DataModelTestFactory;
import org.rookit.parser.result.dm.album.AlbumBuilder;
import org.rookit.parser.utils.TestUtils;
import org.rookit.parser.utils.TrackPath;

import com.google.common.collect.Iterables;
import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class SingleTrackAlbumBuilderTest {

	private static DataModelTestFactory factory;
	private AlbumBuilder guineaPig;

	@BeforeClass
	public static void setUpBeforeClass() {
		final Injector injector = Guice.createInjector(new RookitDataModelModule());
		factory = injector.getInstance(DataModelTestFactory.class);
	}

	@Before
	public void setUp() {
		guineaPig = getBasicSingleTrackAlbumBuilder()
				.withType(factory.getRandomTrackType())
				.withTitle(factory.randomString())
				.withTypeVersion(factory.getRandomVersionType())
				.withVersionArtists(factory.getRandomSetOfArtists())
				.withMainArtists(factory.getRandomSetOfArtists())
				.withFeatures(factory.getRandomSetOfArtists())
				.withProducers(factory.getRandomSetOfArtists())
				.withPath(TestUtils.getRandomTrackPath())
				.withDisc(factory.randomString())
				.withNumber(1)
				.withCover(factory.randomString().getBytes())
				.withAlbum(factory.getRandomAlbum())
				.withDate(LocalDate.now())
				.withAlbumTitle(factory.randomString())
				.withGenres(factory.genres.getRandomSetOfGenres(factory))
				.withHiddenTrack(factory.randomString());

	}


	@Test 
	public final void testCreateClone() { 
		assertThat(guineaPig).isEqualTo(AlbumBuilder.set(guineaPig)); 
	} 

	@Test(expected = IllegalArgumentException.class) 
	public final void testCreateCloneFromNull() { 
		AlbumBuilder.set((AlbumBuilder) null); 
	} 

	@Test
	public final void testHashCode() {
		guineaPig.hashCode();
		// TODO test with all variants (check code)
	}

	@Test
	public final void testType() {
		final TypeTrack type = factory.getRandomTrackType();
		guineaPig.withType(type);
		assertThat(guineaPig.getType()).isEqualTo(type);
	}

	@Test
	public final void testVersionType() {
		final AlbumBuilder builder = getBasicSingleTrackAlbumBuilder();
		final TypeVersion version = factory.getRandomVersionType();
		builder.withTypeVersion(version);
		assertThat(builder.getTypeVersion()).isEqualTo(version);
	}

	@Test
	public final void testMultipleVersions() {
		final TypeVersion versionType = factory.getRandomVersionType();
		guineaPig.withTypeVersion(versionType);
		assertThat(guineaPig.getTypeVersion()).isEqualTo(versionType);
	}

	@Test
	public final void testEmptyBuilderValidDisc() {
		final Album album = factory.getRandomAlbum();
		final Track track = factory.getFactories().createRandomOriginalTrack(this);
		final String disc = guineaPig.getDisc();
		assertThat(guineaPig.getDisc()).isNotNull();
		album.addTrack(track, 1, disc);
	}

	@Test
	public final void testId() {
		final ObjectId id = new ObjectId();
		guineaPig.withId(id);
		assertThat(guineaPig.id()).isEqualTo(id);
	}

	@Test
	public final void testBuild() throws IOException {
		final TypeTrack trackType = factory.getRandomTrackType();
		final String hiddenTrack = factory.randomString();
		final String albumTitle = factory.randomString();
		final String disc = factory.randomString();
		final String title = factory.randomString();
		final TypeVersion versionType = factory.getRandomVersionType();
		final Set<Artist> producers = factory.getRandomSetOfArtists();
		final Set<Artist> features = factory.getRandomSetOfArtists();
		final Set<Artist> mainArtists = factory.getRandomSetOfArtists();
		final Set<Artist> extras = factory.getRandomSetOfArtists();
		final byte[] cover = title.getBytes();
		final LocalDate date = LocalDate.now();
		final Set<Genre> genres = factory.genres.getRandomSetOfGenres(factory);
		final int number = 1;
		final TrackPath path = TestUtils.getRandomTrackPath();
		final AlbumBuilder builder = getBasicSingleTrackAlbumBuilder()
				.withType(trackType)
				.withTitle(title)
				.withTypeVersion(versionType)
				.withVersionArtists(extras)
				.withMainArtists(mainArtists)
				.withFeatures(features)
				.withProducers(producers)
				.withPath(path)
				.withDisc(disc)
				.withNumber(number)
				.withCover(cover)
				.withDate(date)
				.withAlbumTitle(albumTitle)
				.withGenres(genres)
				.withHiddenTrack(hiddenTrack);
		final Album album = builder.build();
		final Track track = Iterables.get(album.tracks(), 0).getTrack();
		final byte[] actualCover = new byte[title.length()];
		album.cover().readFrom().read(actualCover);
		assertThat(track.type()).isEqualTo(trackType);
		assertThat(track.title().getTitle()).isEqualTo(title);
		assertThat(track.mainArtists()).isEqualTo(mainArtists);
		assertThat(track.features()).isEqualTo(features);
		assertThat(track.producers()).isEqualTo(producers);
		assertThat(album.getTrack(disc, number)).isNotNull();
		assertThat(actualCover).isEqualTo(cover);
		assertThat(album.getReleaseDate()).isEqualTo(date);
		assertThat(album.title()).isEqualTo(albumTitle);
		assertThat(album.allGenres()).containsExactlyInAnyOrderElementsOf(genres);
		assertThat(track.getHiddenTrack()).isEqualTo(hiddenTrack);
		if(trackType == TypeTrack.VERSION) {
			assertThat(track.asVersionTrack().get().versionType())
			.isEqualTo(versionType);
			assertThat(track.asVersionTrack().get().versionArtists())
			.containsExactlyElementsOf(extras);
		}
	}

	private AlbumBuilder getBasicSingleTrackAlbumBuilder() {
		return AlbumBuilder.set(factory.getFactories());
	}

}
