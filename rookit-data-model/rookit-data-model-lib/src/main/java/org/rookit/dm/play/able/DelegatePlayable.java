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
package org.rookit.dm.play.able;

import org.rookit.api.dm.play.able.EventStats;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.utils.optional.Optional;

import java.time.Duration;
import java.time.LocalDate;

public class DelegatePlayable implements Playable {

    private final Playable delegate;

    protected DelegatePlayable(final Playable delegate) {
        this.delegate = delegate;
    }

    @Override
    public Optional<Duration> duration() {
        return this.delegate.duration();
    }

    @Override
    public EventStats playStats() {
        return this.delegate.playStats();
    }

    @Override
    public EventStats skipStats() {
        return this.delegate.skipStats();
    }

    @Override
    public Optional<String> id() {
        return this.delegate.id();
    }

    @Override
    public void setId(final String id) {
        this.delegate.setId(id);
    }

    @Override
    public void play() {
        this.delegate.play();
    }

    @Override
    public void setDuration(final Duration duration) {
        this.delegate.setDuration(duration);
    }

    @Override
    public void setLastPlayed(final LocalDate lastPlayed) {
        this.delegate.setLastPlayed(lastPlayed);
    }

    @Override
    public void setLastSkipped(final LocalDate lastSkipped) {
        this.delegate.setLastSkipped(lastSkipped);
    }

    @Override
    public void setPlays(final long plays) {
        this.delegate.setPlays(plays);
    }

    @Override
    public void setSkipped(final long skipped) {
        this.delegate.setSkipped(skipped);
    }

    @Override
    public void skip() {
        this.delegate.skip();
    }

    @Override
    public String toString() {
        return "DelegatePlayable{" +
                "delegate=" + this.delegate +
                "}";
    }
}
