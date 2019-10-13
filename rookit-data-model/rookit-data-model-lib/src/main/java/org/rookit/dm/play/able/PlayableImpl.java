/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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

package org.rookit.dm.play.able;

import org.rookit.api.dm.RookitModel;
import org.rookit.api.dm.play.able.EventStats;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.DelegateRookitModel;
import org.rookit.dm.play.able.event.MutableEventStats;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;

@SuppressWarnings("javadoc")
final class PlayableImpl extends DelegateRookitModel implements Playable {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PlayableImpl.class);

    private final MutableEventStats plays;
    private final MutableEventStats skips;

    private Duration duration;
    private final OptionalFactory optionalFactory;
    private final Failsafe failsafe;

    PlayableImpl(final MutableEventStatsFactory factory,
                 final RookitModel rookitModel,
                 final OptionalFactory optionalFactory,
                 final Failsafe failsafe) {
        super(rookitModel);
        this.plays = factory.createEmpty();
        this.skips = factory.createEmpty();
        this.optionalFactory = optionalFactory;
        this.failsafe = failsafe;
    }

    @Override
    public Optional<Duration> duration() {
        return this.optionalFactory.ofNullable(this.duration);
    }

    @Override
    public void play() {
        this.plays.touch();
    }

    @Override
    public void setDuration(final Duration duration) {
        this.failsafe.checkArgument().isNotNull(logger, duration, "duration");
        this.duration = duration;
    }

    @Override
    public void setLastPlayed(final LocalDate lastPlayed) {
        this.failsafe.checkArgument().isNotNull(logger, lastPlayed, "lastPlayed");
        this.plays.setLast(lastPlayed);
    }

    @Override
    public void setLastSkipped(final LocalDate lastSkipped) {
        this.failsafe.checkArgument().isNotNull(logger, lastSkipped, "lastSkipped");
        this.skips.setLast(lastSkipped);
    }

    @Override
    public void setPlays(final long plays) {
        this.failsafe.checkArgument().number().isPositive(logger, plays, "plays");
        this.plays.setCount(plays);
    }

    @Override
    public void setSkipped(final long skipped) {
        this.failsafe.checkArgument().number().isPositive(logger, skipped, "skipped");
        this.skips.setCount(skipped);
    }

    @Override
    public void skip() {
        this.skips.touch();
    }

    @Override
    public EventStats playStats() {
        return this.plays;
    }

    @Override
    public EventStats skipStats() {
        return this.skips;
    }

    @Override
    public String toString() {
        return "PlayableImpl{" +
                "plays=" + this.plays +
                ", skips=" + this.skips +
                ", duration=" + this.duration +
                ", optionalFactory=" + this.optionalFactory +
                ", injector=" + this.failsafe +
                "} " + super.toString();
    }
}
