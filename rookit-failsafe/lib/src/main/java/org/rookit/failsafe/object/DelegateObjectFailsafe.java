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
package org.rookit.failsafe.object;

import org.rookit.failsafe.ObjectFailsafe;
import org.slf4j.Logger;

public class DelegateObjectFailsafe implements ObjectFailsafe {

    private final ObjectFailsafe delegate;

    protected DelegateObjectFailsafe(final ObjectFailsafe delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings("BooleanParameter") // necessary evil
    @Override
    public <T> T is(final Logger logger, final boolean condition, final String message, final Object... args) {
        return this.delegate.is(logger, condition, message, args);
    }

    @Override
    public <T> T isEquals(final Logger logger, final Object expected, final Object actual, final String name) {
        return this.delegate.isEquals(logger, expected, actual, name);
    }

    @Override
    public <T> T isNotEquals(final Logger logger, final Object object, final Object unexpected, final String objectName) {
        return this.delegate.isNotEquals(logger, object, unexpected, objectName);
    }

    @Override
    public <T> T isNotNull(final Logger logger, final Object object, final String name) {
        return this.delegate.isNotNull(logger, object, name);
    }

    @Override
    public <T> T isNull(final Logger logger, final Object object, final String name) {
        return this.delegate.isNull(logger, object, name);
    }

    @Override
    public String toString() {
        return "DelegateObjectFailsafe{" +
                "delegate=" + this.delegate +
                "}";
    }
}
