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
package org.rookit.failsafe.number;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import org.rookit.failsafe.NumberFailsafe;
import org.rookit.failsafe.ObjectFailsafe;
import org.rookit.failsafe.object.DelegateObjectFailsafe;
import org.slf4j.Logger;

final class BaseNumberFailsafe extends DelegateObjectFailsafe implements NumberFailsafe {

    BaseNumberFailsafe(final ObjectFailsafe delegate) {
        super(delegate);
    }

    @Override
    public <T> T isNotEquals(final Logger logger, final double value, final double unexpected, final String valueName) {
        //noinspection AutoBoxing
        return is(logger, Double.compare(value, unexpected) != 0,
                "The %s cannot be equal to %d", valueName, unexpected);
    }

    @Override
    public <T> T isPositive(final Logger logger, final int object, final String name) {
        return is(logger, object > 0, "%s must be greater than 0", name);
    }

    @Override
    public <T> T isPositive(final Logger logger, final long object, final String name) {
        return is(logger, object > 0L, "%s must be greater than 0", name);
    }

    @Override
    public <T> T isSum(final Logger logger, final DoubleCollection values, final double expected) {
        double sum = 0;
        final DoubleIterator it = values.iterator();
        while (it.hasNext()) {
            sum += it.nextDouble();
        }

        //noinspection AutoBoxing
        return is(logger, Double.compare(expected, sum) == 0,
                "The sum of all values must be %s", expected);
    }

    @Override
    public <T> T isNotNegative(final Logger logger, final long count, final String name) {
        return is(logger, count >= 0, "%s of variable cannot be negative.", count, name);
    }

}
