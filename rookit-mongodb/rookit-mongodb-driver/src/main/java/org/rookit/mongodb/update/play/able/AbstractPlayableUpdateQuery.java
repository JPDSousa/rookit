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
package org.rookit.mongodb.update.play.able;

import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.able.GenericPlayableMetaType;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.api.storage.update.PlayableUpdateQuery;
import org.rookit.api.storage.update.filter.PlayableUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.AbstractRookitModelUpdateQuery;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public abstract class AbstractPlayableUpdateQuery<E extends Playable, Q extends PlayableUpdateQuery<Q, S>,
        S extends PlayableUpdateFilterQuery<S>, P extends GenericPlayableMetaType<E>>
        extends AbstractRookitModelUpdateQuery<E, Q, S, P> implements PlayableUpdateQuery<Q, S> {

    protected AbstractPlayableUpdateQuery(final UpdateFilterQueryFactory<S> filterQueryFactory,
                                          final List<Bson> updates,
                                          final P properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public Q setDuration(final Duration duration) {
        return appendUpdate(Updates.set(properties().duration().propertyName(), duration));
    }

    @Override
    public Q removeSkipStatsLastDate() {
        final String propertyName = properties().skipStats().lastDate().propertyName();

        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q setPlayStatsLastDate(final LocalDate playStatsLastDate) {
        final String propertyName = properties().playStats().lastDate().propertyName();

        return appendUpdate(Updates.set(propertyName, playStatsLastDate));
    }

    @Override
    public Q removeDuration() {
        return appendUpdate(Updates.unset(properties().duration().propertyName()));
    }

    @Override
    public Q setSkipStatsLastDate(final LocalDate skipStatsLastDate) {
        final String propertyName = properties().skipStats().lastDate().propertyName();

        return appendUpdate(Updates.set(propertyName, skipStatsLastDate));
    }

    @Override
    public Q removePlayStatsLastDate() {
        final String propertyName = properties().playStats().lastDate().propertyName();

        return appendUpdate(Updates.unset(propertyName));
    }

    @SuppressWarnings("AutoBoxing") // api limitation
    @Override
    public Q setPlayStatsCount(final long playStatsCount) {
        final String propertyName = properties().playStats().count().propertyName();

        return appendUpdate(Updates.set(propertyName, playStatsCount));
    }

    @SuppressWarnings("AutoBoxing") // api limitation
    @Override
    public Q setSkipStatsCount(final long skipStatsCount) {
        final String propertyName = properties().skipStats().count().propertyName();

        return appendUpdate(Updates.set(propertyName, skipStatsCount));
    }
}
