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

package org.rookit.mongodb;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeVersion;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.dm.test.DataModelTestFactory;
import org.rookit.dm.test.mixin.TrackMixin;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@SuppressWarnings("javadoc")
// @RunWith(ConcurrentTestRunner.class)
public class RookitMongoDBTest implements TrackMixin {

    private static final Injector INJECTOR = null;

    private final DataModelTestFactory factory = INJECTOR.getInstance(DataModelTestFactory.class);

    @Override
    public TrackFactory getTrackFactory() {
        return INJECTOR.getInstance(TrackFactory.class);
    }

    @Override
    public VersionTrackFactory getVersionTrackFactory() {
        return INJECTOR.getInstance(VersionTrackFactory.class);
    }

    @Test
    public final void testAddAlbum() throws IOException {
        // preparing data
//        final Album expected = this.factory.albums().createRandom();
//        final Path randomCoverPath = TestResources.getRandomCoverPath();
//        final byte[] expectedContent = Files.readAllBytes(randomCoverPath);
//        TestPreconditions.assure().isNotEmpty(expectedContent, randomCoverPath.toString());

//        expected.setCover(expectedContent);
//        expected.cover().readFrom().read(new byte[expectedContent.length]);
        //this.database.addAlbum(expected); TODO change me

        // assertions
        // final Album actual = getModelById(this.database.getAlbums(), expected); TODO change me

        // assertThat(actual).isEqualTo(expected); TODO change me

        // stream
//        assertThat(actual.cover())
//                .isInstanceOf(GridFsBiStream.class);
//        final GridFsBiStream stream = (GridFsBiStream) actual.cover();
//
//        // gridfs file
//        final GridFSFile file = stream.getMetadata();
//        assertThat(file.getLength()).isEqualTo(expectedContent.length);
//
//        // writeTo
//        final ByteArrayOutputStream arrayOut = new ByteArrayOutputStream(expectedContent.length);
//        // stream.writeTo(arrayOut);
//        assertThat(arrayOut.toByteArray()).isEqualTo(expectedContent);
//
//        // readFrom
//        final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(expectedContent.length);
//        IOUtils.copy(actual.cover().readFrom(), actualContent);
//        assertThat(actualContent.toByteArray()).isEqualTo(expectedContent);
    }

    @Test
    public final void testAddArtist() throws IOException {
//        final Artist expected = this.factory.artists().createRandom();
//        expected.setGenres(this.factory.genres().createRandomSet());
//        final Path randomCoverPath = TestResources.getRandomCoverPath();
//        final byte[] expectedContent = Files.readAllBytes(randomCoverPath);
//        TestPreconditions.assure().isNotEmpty(expectedContent, randomCoverPath.toString());
//
//        expected.setPicture(expectedContent);

//        this.database.addArtist(expected);
//        final Artist actual = getModelById(this.database.getArtists(), expected);
//        assertThat(actual).isEqualTo(expected);
//        assertThat(actual.genres())
//                .containsExactlyInAnyOrderElementsOf(expected.genres());
    }

    @Test
    public final void testAddDuplicateTrack() throws IOException {
//        final List<Path> paths = TestResources.getTrackPaths();
//        final Set<Artist> mainArtists = this.factory.artists().createRandomSet();
//        final String title = "title";
//        final Track expected = createOriginalTrack(title, mainArtists);
//        final Track expectedDup = createOriginalTrack(title, mainArtists);
//
//        TestPreconditions.assure().is(Objects.equal(expectedDup, expected),
//                "Both tracks must be equal in order for the test to make sense");
//        TestPreconditions.assure().isGreaterThanOrEqualTo(paths, 2, "track paths");
//
//        final byte[] content1 = Files.readAllBytes(paths.get(0));
//        final byte[] content2 = Files.readAllBytes(paths.get(1));
//
//        TestPreconditions.assure().isNotEmpty(content1, "content of " + paths.get(0));
//        TestPreconditions.assure().isNotEmpty(content2, "content of " + paths.get(1));
//        expected.setAudioContent(content1);
//        expectedDup.setAudioContent(content2);
//        this.database.addTrack(expected);
//        this.database.addTrack(expectedDup);
//        assertThat(this.database.getTracks().count()).isEqualTo(1);
    }

    @Test
    public final void testAddGenre() {
        final Genre expected = this.factory.genres().createRandom();
//        this.database.addGenre(expected);
//        final Genre actual = getModelById(this.database.genres(), expected);
//        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public final void testAddPlaylist() throws IOException {
//        final Playlist expected = this.factory.playlists().createRandom();
//        final Path randomCoverPath = TestResources.getRandomCoverPath();
//        final byte[] bytes = Files.readAllBytes(randomCoverPath);
//        TestPreconditions.assure().isNotEmpty(bytes, randomCoverPath.toString());
//        expected.setImage(bytes);

//        this.database.addPlaylist(expected);
//        final Playlist actual = expected.id()
//                .map(this.database.getPlaylists()::withId)
//                .flatMap(PlaylistQuery::first)
//                .orElseThrow(TestPreconditions.fail("No id found."));
//        assertThat(expected.image())
//                .isEqualTo(actual.image());
//
//        assertThat(actual).isEqualTo(expected);
//
//        final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(bytes.length);
//        IOUtils.copy(actual.image().readFrom(), actualContent);
//        assertThat(actualContent.toByteArray())
//                .isEqualTo(bytes);
    }

    @Test
    public final void testAddRemix() {
        final Track original = this.factory.originalTracks().createRandom();

        final Set<Artist> remixA1 = this.factory.artists().createRandomSet();
        final VersionTrack remix1 = createVersionTrack(original, TypeVersion.REMIX, remixA1);

        final Set<Artist> remixA2 = this.factory.artists().createRandomUniqueSet(remixA1);
        final VersionTrack remix2 = createVersionTrack(original, TypeVersion.REMIX, remixA2);

        final VersionTrack acoustic1 = createVersionTrack(original, TypeVersion.ACOUSTIC,
                ImmutableSet.of());

        final Collection<Track> tracks = ImmutableSet.of(original, remix1, remix2, acoustic1);
//        tracks.forEach(this.database::addTrack);
//
//        assertThat(this.database.getTracks().count()).isEqualTo(tracks.size());
    }

    @Test
    public final void testAddTrack() throws IOException {
//        final Track expected = this.factory.originalTracks().createRandom();
//        final Path randomTrackPath = TestResources.getRandomTrackPath(tracks);
//        final byte[] bytes = Files.readAllBytes(randomTrackPath);
//        TestPreconditions.assure().isNotEmpty(bytes, randomTrackPath.toString());
//        expected.setAudioContent(bytes);

//        this.database.addTrack(expected);
//        final Track actual = getModelById(this.database.getTracks(), expected);
//        assertThat(actual).isEqualTo(expected);
//        final ByteArrayOutputStream actualContent = new ByteArrayOutputStream(bytes.length);
//        IOUtils.copy(actual.audio().readFrom(), actualContent);
//        assertThat(actualContent.toByteArray())
//                .isEqualTo(bytes);
    }

}
