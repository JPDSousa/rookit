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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

final class ExtendedMethodImpl<C, R> implements ExtendedMethod<C, R> {

    private final Failsafe failsafe;
    private final ExtendedClass<C> clazz;
    private final Method method;
    private final ExtendedClassFactory classFactory;

    ExtendedMethodImpl(final Failsafe failsafe,
                       final ExtendedClass<C> clazz,
                       final Method method,
                       final ExtendedClassFactory classFactory) {
        this.failsafe = failsafe;
        this.clazz = clazz;
        this.method = method;
        this.classFactory = classFactory;
    }

    @Override
    public <E> E accept(final ClassVisitor<E> visitor) {
        return this.clazz.accept(visitor);
    }

    @Override
    public Class<C> original() {
        return this.clazz.original();
    }

    @Override
    public String className() {
        return this.clazz.className();
    }

    @Override
    public Collection<ExtendedMethod<C, ?>> methods() {
        return this.clazz.methods();
    }

    @Override
    public boolean isAccessible() {
        return this.method.isAccessible();
    }

    @Override
    public String methodName() {
        return this.method.getName();
    }

    @Override
    public List<ExtendedClass<?>> parameters() {
        return Arrays.stream(this.method.getParameterTypes())
                .map(this.classFactory::create)
                .collect(Collectors.toList());
    }

    @Override
    public ExtendedClass<R> returnType() {
        //noinspection unchecked
        return this.classFactory.create((Class<R>) this.method.getReturnType());
    }

    @Override
    public MethodInvocation<C, R> invocation(final Object[] arguments) {
        return new MethodInvocationImpl<>(this.failsafe, this, arguments);
    }

    @Override
    public Method originalMethod() {
        return this.method;
    }

    @Override
    public String toString() {
        return "ExtendedMethodImpl{" +
                "failsafe=" + this.failsafe +
                ", clazz=" + this.clazz +
                ", method=" + this.method +
                ", classFactory=" + this.classFactory +
                "}";
    }
}
