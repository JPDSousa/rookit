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

import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.play.GenericPlaylistMetaType;
import org.rookit.api.dm.play.Playlist;
import org.rookit.api.storage.update.GenericPlaylistUpdateQuery;
import org.rookit.api.storage.update.filter.GenericPlaylistUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.genre.able.AbstractMongoGenreableUpdateQuery;

import java.util.List;

abstract class AbstractMongoPlaylistUpdateQuery<E extends Playlist, U extends GenericPlaylistUpdateQuery<U, S>,
        S extends GenericPlaylistUpdateFilterQuery<S>, P extends GenericPlaylistMetaType<E>>
        extends AbstractMongoGenreableUpdateQuery<E, U, S, P> implements GenericPlaylistUpdateQuery<U, S> {

    AbstractMongoPlaylistUpdateQuery(final UpdateFilterQueryFactory<S> filterQueryFactory,
                                     final List<Bson> updates,
                                     final P properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public U setImage(final BiStream image) {
        return appendUpdate(Updates.set(properties().image().propertyName(), image));
    }

}
