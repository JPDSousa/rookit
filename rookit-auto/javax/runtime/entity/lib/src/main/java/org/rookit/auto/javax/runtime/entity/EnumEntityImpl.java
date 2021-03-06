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

import com.google.common.base.Objects;

import java.lang.annotation.Annotation;

final class EnumEntityImpl implements RuntimeEnumEntity {

    private final Enum<?> enumeration;
    private final RuntimeClassEntity declaringClass;

    EnumEntityImpl(final Enum<?> enumeration,
                   final RuntimeClassEntity declaringClass) {
        this.enumeration = enumeration;
        this.declaringClass = declaringClass;
    }

    @Override
    public Enum<?> enumeration() {
        return this.enumeration;
    }

    @Override
    public RuntimeClassEntity declaringClass() {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final EnumEntityImpl other = (EnumEntityImpl) o;
        return this.enumeration == other.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.enumeration);
    }

    @Override
    public String toString() {
        return "EnumEntityImpl{" +
                "enumeration=" + this.enumeration +
                ", declaringClass=" + this.declaringClass +
                "}";
    }

}
