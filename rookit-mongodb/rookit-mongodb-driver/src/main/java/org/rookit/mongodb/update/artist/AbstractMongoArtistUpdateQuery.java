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
package org.rookit.mongodb.update.artist;

import com.google.common.collect.ImmutableList;
import com.mongodb.client.model.Updates;
import com.neovisionaries.i18n.CountryCode;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GenericArtistMetaType;
import org.rookit.api.storage.update.GenericArtistUpdateQuery;
import org.rookit.api.storage.update.filter.GenericArtistUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.genre.able.AbstractMongoGenreableUpdateQuery;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

abstract class AbstractMongoArtistUpdateQuery<E extends Artist, Q extends GenericArtistUpdateQuery<Q, S>,
        S extends GenericArtistUpdateFilterQuery<S>, P extends GenericArtistMetaType<E>>
        extends AbstractMongoGenreableUpdateQuery<E, Q, S, P> implements GenericArtistUpdateQuery<Q, S> {

    AbstractMongoArtistUpdateQuery(final UpdateFilterQueryFactory<S> filterQueryFactory,
                                   final List<Bson> updates,
                                   final P properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public Q addRelatedArtists(final Artist relatedArtists) {
        return addAllRelatedArtists(ImmutableList.of(relatedArtists));
    }

    @Override
    public Q removeRelatedArtists(final Artist relatedArtists) {
        return removeAllRelatedArtists(ImmutableList.of(relatedArtists));
    }

    @Override
    public Q addAllProfileNameAliases(final Collection<String> profileNameAliases) {
        final String propertyName = properties().profile().name().aliases().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(profileNameAliases)));
    }

    @Override
    public Q addProfileNameAliases(final String profileNameAliases) {
        return addAllProfileNameAliases(ImmutableList.of(profileNameAliases));
    }

    @Override
    public Q setProfileOrigin(final CountryCode profileOrigin) {
        final String propertyName = properties().profile().origin().propertyName();
        return appendUpdate(Updates.set(propertyName, profileOrigin));
    }

    @Override
    public Q setProfileTimelineBegin(final LocalDate profileTimelineBegin) {
        final String propertyName = properties().profile().timeline().begin().propertyName();
        return appendUpdate(Updates.set(propertyName, profileTimelineBegin));
    }

    @Override
    public Q removeProfileOrigin() {
        final String propertyName = properties().profile().origin().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAllProfileNameAliases(final Collection<String> profileNameAliases) {
        final String propertyName = properties().profile().name().aliases().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(profileNameAliases)));
    }

    @Override
    public Q removeAllRelatedArtists(final Collection<Artist> relatedArtists) {
        final String propertyName = properties().relatedArtists().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(relatedArtists)));
    }

    @Override
    public Q removeProfileTimelineBegin() {
        final String propertyName = properties().profile().timeline().begin().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeProfileExternalIdentifiersIpi() {
        final String propertyName = properties().profile().externalIdentifiers().ipi().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeProfileNameAliases(final String profileNameAliases) {
        return removeAllProfileNameAliases(ImmutableList.of(profileNameAliases));
    }

    @Override
    public Q removeProfileTimelineEnd() {
        final String propertyName = properties().profile().timeline().end().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q addAllRelatedArtists(final Collection<Artist> relatedArtists) {
        final String propertyName = properties().relatedArtists().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(relatedArtists)));
    }

    @Override
    public Q setProfilePicture(final BiStream profilePicture) {
        final String propertyName = properties().profile().picture().propertyName();
        return appendUpdate(Updates.set(propertyName, profilePicture));
    }

    @Override
    public Q setProfileExternalIdentifiersIpi(final String profileExternalIdentifiersIpi) {
        final String propertyName = properties().profile().externalIdentifiers().ipi().propertyName();
        return appendUpdate(Updates.set(propertyName, profileExternalIdentifiersIpi));
    }

    @Override
    public Q setProfileTimelineEnd(final LocalDate profileTimelineEnd) {
        final String propertyName = properties().profile().timeline().end().propertyName();
        return appendUpdate(Updates.set(propertyName, profileTimelineEnd));
    }
}
