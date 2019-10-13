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
package org.rookit.dm.play.able.event;

import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

final class MutableEventStatsImpl implements MutableEventStats {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableEventStatsImpl.class);

    private final Failsafe failsafe;
    private long count;
    private LocalDate date;
    private final OptionalFactory optionalFactory;

    MutableEventStatsImpl(final Failsafe failsafe,
                          final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
        this.count = 0;
    }

    @Override
    public void setCount(final long count) {
        this.failsafe.checkArgument().number().isNotNegative(logger, count, "count");
        this.count = count;
    }

    @Override
    public void touch() {
        this.count++;
        this.date = LocalDate.now();
    }

    @Override
    public void setLast(final LocalDate last) {
        this.failsafe.checkArgument().time().isNotFuture(logger, last, "last");
        this.date = last;
    }

    @Override
    public long count() {
        return this.count;
    }

    @Override
    public Optional<LocalDate> lastDate() {
        return this.optionalFactory.ofNullable(this.date);
    }

    @Override
    public String toString() {
        return "MutableEventStatsImpl{" +
                "injector=" + this.failsafe +
                ", count=" + this.count +
                ", date=" + this.date +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
