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
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.GroupArtistMetaType;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.storage.query.ArtistQuery;
import org.rookit.api.storage.query.GroupArtistQuery;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;

import java.util.Collection;

final class MongoGroupArtistQuery extends AbstractMongoArtistQuery<GroupArtist, GroupArtistQuery,
        GroupArtistMetaType> implements GroupArtistQuery {

    private final MongoQueryFactory<Artist, ArtistQuery> queryFactory;

    MongoGroupArtistQuery(final GroupArtistMetaType properties,
                          final Bson initialClause,
                          final MongoQueryFactory<Artist, ArtistQuery> queryFactory,
                          final MongoQueryResultFactory<GroupArtist> resultFactory) {
        super(properties, initialClause, resultFactory);
        this.queryFactory = queryFactory;
    }

    @Override
    public GroupArtistQuery withMembers(final Collection<Musician> present) {
        return addQueryClause(Filters.all(properties().members().propertyName(), present));
    }

    @Override
    public GroupArtistQuery withNoMembers(final Collection<Musician> absent) {
        return addQueryClause(Filters.nin(properties().members().propertyName(), absent));
    }

    @Override
    public GroupArtistQuery withMembers(final Musician present) {
        return withMembers(ImmutableSet.of(present));
    }

    @Override
    public GroupArtistQuery withGroupType(final TypeGroup groupType) {
        return addQueryClause(Filters.eq(properties().groupType().propertyName(), groupType));
    }

    @Override
    public GroupArtistQuery withNoMembers(final Musician absent) {
        return withNoMembers(ImmutableSet.of(absent));
    }

    @Override
    public ArtistQuery toArtistQuery() {
        return this.queryFactory.create(this);
    }

    @Override
    protected GroupArtistQuery self() {
        return this;
    }

    @Override
    public String toString() {
        return "MongoGroupArtistQuery{" +
                "queryFactory=" + this.queryFactory +
                "} " + super.toString();
    }
}
