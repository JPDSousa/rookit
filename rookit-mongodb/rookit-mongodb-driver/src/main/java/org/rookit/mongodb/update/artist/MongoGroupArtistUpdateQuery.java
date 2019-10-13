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
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.GroupArtistMetaType;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.storage.update.GroupArtistUpdateQuery;
import org.rookit.api.storage.update.filter.GroupArtistUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;

import java.util.Collection;
import java.util.List;

final class MongoGroupArtistUpdateQuery extends AbstractMongoArtistUpdateQuery<GroupArtist, GroupArtistUpdateQuery,
        GroupArtistUpdateFilterQuery, GroupArtistMetaType> implements GroupArtistUpdateQuery {

    MongoGroupArtistUpdateQuery(final UpdateFilterQueryFactory<GroupArtistUpdateFilterQuery> filterQueryFactory,
                                final List<Bson> updates,
                                final GroupArtistMetaType properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public GroupArtistUpdateQuery removeAllMembers(final Collection<Musician> members) {
        final String propertyName = properties().members().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(members)));
    }

    @Override
    public GroupArtistUpdateQuery removeMembers(final Musician members) {
        return removeAllMembers(ImmutableList.of(members));
    }

    @Override
    public GroupArtistUpdateQuery setGroupType(final TypeGroup groupType) {
        final String propertyName = properties().groupType().propertyName();
        return appendUpdate(Updates.set(propertyName, groupType));
    }

    @Override
    public GroupArtistUpdateQuery addAllMembers(final Collection<Musician> members) {
        final String propertyName = properties().members().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(members)));
    }

    @Override
    public GroupArtistUpdateQuery addMembers(final Musician members) {
        return addAllMembers(ImmutableList.of(members));
    }

    @Override
    protected GroupArtistUpdateQuery self() {
        return this;
    }
}
