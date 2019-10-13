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

import com.google.inject.Inject;
import org.rookit.api.dm.artist.timeline.Timeline;
import org.rookit.api.dm.artist.timeline.TimelineFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

final class TimelineFactoryImpl implements TimelineFactory {

    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TimelineFactoryImpl.class);
    
    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    @Inject
    private TimelineFactoryImpl(final Failsafe failsafe, final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Timeline startAt(final LocalDate date) {
        this.failsafe.checkArgument().isNotNull(logger, date, "date");
        final MutableTimeline timeline = new MutableTimelineImpl(this.failsafe, this.optionalFactory);
        timeline.setBegin(date);
        return timeline;
    }

    @Override
    public Timeline endAt(final LocalDate date) {
        this.failsafe.checkArgument().isNotNull(logger, date, "date");
        final MutableTimeline timeline = new MutableTimelineImpl(this.failsafe, this.optionalFactory);
        timeline.setEnd(date);
        return timeline;
    }

    @Override
    public Timeline between(final LocalDate begin, final LocalDate end) {
        this.failsafe.checkArgument().isNotNull(logger, begin, "begin");
        this.failsafe.checkArgument().isNotNull(logger, end, "end");
        final MutableTimeline timeline = new MutableTimelineImpl(this.failsafe, this.optionalFactory);
        timeline.setBegin(begin);
        timeline.setEnd(end);
        return timeline;
    }

    @Override
    public Timeline create(final Key key) {
        logger.info("Creation by key is not supported. Falling back to createEmpty");
        return createEmpty();
    }

    @Override
    public Timeline createEmpty() {
        return new MutableTimelineImpl(this.failsafe, this.optionalFactory);
    }

    @Override
    public String toString() {
        return "TimelineFactoryImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
