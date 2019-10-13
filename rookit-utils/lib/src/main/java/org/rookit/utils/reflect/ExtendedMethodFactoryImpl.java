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
package org.rookit.utils.reflect;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rookit.failsafe.Failsafe;

import java.lang.reflect.Method;

final class ExtendedMethodFactoryImpl implements ExtendedMethodFactory {

    private final Failsafe failsafe;
    private final Provider<ExtendedClassFactory> classFactory;

    @Inject
    private ExtendedMethodFactoryImpl(final Failsafe failsafe, final Provider<ExtendedClassFactory> classFactory) {
        this.failsafe = failsafe;
        this.classFactory = classFactory;
    }

    @Override
    public <C, R> ExtendedMethod<C, R> create(final ExtendedClass<C> clazz, final Method method) {
        return new ExtendedMethodImpl<>(this.failsafe, clazz, method, this.classFactory.get());
    }

    @Override
    public String toString() {
        return "ExtendedMethodFactoryImpl{" +
                "failsafe=" + this.failsafe +
                ", classFactory=" + this.classFactory +
                "}";
    }
}
