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
package org.rookit.auto.javax.runtime.entity;

import java.lang.annotation.Annotation;

final class EnumEntityImpl implements RuntimeEnumEntity {

    private final Enum<?> enumeration;
    private final RuntimeTypeEntity declaringClass;

    EnumEntityImpl(final Enum<?> enumeration,
                   final RuntimeTypeEntity declaringClass) {
        this.enumeration = enumeration;
        this.declaringClass = declaringClass;
    }

    @Override
    public Enum<?> enumeration() {
        return this.enumeration;
    }

    @Override
    public RuntimeTypeEntity declaringClass() {
        return this.declaringClass;
    }

    @Override
    public int modifiers() {
        return this.declaringClass.modifiers();
    }

    @Override
    public String name() {
        return this.enumeration.name();
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return this.declaringClass.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.declaringClass.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.declaringClass.getDeclaredAnnotations();
    }

}
