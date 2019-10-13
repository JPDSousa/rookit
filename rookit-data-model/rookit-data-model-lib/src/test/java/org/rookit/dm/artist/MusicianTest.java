
package org.rookit.dm.artist;

import org.rookit.api.dm.artist.Musician;
import org.rookit.test.exception.ResourceCreationException;

@SuppressWarnings("javadoc")
public class MusicianTest extends AbstractArtistTest<Musician> {

    @Override
    public Musician doCreateTestResource() throws ResourceCreationException {
        return FACTORY.musicians().createRandom();
    }

}
