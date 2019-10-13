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
package org.rookit.mongodb.query.artist;

import com.google.common.collect.ImmutableSet;
import com.mongodb.client.model.Filters;
import com.neovisionaries.i18n.CountryCode;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GenericArtistMetaType;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.storage.query.GenericArtistQuery;
import org.rookit.mongodb.query.MongoQueryResultFactory;
import org.rookit.mongodb.query.genre.AbstractMongoGenreableQuery;

import java.time.LocalDate;
import java.util.Collection;

abstract class AbstractMongoArtistQuery<E extends Artist, Q extends GenericArtistQuery<E, Q>,
        P extends GenericArtistMetaType<E>> extends AbstractMongoGenreableQuery<E, Q, P>
        implements GenericArtistQuery<E, Q> {

    AbstractMongoArtistQuery(final P properties,
                             final Bson initialClause,
                             final MongoQueryResultFactory<E> resultFactory) {
        super(properties, initialClause, resultFactory);
    }

    @Override
    public Q withRelatedArtists(final Collection<Artist> present) {
        return addQueryClause(Filters.all(properties().relatedArtists().propertyName(), present));
    }

    @Override
    public Q withAnyProfileOrigin() {
        final String propertyName = properties().profile().origin().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAnyProfileExternalIdentifiersIpi() {
        final String propertyName = properties().profile().externalIdentifiers().ipi().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withNoProfileOrigin() {
        final String propertyName = properties().profile().origin().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withNoRelatedArtists(final Collection<Artist> absent) {
        return addQueryClause(Filters.nin(properties().relatedArtists().propertyName(), absent));
    }

    @Override
    public Q withNoProfileExternalIdentifiersIpi() {
        final String propertyName = properties().profile().externalIdentifiers().ipi().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAnyProfileTimelineEnd() {
        final String propertyName = properties().profile().timeline().end().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withProfileNameAliases(final Collection<String> present) {
        final String propertyName = properties().profile().name().aliases().propertyName();

        return addQueryClause(Filters.all(propertyName, present));
    }

    @Override
    public Q withProfileTimelineBeginBetween(final LocalDate before, final LocalDate after) {
        final String propertyName = properties().profile().timeline().begin().propertyName();

        return addQueryClause(Filters.and(
                Filters.gt(propertyName, before),
                Filters.lt(propertyName, after)
        ));
    }

    @Override
    public Q withProfileTimelineEnd(final LocalDate profileTimelineEnd) {
        final String propertyName = properties().profile().timeline().end().propertyName();

        return addQueryClause(Filters.eq(propertyName, profileTimelineEnd));
    }

    @Override
    public Q withProfileNameOfficial(final String profileNameOfficial) {
        final String propertyName = properties().profile().name().official().propertyName();

        return addQueryClause(Filters.eq(propertyName, profileNameOfficial));
    }

    @Override
    public Q withNoRelatedArtists(final Artist absent) {
        return withNoRelatedArtists(ImmutableSet.of(absent));
    }

    @Override
    public Q withRelatedArtists(final Artist present) {
        return withRelatedArtists(ImmutableSet.of(present));
    }

    @Override
    public Q withProfileTimelineEndBetween(final LocalDate before, final LocalDate after) {
        final String propertyName = properties().profile().timeline().end().propertyName();

        return addQueryClause(Filters.and(
                Filters.gt(propertyName, before),
                Filters.lt(propertyName, after)
        ));
    }

    @Override
    public Q withNoProfileTimelineBegin() {
        final String propertyName = properties().profile().timeline().begin().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withType(final TypeArtist type) {
        return addQueryClause(Filters.eq(properties().type().propertyName(), type));
    }

    @Override
    public Q withProfileTimelineBegin(final LocalDate profileTimelineBegin) {
        final String propertyName = properties().profile().timeline().begin().propertyName();

        return addQueryClause(Filters.eq(propertyName, profileTimelineBegin));
    }

    @Override
    public Q withNoProfileNameAliases(final String absent) {
        return withNoProfileNameAliases(ImmutableSet.of(absent));
    }

    @Override
    public Q withProfileOrigin(final CountryCode profileOrigin) {
        final String propertyName = properties().profile().origin().propertyName();

        return addQueryClause(Filters.eq(propertyName, profileOrigin));
    }

    @Override
    public Q withProfileTimelineEndAfter(final LocalDate profileTimelineEnd) {
        final String propertyName = properties().profile().timeline().end().propertyName();

        return addQueryClause(Filters.gt(propertyName, profileTimelineEnd));
    }

    @Override
    public Q withProfilePicture(final BiStream profilePicture) {
        final String propertyName = properties().profile().picture().propertyName();
        return addQueryClause(Filters.eq(propertyName, profilePicture));
    }

    @Override
    public Q withAnyProfileTimelineBegin() {
        final String propertyName = properties().profile().timeline().begin().propertyName();
        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withNoProfileTimelineEnd() {
        final String propertyName = properties().profile().timeline().end().propertyName();
        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withProfileNameAliases(final String present) {
        return withProfileNameAliases(ImmutableSet.of(present));
    }

    @Override
    public Q withProfileTimelineBeginBefore(final LocalDate profileTimelineBegin) {
        final String propertyName = properties().profile().timeline().begin().propertyName();

        return addQueryClause(Filters.lt(propertyName, profileTimelineBegin));
    }

    @Override
    public Q withProfileTimelineEndBefore(final LocalDate profileTimelineEnd) {
        final String propertyName = properties().profile().timeline().end().propertyName();

        return addQueryClause(Filters.lt(propertyName, profileTimelineEnd));
    }

    @Override
    public Q withNoProfileNameAliases(final Collection<String> absent) {
        final String propertyName = properties().profile().name().aliases().propertyName();

        return addQueryClause(Filters.nin(propertyName, absent));
    }

    @Override
    public Q withProfileExternalIdentifiersIpi(final String profileExternalIdentifiersIpi) {
        final String propertyName = properties().profile().externalIdentifiers().ipi().propertyName();

        return addQueryClause(Filters.eq(propertyName, profileExternalIdentifiersIpi));
    }

    @Override
    public Q withProfileExternalIdentifiersIsni(final String profileExternalIdentifiersIsni) {
        final String propertyName = properties().profile().externalIdentifiers().isni().propertyName();

        return addQueryClause(Filters.eq(propertyName, profileExternalIdentifiersIsni));
    }

    @Override
    public Q withProfileTimelineBeginAfter(final LocalDate profileTimelineBegin) {
        final String propertyName = properties().profile().timeline().begin().propertyName();

        return addQueryClause(Filters.gt(propertyName, profileTimelineBegin));
    }
}
