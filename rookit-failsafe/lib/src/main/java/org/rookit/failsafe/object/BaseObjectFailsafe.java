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

import java.util.Objects;
import java.util.function.Function;

final class BaseObjectFailsafe implements ObjectFailsafe {

    private final Function<String, RuntimeException> exceptionSupplier;

    BaseObjectFailsafe(final Function<String, RuntimeException> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }

    @SuppressWarnings("BooleanParameter") // intended
    @Override
    public <T> T is(final Logger logger, final boolean condition, final String message, final Object... args) {
        if (!condition) {
            final String errorMessage = String.format(message, args);
            logger.error(errorMessage);
            throw this.exceptionSupplier.apply(errorMessage);
        }
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public <T> T isEquals(final Logger logger, final Object expected, final Object actual, final String name) {
        isNotNull(logger, actual, "actual object");
        isNotNull(logger, expected, "expected object");
        return is(logger, Objects.equals(expected, actual), "%s of type %s is not the same class as %s",
                actual, name, expected);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public <T> T isNotEquals(final Logger logger,
                             final Object actual,
                             final Object unexpected,
                             final String objectName) {
        isNotNull(logger, actual, "actual object");
        isNotNull(logger, unexpected, "expected object");
        return is(logger, !Objects.equals(actual, unexpected), "The %s cannot be equal to %s", objectName, unexpected);
    }

    @Override
    public <T> T isNotNull(final Logger logger, final Object object, final String name) {
        return is(logger, Objects.nonNull(object), "The %s must not be null", name);
    }

    @Override
    public <T> T isNull(final Logger logger, final Object object, final String name) {
        return is(logger, Objects.isNull(object), "The %s must be null", name);
    }

    @Override
    public String toString() {
        return "BaseObjectFailsafe{" +
                "exceptionSupplier=" + this.exceptionSupplier +
                "}";
    }
}
