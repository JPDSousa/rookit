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
package org.rookit.dm.artist.timeline;

import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

final class MutableTimelineImpl implements MutableTimeline {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableTimelineImpl.class);

    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;
    private LocalDate beginDate;
    private LocalDate endDate;

    MutableTimelineImpl(final Failsafe failsafe, final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public void setBegin(final LocalDate date) {
        this.failsafe.checkArgument().isNotNull(logger, date, "date");
        final Optional<LocalDate> endOrNone = end();
        if (endOrNone.isPresent()) {
            final LocalDate end = endOrNone.get();
            this.failsafe.checkArgument().is(logger, end.isAfter(date), "%s is after the end date %s",
                    date, end);
        }
        this.beginDate = date;
    }

    @Override
    public void setEnd(final LocalDate date) {
        this.failsafe.checkArgument().isNotNull(logger, date, "date");
        final Optional<LocalDate> beginOrNone = begin();
        if (beginOrNone.isPresent()) {
            final LocalDate begin = beginOrNone.get();
            this.failsafe.checkArgument().is(logger, begin.isBefore(date), "%s is before the begin date %s",
                    date, begin);
        }
        this.endDate = date;
    }

    @Override
    public Optional<LocalDate> begin() {
        return this.optionalFactory.ofNullable(this.beginDate);
    }

    @Override
    public Optional<LocalDate> end() {
        return this.optionalFactory.ofNullable(this.endDate);
    }

    @Override
    public String toString() {
        return "MutableTimelineImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                ", beginDate=" + this.beginDate +
                ", endDate=" + this.endDate +
                "}";
    }
}
