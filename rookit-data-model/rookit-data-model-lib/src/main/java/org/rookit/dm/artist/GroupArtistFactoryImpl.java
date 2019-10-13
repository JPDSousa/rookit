/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
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

import com.google.inject.Inject;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.dm.artist.factory.GroupArtistFactory;
import org.rookit.api.dm.artist.key.ArtistKey;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.genre.able.GenreableFactory;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.dm.artist.profile.MutableProfileFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class GroupArtistFactoryImpl implements GroupArtistFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GroupArtistFactoryImpl.class);
    
    private final Failsafe failsafe;
    private final MutableProfileFactory profileFactory;
    private final OptionalUtils optionalUtils;
    private final GenreableFactory genreableFactory;

    @Inject
    private GroupArtistFactoryImpl(final Failsafe failsafe,
                                   final MutableProfileFactory profileFactory,
                                   final OptionalUtils optionalUtils,
                                   final GenreableFactory genreableFactory) {
        this.failsafe = failsafe;
        this.profileFactory = profileFactory;
        this.optionalUtils = optionalUtils;
        this.genreableFactory = genreableFactory;
    }

    @Override
    public GroupArtist create(final String groupName, final String isni, final TypeGroup groupType) {
        final MutableProfile profile = this.profileFactory.create(groupName, isni);
        final Genreable genreable = this.genreableFactory.create();
        return new GroupArtistImpl(genreable, profile, groupType, this.optionalUtils, this.failsafe);
    }

    @Override
    public GroupArtist create(final ArtistKey key) {
        this.failsafe.checkArgument().isNotNull(logger, key, "key");

        return create(key.name(), key.isni(), key.groupType());
    }

    @Override
    public GroupArtist createEmpty() {
        return this.failsafe.handleException().unsupportedOperation("Cannot create an empty GroupArtist");
    }

    @Override
    public String toString() {
        return "GroupArtistFactoryImpl{" +
                "injector=" + this.failsafe +
                ", profileFactory=" + this.profileFactory +
                ", optionalUtils=" + this.optionalUtils +
                ", genreableFactory=" + this.genreableFactory +
                "}";
    }
}
