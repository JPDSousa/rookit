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
package org.rookit.test.guice;

import com.google.inject.Inject;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.reflect.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class MockVisitor implements ClassVisitor<Object> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MockVisitor.class);

    private final Failsafe failsafe;

    @Inject
    private MockVisitor(final Failsafe failsafe) {
        this.failsafe = failsafe;
    }

    @Override
    public Object booleanClass() {
        return true;
    }

    @Override
    public Object byteClass() {
        return (byte) 100;
    }

    @Override
    public Object shortClass() {
        return (short) 324;
    }

    @Override
    public Object intClass() {
        return 217823;
    }

    @Override
    public Object floatClass() {
        return 0.32324f;
    }

    @Override
    public Object doubleClass() {
        return 0.234234;
    }

    @Override
    public Object longClass() {
        return 1238375L;
    }

    @Override
    public Object regularClass(final Class<?> clazz) {
        this.failsafe.checkArgument().isNotNull(logger, clazz, "class");
        if (clazz.equals(String.class)) {
            return "mockedString";
        }
        if (clazz.isEnum()) {
            return mockEnum(clazz);
        }

        return Mockito.mock(clazz, Answers.RETURNS_MOCKS);
    }

    private Object mockEnum(final Class<?> clazz) {
        final Object[] values = clazz.getEnumConstants();
        this.failsafe.checkArgument().array().isNotEmpty(logger, values, "enum values");

        return values[values.length / 2];
    }
}
