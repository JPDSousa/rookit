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

import javax.lang.model.element.ElementKind;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

final class FieldEntityImpl implements RuntimeFieldEntity {

    private final Field field;
    private final RuntimeClassEntity declaringClass;

    FieldEntityImpl(final Field field,
                    final RuntimeClassEntity declaringClass) {
        this.field = field;
        this.declaringClass = declaringClass;
    }

    @Override
    public Field field() {
        return this.field;
    }

    @Override
    public RuntimeClassEntity declaringClass() {
        return this.declaringClass;
    }

    @Override
    public int modifiers() {
        return this.field.getModifiers();
    }

    @Override
    public String name() {
        return this.field.getName();
    }

    @Override
    public ElementKind kind() {
        return ElementKind.FIELD;
    }

    @Override
    public <R, P> R accept(final RuntimeEntityVisitor<R, P> visitor, final P parameter) {
        return visitor.visitField(this, parameter);
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        return this.field.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.field.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.field.getDeclaredAnnotations();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final FieldEntityImpl other = (FieldEntityImpl) o;
        return Objects.equal(this.field, other.field);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.field);
    }

    @Override
    public String toString() {
        return "FieldEntityImpl{" +
                "field=" + this.field +
                ", declaringClass=" + this.declaringClass +
                "}";
    }

}
