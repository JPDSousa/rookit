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
package org.rookit.failsafe.comparable;

import com.google.common.collect.Range;
import org.rookit.failsafe.ComparableFailsafe;
import org.rookit.failsafe.ObjectFailsafe;
import org.rookit.failsafe.object.DelegateObjectFailsafe;
import org.slf4j.Logger;

final class BaseComparableFailsafe extends DelegateObjectFailsafe implements ComparableFailsafe {

    BaseComparableFailsafe(final ObjectFailsafe delegate) {
        super(delegate);
    }

    @Override
    public <T, R extends Comparable<R>> T isBetween(final Logger logger,
                                                    final R object,
                                                    final Range<R> range,
                                                    final String objectName) {
        return is(logger, range.contains(object), "Range for %s is %s but value is %s",
                objectName, range, object);
    }

}
