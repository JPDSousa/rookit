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
package org.rookit.failsafe;

import com.google.inject.Inject;
import org.rookit.failsafe.guice.Argument;
import org.rookit.failsafe.guice.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class BaseFailsafe implements Failsafe {

    private final AccessorObjectFailsafe argument;
    private final AccessorObjectFailsafe state;
    private final ExceptionHandler exception;

    @Inject
    private BaseFailsafe(@Argument final AccessorObjectFailsafe argument,
                         @State final AccessorObjectFailsafe state,
                         final ExceptionHandler exception) {
        this.argument = argument;
        this.state = state;
        this.exception = exception;
    }

    @Override
    public AccessorObjectFailsafe checkArgument() {
        return this.argument;
    }

    @Override
    public AccessorObjectFailsafe checkState() {
        return this.state;
    }

    @Override
    public Logger getLogger(final Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    @Override
    public ExceptionHandler handleException() {
        return this.exception;
    }

    @Override
    public String toString() {
        return "BaseFailsafe{" +
                "argument=" + this.argument +
                ", state=" + this.state +
                ", exception=" + this.exception +
                "}";
    }
}
