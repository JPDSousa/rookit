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
package org.rookit.test.generator.number;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.generator.AbstractGenerator;

import java.util.Random;

final class LongGeneratorImpl extends AbstractGenerator<Long> implements LongGenerator {

    private final Random random;

    @Inject
    LongGeneratorImpl(final Failsafe failsafe, final Random random) {
        super(failsafe, random);
        this.random = random;
    }

    @Override
    public long createRandomLong() {
        return this.random.nextLong();
    }

    @Override
    public long getAsLong() {
        return createRandomLong();
    }

    @SuppressWarnings("AutoBoxing")
    @Override
    public Long createRandom() {
        return createRandomLong();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("random", this.random)
                .toString();
    }
}
