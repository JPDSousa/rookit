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
package org.rookit.mongodb.query.ops;

import com.google.common.collect.Sets;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.dm.RookitModel;
import org.rookit.storage.query.QueryOps;
import org.rookit.api.storage.query.RookitModelQuery;
import org.rookit.failsafe.Failsafe;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

final class MongoQueryOps<E extends RookitModel, Q extends RookitModelQuery<E, Q>> implements QueryOps<E, Q> {

    private static final String ERROR_MESSAGE = "Clause %s is not of type %s, is is a requirement for %s.";

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MongoQueryOps.class);

    private final MongoQueryFactory<E, Q> factory;
    private final Failsafe failsafe;

    MongoQueryOps(final MongoQueryFactory<E, Q> factory,
                  final Failsafe failsafe) {
        this.factory = factory;
        this.failsafe = failsafe;
    }

    private Bson[] toBson(final Q[] clauses) {
        final int length = clauses.length;
        final Bson[] bson = new Bson[length];
        for (int i = 0; i < length; i++) {
            final Q clause = clauses[i];
            if (!(clause instanceof Bson)) {
                return this.failsafe.checkArgument().is(logger, false, ERROR_MESSAGE, clause, Bson.class, this);
            }
            bson[i] = (Bson) clause;
        }
        return bson;
    }

    private Collection<Bson> toBson(final Iterable<Q> clauses) {
        final Set<Bson> bson = Sets.newHashSet();
        for (final Q clause : clauses) {
            if (!(clause instanceof Bson)) {
                return this.failsafe.checkArgument().is(logger, false, ERROR_MESSAGE, clause, Bson.class, this);
            }
            bson.add((Bson) clause);
        }
        return bson;
    }

    @Override
    public Q and(final Q[] clauses) {
        return this.factory.create(Filters.and(toBson(clauses)));
    }

    @Override
    public Q and(final Collection<Q> clauses) {
        return this.factory.create(Filters.and(toBson(clauses)));
    }

    @Override
    public Q or(final Q[] clauses) {
        return this.factory.create(Filters.or(toBson(clauses)));
    }

    @Override
    public Q or(final Collection<Q> clauses) {
        return this.factory.create(Filters.and(toBson(clauses)));
    }

    @Override
    public String toString() {
        return "MongoQueryOps{" +
                "factory=" + this.factory +
                ", injector=" + this.failsafe +
                "}";
    }
}
