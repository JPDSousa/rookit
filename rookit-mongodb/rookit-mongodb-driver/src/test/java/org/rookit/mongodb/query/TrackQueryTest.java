
package org.rookit.mongodb.query;

import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.track.factory.TrackFactory;
import org.rookit.api.dm.track.factory.VersionTrackFactory;
import org.rookit.dm.test.DataModelTestFactory;
import org.rookit.dm.test.mixin.TrackMixin;

@SuppressWarnings("javadoc")
// @RunWith(ConcurrentTestRunner.class)
public class TrackQueryTest implements TrackMixin {

    private static final Injector INJECTOR = null;

    private DataModelTestFactory factory;

    @BeforeEach
    public void createFactory() {
        this.factory = INJECTOR.getInstance(DataModelTestFactory.class);
    }

    @Test
    public final void testRemixes() {
//        final Set<Artist> main = this.factory.artists().createRandomSet();
//        final Track original = createOriginalTrack("I'm Original", main);
//
//        final Set<Artist> remixA1 = this.factory.artists().createRandomSet();
//        final VersionTrack remix1 = createVersionTrack(original, TypeVersion.REMIX, remixA1);
//
//        final Set<Artist> remixA2 = this.factory.artists().createRandomSet();
//        final VersionTrack remix2 = createVersionTrack(original, TypeVersion.REMIX, remixA2);
//
//        this.database.addTrack(original);
//        this.database.addTrack(remix1);
//        this.database.addTrack(remix2);
//
//        assertThat(this.database.getTracks().count()).isEqualTo(3);
//        final BsonObjectId originalId = original.id()
//                .map(ObjectId::new)
//                .map(BsonObjectId::new)
//                .orElseThrow(TestPreconditions.fail("cannot find an Id for track: " + original));
//        final BsonDocument expectedQuery = new BsonDocument("auto",
//                new BsonDocument(ORIGINAL, originalId));
//        final TrackQuery query = this.database
//                .getTracks()
//                .withOriginal(original);
//        assertThat(BsonDocument.parse(query.toString())).isEqualTo(expectedQuery);
//        final List<Track> results = query
//                .stream()
//                .collect(Collectors.toList());
//
//        assertThat(results)
//                .contains(remix1, remix2);
    }

    @Override
    public TrackFactory getTrackFactory() {
        return INJECTOR.getInstance(TrackFactory.class);
    }

    @Override
    public VersionTrackFactory getVersionTrackFactory() {
        return INJECTOR.getInstance(VersionTrackFactory.class);
    }
}
