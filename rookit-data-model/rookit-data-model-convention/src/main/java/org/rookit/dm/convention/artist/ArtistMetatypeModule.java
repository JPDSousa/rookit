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
package org.rookit.dm.convention.artist;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.external.meta.ExternalIdentifiersMetatype;
import org.rookit.api.dm.artist.name.meta.ArtistNameMetatype;
import org.rookit.api.dm.artist.profile.Profile;
import org.rookit.api.dm.artist.profile.meta.ProfileMetatype;
import org.rookit.api.dm.artist.profile.meta.ProfileMetatypeImpl;
import org.rookit.api.dm.artist.timeline.meta.TimelineMetatype;
import org.rookit.api.meta.ExternalIdentifiers;
import org.rookit.api.meta.Name;
import org.rookit.api.meta.Origin;
import org.rookit.api.meta.Picture;
import org.rookit.api.meta.Timeline;
import org.rookit.convention.Metatype;
import org.rookit.convention.property.MutableOptionalPropertyModel;
import org.rookit.convention.property.MutablePropertyModel;
import org.rookit.io.data.DataBucket;

@SuppressWarnings("MethodMayBeStatic")
public final class ArtistMetatypeModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new ArtistMetatypeModule(),
            org.rookit.api.dm.artist.meta.ArtistMetatypeModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private ArtistMetatypeModule() {}

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    @org.rookit.api.meta.Profile
    ProfileMetatype<Artist> profileArtist(
            final Metatype<Profile> metatype,
            @ExternalIdentifiers final ExternalIdentifiersMetatype<Profile> externalIdentifiers,
            @Origin final MutableOptionalPropertyModel<Profile, CountryCode> origin,
            @Name final ArtistNameMetatype<Profile> name,
            @Timeline final TimelineMetatype<Profile> timeline,
            @Picture final MutablePropertyModel<Profile, DataBucket> picture) {
        return ProfileMetatypeImpl.create(metatype, "profile", Artist::profile, externalIdentifiers, name,
                origin, picture, timeline);
    }

    @Provides
    @Singleton
    @org.rookit.api.meta.Profile
    ProfileMetatype<Musician> profileMusician(
            final Metatype<Profile> metatype,
            @ExternalIdentifiers final ExternalIdentifiersMetatype<Profile> externalIdentifiers,
            @Origin final MutableOptionalPropertyModel<Profile, CountryCode> origin,
            @Name final ArtistNameMetatype<Profile> name,
            @Timeline final TimelineMetatype<Profile> timeline,
            @Picture final MutablePropertyModel<Profile, DataBucket> picture) {
        return ProfileMetatypeImpl.create(metatype, "profile", Musician::profile, externalIdentifiers, name,
                origin, picture, timeline);
    }

    @Provides
    @Singleton
    @org.rookit.api.meta.Profile
    ProfileMetatype<GroupArtist> profileGroupArtist(
            final Metatype<Profile> metatype,
            @ExternalIdentifiers final ExternalIdentifiersMetatype<Profile> externalIdentifiers,
            @Origin final MutableOptionalPropertyModel<Profile, CountryCode> origin,
            @Name final ArtistNameMetatype<Profile> name,
            @Timeline final TimelineMetatype<Profile> timeline,
            @Picture final MutablePropertyModel<Profile, DataBucket> picture) {
        return ProfileMetatypeImpl.create(metatype, "profile", GroupArtist::profile, externalIdentifiers, name,
                origin, picture, timeline);
    }

}
