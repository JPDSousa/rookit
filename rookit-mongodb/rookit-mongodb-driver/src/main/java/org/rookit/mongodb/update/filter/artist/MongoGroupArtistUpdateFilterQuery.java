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
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.storage.query.GroupArtistQuery;
import org.rookit.api.storage.update.filter.GroupArtistUpdateFilterQuery;

import java.util.Collection;

final class MongoGroupArtistUpdateFilterQuery extends AbstractMongoArtistUpdateFilterQuery<GroupArtist, 
        GroupArtistQuery, GroupArtistUpdateFilterQuery> implements GroupArtistUpdateFilterQuery {

    MongoGroupArtistUpdateFilterQuery(final MongoCollection<GroupArtist> collection, 
                                      final Bson update, 
                                      final GroupArtistQuery query) {
        super(collection, update, query);
    }

    @Override
    public GroupArtistUpdateFilterQuery withMembers(final Collection<Musician> present) {
        return appendClause(mongoQuery -> mongoQuery.withMembers(present));
    }

    @Override
    public GroupArtistUpdateFilterQuery withNoMembers(final Collection<Musician> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoMembers(absent));
    }

    @Override
    public GroupArtistUpdateFilterQuery withMembers(final Musician present) {
        return appendClause(mongoQuery -> mongoQuery.withMembers(present));
    }

    @Override
    public GroupArtistUpdateFilterQuery withGroupType(final TypeGroup groupType) {
        return appendClause(mongoQuery -> mongoQuery.withGroupType(groupType));
    }

    @Override
    public GroupArtistUpdateFilterQuery withNoMembers(final Musician absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoMembers(absent));
    }

    @Override
    protected GroupArtistUpdateFilterQuery self() {
        return this;
    }
}
