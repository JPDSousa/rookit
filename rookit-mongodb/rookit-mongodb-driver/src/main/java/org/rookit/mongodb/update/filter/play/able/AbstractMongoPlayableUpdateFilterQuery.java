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
package org.rookit.mongodb.update.filter.play.able;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.api.storage.query.PlayableQuery;
import org.rookit.api.storage.update.filter.PlayableUpdateFilterQuery;
import org.rookit.mongodb.update.filter.AbstractMongoRookitModelUpdateFilterQuery;
import org.rookit.utils.source.Prototype;

import java.time.Duration;
import java.time.LocalDate;

@Prototype
public abstract class AbstractMongoPlayableUpdateFilterQuery<E extends Playable, Q extends PlayableQuery<E, Q>,
        U extends PlayableUpdateFilterQuery<U>> extends AbstractMongoRookitModelUpdateFilterQuery<E, Q, U>
        implements PlayableUpdateFilterQuery<U> {

    protected AbstractMongoPlayableUpdateFilterQuery(final MongoCollection<E> collection,
                                                     final Bson update,
                                                     final Q query) {
        super(collection, update, query);
    }

    @Override
    public U withDurationAfter(final Duration duration) {
        return appendClause(mongoQuery -> mongoQuery.withDurationAfter(duration));
    }

    @Override
    public U withDurationBefore(final Duration duration) {
        return appendClause(mongoQuery -> mongoQuery.withDurationBefore(duration));
    }

    @Override
    public U withSkipStatsCount(final long skipStatsCount) {
        return appendClause(mongoQuery -> mongoQuery.withSkipStatsCount(skipStatsCount));
    }

    @Override
    public U withPlayStatsLastDateAfter(final LocalDate playStatsLastDate) {
        return appendClause(mongoQuery -> mongoQuery.withPlayStatsLastDateAfter(playStatsLastDate));
    }

    @Override
    public U withSkipStatsLastDateAfter(final LocalDate skipStatsLastDate) {
        return appendClause(mongoQuery -> mongoQuery.withSkipStatsLastDateAfter(skipStatsLastDate));
    }

    @Override
    public U withSkipStatsLastDateBetween(final LocalDate before, final LocalDate after) {
        return appendClause(mongoQuery -> mongoQuery.withSkipStatsLastDateBetween(before, after));
    }

    @Override
    public U withNoPlayStatsLastDate() {
        return appendClause(Q::withNoPlayStatsLastDate);
    }

    @Override
    public U withNoDuration() {
        return appendClause(Q::withNoDuration);
    }

    @Override
    public U withDuration(final Duration duration) {
        return appendClause(mongoQuery -> mongoQuery.withDuration(duration));
    }

    @Override
    public U withPlayStatsLastDateBefore(final LocalDate playStatsLastDate) {
        return appendClause(mongoQuery -> mongoQuery.withPlayStatsLastDateBefore(playStatsLastDate));
    }

    @Override
    public U withPlayStatsLastDateBetween(final LocalDate before, final LocalDate after) {
        return appendClause(mongoQuery -> mongoQuery.withPlayStatsLastDateBetween(before, after));
    }

    @Override
    public U withAnyPlayStatsLastDate() {
        return appendClause(Q::withAnyPlayStatsLastDate);
    }

    @Override
    public U withDurationBetween(final Duration before, final Duration after) {
        return appendClause(mongoQuery -> mongoQuery.withDurationBetween(before, after));
    }

    @Override
    public U withPlayStatsCount(final long playStatsCount) {
        return appendClause(mongoQuery -> mongoQuery.withPlayStatsCount(playStatsCount));
    }

    @Override
    public U withPlayStatsLastDate(final LocalDate playStatsLastDate) {
        return appendClause(mongoQuery -> mongoQuery.withPlayStatsLastDate(playStatsLastDate));
    }

    @Override
    public U withNoSkipStatsLastDate() {
        return appendClause(Q::withNoSkipStatsLastDate);
    }

    @Override
    public U withAnyDuration() {
        return appendClause(Q::withAnyDuration);
    }

    @Override
    public U withSkipStatsLastDateBefore(final LocalDate skipStatsLastDate) {
        return appendClause(mongoQuery -> mongoQuery.withSkipStatsLastDateBefore(skipStatsLastDate));
    }

    @Override
    public U withSkipStatsLastDate(final LocalDate skipStatsLastDate) {
        return appendClause(mongoQuery -> mongoQuery.withSkipStatsLastDate(skipStatsLastDate));
    }

    @Override
    public U withAnySkipStatsLastDate() {
        return appendClause(Q::withAnySkipStatsLastDate);
    }

}
