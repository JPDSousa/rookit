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
package org.rookit.mongodb.update.filter.play;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.DynamicPlaylist;
import org.rookit.api.storage.query.DynamicPlaylistQuery;
import org.rookit.api.storage.update.filter.DynamicPlaylistUpdateFilterQuery;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.update.MongoUpdateFilterQueryFactory;

final class MongoDynamicPlaylistUpdateFilterQueryFactory
        implements MongoUpdateFilterQueryFactory<DynamicPlaylistUpdateFilterQuery> {

    private final MongoCollection<DynamicPlaylist> collection;
    private final MongoQueryFactory<DynamicPlaylist, DynamicPlaylistQuery> queryFactory;

    @Inject
    private MongoDynamicPlaylistUpdateFilterQueryFactory(
            final MongoCollection<DynamicPlaylist> collection,
            final MongoQueryFactory<DynamicPlaylist, DynamicPlaylistQuery> queryFactory) {
        this.collection = collection;
        this.queryFactory = queryFactory;
    }

    @Override
    public DynamicPlaylistUpdateFilterQuery create(final Bson update) {
        return new MongoDynamicPlaylistUpdateFilterQuery(this.collection, update, this.queryFactory.create());
    }

    @Override
    public String toString() {
        return "MongoDynamicPlaylistUpdateFilterQueryFactory{" +
                "collection=" + this.collection +
                ", queryFactory=" + this.queryFactory +
                "}";
    }
}
