
package org.rookit.dm.artist;

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.CollectionOps;
import org.rookit.test.exception.ResourceCreationException;

import java.util.Collection;

@SuppressWarnings("javadoc")
public class GroupArtistTest extends AbstractArtistTest<GroupArtist> {

    @SuppressWarnings("synthetic-access")
    @Test
    public final void testMembers() {
        AbstractUnitTest.testCollectionOps(new CollectionOps<Musician>() {

            @Override
            public void add(final Musician item) {
                GroupArtistTest.this.testResource.addMember(item);
            }

            @Override
            public void addAll(final Collection<Musician> items) {
                GroupArtistTest.this.testResource.addMembers(items);
            }

            @Override
            public Collection<Musician> get() {
                return GroupArtistTest.this.testResource.members();
            }

            @Override
            public void remove(final Musician item) {
                GroupArtistTest.this.testResource.removeMember(item);
            }

            @Override
            public void removeAll(final Collection<Musician> items) {
                GroupArtistTest.this.testResource.removeMembers(items);
            }

            @Override
            public void reset() {
                GroupArtistTest.this.testResource.clearMembers();
            }

            @Override
            public void set(final Collection<Musician> items) {
                GroupArtistTest.this.testResource.setMembers(items);
            }
        }, FACTORY.musicians());
    }

    @Override
    public GroupArtist doCreateTestResource() throws ResourceCreationException {
        return FACTORY.groupArtists().createRandom();
    }

}
