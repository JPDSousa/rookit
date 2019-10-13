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
package org.rookit.mongodb.query.play.able;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.able.GenericPlayableMetaType;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.api.storage.query.PlayableQuery;
import org.rookit.mongodb.query.AbstractMongoRookitModelQuery;
import org.rookit.mongodb.query.MongoQueryResultFactory;

import java.time.Duration;
import java.time.LocalDate;

public abstract class AbstractPlayableQuery<E extends Playable, 
        Q extends PlayableQuery<E, Q>, P extends GenericPlayableMetaType<E>>
        extends AbstractMongoRookitModelQuery<E, Q, P> implements org.rookit.api.storage.query.PlayableQuery<E, Q> {

    protected AbstractPlayableQuery(final P properties,
                                    final Bson initialClause,
                                    final MongoQueryResultFactory<E> resultFactory) {
        super(properties, initialClause, resultFactory);
    }

    @Override
    public Q withDurationAfter(final Duration duration) {
        // TODO implement
        return self();
    }

    @Override
    public Q withDurationBefore(final Duration duration) {
        // TODO implement
        return self();
    }

    @Override
    public Q withSkipStatsCount(final long skipStatsCount) {
        final String property = properties().skipStats().count().propertyName();

        return addQueryClause(Filters.eq(property, skipStatsCount));
    }

    @Override
    public Q withPlayStatsLastDateAfter(final LocalDate playStatsLastDate) {
        final String property = properties().playStats().lastDate().propertyName();

        return addQueryClause(Filters.gt(property, playStatsLastDate));
    }

    @Override
    public Q withSkipStatsLastDateAfter(final LocalDate skipStatsLastDate) {
        final String property = properties().skipStats().lastDate().propertyName();

        return addQueryClause(Filters.gt(property, skipStatsLastDate));
    }

    @Override
    public Q withSkipStatsLastDateBetween(final LocalDate before, final LocalDate after) {
        final String property = properties().skipStats().lastDate().propertyName();

        return addQueryClause(Filters.and(
                Filters.gt(property, before),
                Filters.lt(property, after)
        ));
    }

    @Override
    public Q withNoPlayStatsLastDate() {
        final String property = properties().playStats().lastDate().propertyName();

        return addQueryClause(Filters.exists(property, false));
    }

    @Override
    public Q withNoDuration() {
        return addQueryClause(Filters.exists(properties().duration().propertyName(), false));
    }

    @Override
    public Q withDuration(final Duration duration) {
        return addQueryClause(Filters.eq(properties().duration().propertyName(), duration));
    }

    @Override
    public Q withPlayStatsLastDateBefore(final LocalDate playStatsLastDate) {
        final String property = properties().playStats().lastDate().propertyName();

        return addQueryClause(Filters.lt(property, playStatsLastDate));
    }

    @Override
    public Q withPlayStatsLastDateBetween(final LocalDate before, final LocalDate after) {
        final String property = properties().playStats().lastDate().propertyName();

        return addQueryClause(Filters.and(
                Filters.gt(property, before),
                Filters.lt(property, after)
        ));
    }

    @Override
    public Q withAnyPlayStatsLastDate() {
        final String property = properties().playStats().lastDate().propertyName();

        return addQueryClause(Filters.exists(property));
    }

    @Override
    public Q withDurationBetween(final Duration before, final Duration after) {
        // TODO implement
        return self();
    }

    @Override
    public Q withPlayStatsLastDate(final LocalDate playStatsLastDate) {
        final String property = properties().playStats().lastDate().propertyName();

        return addQueryClause(Filters.eq(property, playStatsLastDate));
    }

    @Override
    public Q withPlayStatsCount(final long playStatsCount) {
        final String property = properties().playStats().count().propertyName();

        return addQueryClause(Filters.eq(property, playStatsCount));
    }

    @Override
    public Q withNoSkipStatsLastDate() {
        final String property = properties().skipStats().lastDate().propertyName();

        return addQueryClause(Filters.exists(property, false));
    }

    @Override
    public Q withAnyDuration() {
        return addQueryClause(Filters.exists(properties().duration().propertyName()));
    }

    @Override
    public Q withSkipStatsLastDateBefore(final LocalDate skipStatsLastDate) {
        final String property = properties().skipStats().lastDate().propertyName();

        return addQueryClause(Filters.lt(property, skipStatsLastDate));
    }

    @Override
    public Q withSkipStatsLastDate(final LocalDate skipStatsLastDate) {
        final String property = properties().skipStats().lastDate().propertyName();

        return addQueryClause(Filters.eq(property, skipStatsLastDate));
    }

    @Override
    public Q withAnySkipStatsLastDate() {
        return self();
    }

}
