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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

final class ExceptionHandlerImpl implements ExceptionHandler {

    private RuntimeException toRuntime(final Exception e) {
        return new RuntimeException(e);
    }

    @Override
    public <T> T inputOutputException(final IOException cause) {
        throw toRuntime(cause);
    }

    @Override
    public <T> T runtimeException(final String message) {
        throw new RuntimeException(message);
    }

    @Override
    public <T> T runtimeException(final String message, final Throwable cause) {
        throw new RuntimeException(message, cause);
    }

    @Override
    public <T> T runtimeException(final Throwable cause) {
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }
        throw new RuntimeException(cause);
    }

    @Override
    public <T> T invocationTargetException(final InvocationTargetException exception) {
        return runtimeException(exception.getCause());
    }

    @Override
    public <T> T unsupportedOperation(final String message, final Object... args) {
        throw new UnsupportedOperationException(String.format(message, args));
    }

    @Override
    public <T> T executionException(final ExecutionException exception) {
        return runtimeException(exception.getCause());
    }
}
