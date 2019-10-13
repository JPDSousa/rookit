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
package org.rookit.mongodb.update.filter.artist;

import com.mongodb.client.MongoCollection;
import com.neovisionaries.i18n.CountryCode;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.storage.filter.GenericArtistFilter;
import org.rookit.api.storage.query.GenericArtistQuery;
import org.rookit.api.storage.update.filter.GenericArtistUpdateFilterQuery;
import org.rookit.mongodb.update.filter.genre.able.AbstractMongoGenreableUpdateFilterQuery;

import java.time.LocalDate;
import java.util.Collection;

abstract class AbstractMongoArtistUpdateFilterQuery<E extends Artist, Q extends GenericArtistQuery<E, Q>,
        U extends GenericArtistUpdateFilterQuery<U>> extends AbstractMongoGenreableUpdateFilterQuery<E, Q, U> 
        implements GenericArtistUpdateFilterQuery<U> {

    AbstractMongoArtistUpdateFilterQuery(final MongoCollection<E> collection, 
                                         final Bson update, 
                                         final Q query) {
        super(collection, update, query);
    }

    @Override
    public U withAnyProfileOrigin() {
        return appendClause(GenericArtistFilter::withAnyProfileOrigin);
    }

    @Override
    public U withAnyProfileExternalIdentifiersIpi() {
        return appendClause(GenericArtistFilter::withAnyProfileExternalIdentifiersIpi);
    }

    @Override
    public U withRelatedArtists(final Collection<Artist> present) {
        return appendClause(mongoQuery -> mongoQuery.withRelatedArtists(present));
    }

    @Override
    public U withNoProfileOrigin() {
        return appendClause(GenericArtistFilter::withNoProfileOrigin);
    }

    @Override
    public U withNoRelatedArtists(final Collection<Artist> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoRelatedArtists(absent));
    }

    @Override
    public U withNoProfileExternalIdentifiersIpi() {
        return appendClause(GenericArtistFilter::withNoProfileExternalIdentifiersIpi);
    }

    @Override
    public U withProfileNameAliases(final Collection<String> present) {
        return appendClause(mongoQuery -> mongoQuery.withProfileNameAliases(present));
    }

    @Override
    public U withAnyProfileTimelineEnd() {
        return appendClause(GenericArtistFilter::withAnyProfileTimelineEnd);
    }

    @Override
    public U withProfileTimelineBeginBetween(final LocalDate before, final LocalDate after) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineBeginBetween(before, after));
    }

    @Override
    public U withProfileTimelineEnd(final LocalDate profileTimelineEnd) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineEnd(profileTimelineEnd));
    }

    @Override
    public U withProfileNameOfficial(final String profileNameOfficial) {
        return appendClause(mongoQuery -> mongoQuery.withProfileNameOfficial(profileNameOfficial));
    }

    @Override
    public U withNoRelatedArtists(final Artist absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoRelatedArtists(absent));
    }

    @Override
    public U withRelatedArtists(final Artist present) {
        return appendClause(mongoQuery -> mongoQuery.withRelatedArtists(present));
    }

    @Override
    public U withNoProfileTimelineBegin() {
        return appendClause(GenericArtistFilter::withNoProfileTimelineBegin);
    }

    @Override
    public U withProfileTimelineEndBetween(final LocalDate before, final LocalDate after) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineEndBetween(before, after));
    }

    @Override
    public U withType(final TypeArtist type) {
        return appendClause(mongoQuery -> mongoQuery.withType(type));
    }

    @Override
    public U withProfileTimelineBegin(final LocalDate profileTimelineBegin) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineBegin(profileTimelineBegin));
    }

    @Override
    public U withNoProfileNameAliases(final String absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoProfileNameAliases(absent));
    }

    @Override
    public U withProfileOrigin(final CountryCode profileOrigin) {
        return appendClause(mongoQuery -> mongoQuery.withProfileOrigin(profileOrigin));
    }

    @Override
    public U withProfileTimelineEndAfter(final LocalDate profileTimelineEnd) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineBeginAfter(profileTimelineEnd));
    }

    @Override
    public U withProfilePicture(final BiStream profilePicture) {
        return appendClause(mongoQuery -> mongoQuery.withProfilePicture(profilePicture));
    }

    @Override
    public U withAnyProfileTimelineBegin() {
        return appendClause(GenericArtistFilter::withAnyProfileTimelineBegin);
    }

    @Override
    public U withNoProfileTimelineEnd() {
        return appendClause(GenericArtistFilter::withNoProfileTimelineEnd);
    }

    @Override
    public U withProfileNameAliases(final String present) {
        return appendClause(mongoQuery -> mongoQuery.withProfileNameAliases(present));
    }

    @Override
    public U withProfileTimelineBeginBefore(final LocalDate profileTimelineBegin) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineBeginBefore(profileTimelineBegin));
    }

    @Override
    public U withProfileTimelineEndBefore(final LocalDate profileTimelineEnd) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineEndBefore(profileTimelineEnd));
    }

    @Override
    public U withNoProfileNameAliases(final Collection<String> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoProfileNameAliases(absent));
    }

    @Override
    public U withProfileExternalIdentifiersIsni(final String profileExternalIdentifiersIsni) {
        return appendClause(mongoQuery -> mongoQuery
                .withProfileExternalIdentifiersIsni(profileExternalIdentifiersIsni));
    }

    @Override
    public U withProfileExternalIdentifiersIpi(final String profileExternalIdentifiersIpi) {
        return appendClause(mongoQuery -> mongoQuery.withProfileExternalIdentifiersIpi(profileExternalIdentifiersIpi));
    }

    @Override
    public U withProfileTimelineBeginAfter(final LocalDate profileTimelineBegin) {
        return appendClause(mongoQuery -> mongoQuery.withProfileTimelineBeginAfter(profileTimelineBegin));
    }

}
