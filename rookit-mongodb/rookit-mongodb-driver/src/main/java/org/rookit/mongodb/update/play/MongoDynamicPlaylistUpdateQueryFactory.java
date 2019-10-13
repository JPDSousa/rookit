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
package org.rookit.mongodb.update.play;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.DynamicPlaylistMetaType;
import org.rookit.api.storage.update.DynamicPlaylistUpdateQuery;
import org.rookit.api.storage.update.filter.DynamicPlaylistUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.MongoUpdateQueryFactory;

final class MongoDynamicPlaylistUpdateQueryFactory implements MongoUpdateQueryFactory<DynamicPlaylistUpdateQuery,
        DynamicPlaylistUpdateFilterQuery> {

    private final UpdateFilterQueryFactory<DynamicPlaylistUpdateFilterQuery> factory;
    private final DynamicPlaylistMetaType properties;

    @Inject
    private MongoDynamicPlaylistUpdateQueryFactory(
            final UpdateFilterQueryFactory<DynamicPlaylistUpdateFilterQuery> factory,
            final DynamicPlaylistMetaType properties) {
        this.factory = factory;
        this.properties = properties;
    }

    @Override
    public DynamicPlaylistUpdateQuery create() {
        return new MongoDynamicPlaylistUpdateQuery(this.factory, ImmutableList.of(), this.properties);
    }

    @Override
    public DynamicPlaylistUpdateQuery create(final Bson initialState) {
        return new MongoDynamicPlaylistUpdateQuery(this.factory, ImmutableList.of(initialState), this.properties);
    }

    @Override
    public String toString() {
        return "MongoDynamicPlaylistUpdateQueryFactory{" +
                "factory=" + this.factory +
                ", properties=" + this.properties +
                "}";
    }
}
