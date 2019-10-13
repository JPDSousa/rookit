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

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.String.format;

final class ExtendedClassImpl<T> implements ExtendedClass<T> {

    private final Class<T> clazz;
    private final ExtendedMethodFactory methodFactory;

    ExtendedClassImpl(final Class<T> clazz, final ExtendedMethodFactory methodFactory) {
        this.clazz = clazz;
        this.methodFactory = methodFactory;
    }

    @Override
    public <E> E accept(final ClassVisitor<E> visitor) {
        if (this.clazz.isPrimitive()) {
            return acceptPrimitive(visitor);
        }
        return visitor.regularClass(this.clazz);
    }

    private <E> E acceptPrimitive(final ClassVisitor<E> visitor) {
        if (this.clazz.equals(boolean.class)) {
            return visitor.booleanClass();
        }
        if (this.clazz.equals(byte.class)) {
            return visitor.byteClass();
        }
        if (this.clazz.equals(short.class)) {
            return visitor.shortClass();
        }
        if (this.clazz.equals(int.class)) {
            return visitor.intClass();
        }
        if (this.clazz.equals(float.class)) {
            return visitor.floatClass();
        }
        if (this.clazz.equals(double.class)) {
            return visitor.doubleClass();
        }
        if (this.clazz.equals(long.class)) {
            return visitor.longClass();
        }
        final String errorMsg = format("%s does not support this kind of primitive type '%s'",
                ClassVisitor.class.getName(), this.clazz.getName());
        throw new IllegalStateException(errorMsg);
    }

    @Override
    public Class<T> original() {
        return this.clazz;
    }


    @Override
    public Collection<ExtendedMethod<T, ?>> methods() {
        return Arrays.stream(original().getMethods())
                .map(method -> this.methodFactory.create(this, method))
                .collect(Collectors.toSet());
    }
}
