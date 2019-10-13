package org.rookit.dm.album.tracks;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.rookit.api.dm.track.Track;
import org.rookit.dm.album.disc.MutableDiscFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.exception.ResourceCreationException;
import org.rookit.test.junit.categories.UnitTest;
import org.rookit.utils.optional.OptionalFactory;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@UnitTest
class MutableAlbumTracksImplUnitTest extends AbstractUnitTest<MutableAlbumTracksImpl> {

    private final MutableDiscFactory discFactory = mock(MutableDiscFactory.class);
    private final Failsafe failsafe = mock(Failsafe.class);
    private final OptionalFactory optionalFactory = mock(OptionalFactory.class);
    
    @Override
    public MutableAlbumTracksImpl doCreateTestResource() throws ResourceCreationException {
        return new MutableAlbumTracksImpl(this.failsafe, this.discFactory, this.optionalFactory);
    }

    @Test
    public final void testAddTrackLastWithInvalidTrack() {
        assertThatThrownBy(() -> this.testResource.addTrackLast(null, "A disc"))
                .as("Adding a null track")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @TestFactory
    public final Collection<DynamicTest> testAddTrackLastWithInvalidDisc() {
        final Track track = mock(Track.class);
        return testBlankStringArgument(discName -> this.testResource.addTrackLast(track, discName));
    }

    @Test
    public final void testEmptyTrackCount() {
        assertThat(this.testResource.size())
                .as("An empty album has always 0 track")
                .isEqualTo(0);
    }

}