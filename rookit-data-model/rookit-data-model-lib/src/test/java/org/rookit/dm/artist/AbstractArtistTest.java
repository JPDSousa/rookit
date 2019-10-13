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

package org.rookit.dm.artist;

import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.factory.GroupArtistFactory;
import org.rookit.dm.genre.AbstractGenreableTest;
import org.rookit.dm.test.mixin.ArtistMixin;
import org.rookit.test.exception.ResourceCreationException;
import org.rookit.test.preconditions.TestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
public abstract class AbstractArtistTest<T extends Artist> extends AbstractGenreableTest<T>
        implements ArtistMixin {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractArtistTest.class);

    private static final GroupArtistFactory ARTIST_FACTORY = INJECTOR.getInstance(GroupArtistFactory.class);

    @Override
    public GroupArtistFactory getGroupArtistFactory() {
        return ARTIST_FACTORY;
    }

    @Test
    public final void testAliases() {
        final String alias = "an alias";

        this.testResource.addAlias(alias);
        assertThat(this.testResource.profile().name().aliases())
                .contains(alias);

        final Set<String> aliases = Sets.newLinkedHashSetWithExpectedSize(1);
        aliases.add(alias);
        this.testResource.setAliases(aliases);
        assertThat(this.testResource.profile().name().aliases())
                .containsExactlyInAnyOrderElementsOf(aliases);
    }

    @Test
    public void testEqualsObject() {
        final String testName = "amithesameastheother";
        final Artist artist1 = createGroupArtist(testName);
        final Artist artist2 = createGroupArtist(testName);
        artist1.id().ifPresent(artist2::setId);
        assertThat(artist2).isEqualTo(artist1);
    }

    @Test
    public final void testGetAllGenres() {
        assertThat(this.testResource.allGenres())
                .containsExactlyInAnyOrderElementsOf(this.testResource.genres());
    }

    @Override
    @Test
    public final void testHashCode() throws ResourceCreationException {
        super.testHashCode();
        final String testName = "amithesameastheother";
        final Artist artist1 = createGroupArtist(testName);
        final Artist artist2 = createGroupArtist(testName);
        artist1.id().ifPresent(artist2::setId);
        assertThat(artist2.hashCode()).isEqualTo(artist1.hashCode());
    }

    @Test
    public final void testIPI() {
        final String ipi = "some random ipi";

        TestPreconditions.assure().optional()
                .doesNotContain(logger, this.testResource.profile().externalIdentifiers().ipi(), ipi, "IPI");

        this.testResource.setIPI(ipi);
        assertThat(this.testResource.profile().externalIdentifiers().ipi())
                //.isNotEmpty()
                //.get()
                .isEqualTo(ipi);
    }

    // TODO test beginDate and endDate -> begin date cannot happen before
    // endDate and so on

    @Test
    public void testOrigin() {
        final CountryCode testOrigin = CountryCode.RU;
        this.testResource.setOrigin(testOrigin);
        assertThat(this.testResource.profile().origin())
                .as("Origin is not being properly assigned")
                //.isNotEmpty()
                //.get()
                .isEqualTo(testOrigin);
    }

    @Test
    public final void testPicture() throws IOException {
        final byte[] picture = "randomBytes".getBytes();
        this.testResource.setPicture(picture);
        final byte[] actual = new byte[picture.length];
        this.testResource.profile().picture().readFrom().read(actual);
        assertThat(actual)
                .isNotEmpty()
                .containsExactly(picture);
    }

    @Test
    public void testRelatedArtist() {
        final Set<GroupArtist> related = FACTORY.groupArtists().createRandomSet();
        assertThat(this.testResource.relatedArtists())
                .as("Initial state")
                .isEmpty();

        for (final Artist art : related) {
            this.testResource.addRelatedArtist(art);
        }
        assertThat(this.testResource.relatedArtists())
                .as("Related artists are not being properly assigned!")
                .containsExactlyInAnyOrderElementsOf(related);

        for (final Artist art : related) {
            this.testResource.addRelatedArtist(art);
        }
        assertThat(this.testResource.relatedArtists()).as("Related artists are accepting duplicates!")
                .containsExactlyInAnyOrderElementsOf(related);
    }

}
