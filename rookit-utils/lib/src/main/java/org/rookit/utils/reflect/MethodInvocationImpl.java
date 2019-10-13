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

import org.rookit.failsafe.Failsafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

final class MethodInvocationImpl<C, R> implements MethodInvocation<C, R> {

    private final Failsafe failsafe;
    private final ExtendedMethod<C, R> method;
    private final Object[] args;

    MethodInvocationImpl(final Failsafe failsafe, final ExtendedMethod<C, R> method, final Object[] args) {
        this.failsafe = failsafe;
        this.method = method;
        this.args = args.clone();
    }

    @Override
    public ExtendedMethod<C, R> method() {
        return this.method;
    }

    @Override
    public Object[] arguments() {
        return this.args.clone();
    }

    @Override
    public R invoke(final C instance) {
        final Method method = method().originalMethod();
        final boolean isAccessible = method.isAccessible();
        try {
            method.setAccessible(true);
            //noinspection unchecked
            return (R) method.invoke(instance, this.args);
        } catch (final IllegalAccessException | IllegalArgumentException e) {
            return this.failsafe.handleException().runtimeException(e);
        } catch (final InvocationTargetException e) {
            return this.failsafe.handleException().invocationTargetException(e);
        } finally {
            method.setAccessible(isAccessible);
        }
    }

    @Override
    public String toString() {
        return "MethodInvocationImpl{" +
                "failsafe=" + this.failsafe +
                ", method=" + this.method +
                ", args=" + Arrays.toString(this.args) +
                "}";
    }
}
