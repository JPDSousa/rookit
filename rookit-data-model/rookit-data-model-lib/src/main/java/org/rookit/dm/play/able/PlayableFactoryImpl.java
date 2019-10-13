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

import com.google.inject.Inject;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.dm.RookitModelFactory;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.api.dm.play.able.PlayableFactory;
import org.rookit.dm.play.able.event.MutableEventStatsFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;

final class PlayableFactoryImpl implements PlayableFactory {

    private final Failsafe failsafe;
    private final MutableEventStatsFactory eventStatsFactory;
    private final RookitModelFactory rookitModelFactory;
    private final OptionalFactory optionalFactory;

    @Inject
    private PlayableFactoryImpl(final Failsafe failsafe,
                                final MutableEventStatsFactory eventStatsFactory,
                                final RookitModelFactory rookitModelFactory,
                                final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.eventStatsFactory = eventStatsFactory;
        this.rookitModelFactory = rookitModelFactory;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Playable create() {
        final RookitModel rookitModel = this.rookitModelFactory.create();
        return new PlayableImpl(this.eventStatsFactory, rookitModel, this.optionalFactory, this.failsafe);
    }

    @Override
    public String toString() {
        return "PlayableFactoryImpl{" +
                "injector=" + this.failsafe +
                ", eventStatsFactory=" + this.eventStatsFactory +
                ", rookitModelFactory=" + this.rookitModelFactory +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
