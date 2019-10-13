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
package org.rookit.mongodb.update;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.rookit.api.dm.GenericRookitModelMetaType;
import org.rookit.api.dm.RookitModel;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;

import java.util.List;

public abstract class AbstractRookitModelUpdateQuery<E extends RookitModel, Q extends RookitModelUpdateQuery<Q, S>,
        S extends RookitModelUpdateFilterQuery<S>, P extends GenericRookitModelMetaType<E>>
        implements RookitModelUpdateQuery<Q, S>, Bson {

    private final UpdateFilterQueryFactory<S> filterQueryFactory;
    private final List<Bson> updates;
    private final P properties;

    @SuppressWarnings("TypeMayBeWeakened")//due to semantic value
    protected AbstractRookitModelUpdateQuery(final UpdateFilterQueryFactory<S> filterQueryFactory,
                                             final List<Bson> updates,
                                             final P properties) {
        this.filterQueryFactory = filterQueryFactory;
        this.properties = properties;
        this.updates = Lists.newArrayList(updates);
    }

    protected P properties() {
        return this.properties;
    }

    protected Q appendUpdate(final Bson update) {
        this.updates.add(update);
        return self();
    }

    @Override
    public S where() {
        return this.filterQueryFactory.create();
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> tDocumentClass,
                                                   final CodecRegistry codecRegistry) {
        return Updates.combine(this.updates).toBsonDocument(tDocumentClass, codecRegistry);
    }

    protected abstract Q self();

    @Override
    public String toString() {
        return "AbstractRookitModelUpdateQuery{" +
                "filterQueryFactory=" + this.filterQueryFactory +
                ", updates=" + this.updates +
                ", properties=" + this.properties +
                "}";
    }
}
