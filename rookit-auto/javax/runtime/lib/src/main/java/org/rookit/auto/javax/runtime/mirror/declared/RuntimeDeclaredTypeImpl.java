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
package org.rookit.auto.javax.runtime.mirror.declared;

import com.google.common.base.Objects;
import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeEntity;
import org.rookit.auto.javax.runtime.mirror.declared.node.MutableNodeDeclaredType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class RuntimeDeclaredTypeImpl implements RuntimeDeclaredType {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeDeclaredTypeImpl.class);

    private final RuntimeTypeEntity entity;
    private final MutableNodeDeclaredType node;

    RuntimeDeclaredTypeImpl(
            final RuntimeTypeEntity entity,
            final MutableNodeDeclaredType node) {
        this.entity = entity;
        this.node = node;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to node declared type");
        return this.node.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to node declared type");
        return this.node.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to node declared type");
        return this.node.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable enclosingType(final TypeMirror enclosingType) {
        logger.trace("Delegating to node declared type");
        return this.node.enclosingType(enclosingType);
    }

    @Override
    public Completable typeArguments(final List<? extends TypeMirror> typeArguments) {
        logger.trace("Delegating to node declared type");
        return this.node.typeArguments(typeArguments);
    }

    @Override
    public Completable element(final Element element) {
        logger.trace("Delegating to node declared type");
        return this.node.element(element);
    }

    @Override
    public TypeMirror enclosingType() {
        logger.trace("Delegating to node declared type");
        return this.node.enclosingType();
    }

    @Override
    public List<? extends TypeMirror> typeArguments() {
        logger.trace("Delegating to node declared type");
        return this.node.typeArguments();
    }

    @Override
    public Element element() {
        logger.trace("Delegating to node declared type");
        return this.node.element();
    }

    @Override
    public boolean isSubTypeOf(final RuntimeDeclaredType other) {
        return this.entity.isSubTypeOf(other.entity());
    }

    @Override
    public boolean isAssignableFrom(final RuntimeDeclaredType other) {
        return this.entity.isAssignableFrom(other.entity());
    }

    @Override
    public RuntimeTypeEntity entity() {
        return this.entity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final RuntimeDeclaredTypeImpl other = (RuntimeDeclaredTypeImpl) o;
        return Objects.equal(this.entity, other.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.entity);
    }

    @Override
    public String toString() {
        return "RuntimeDeclaredTypeImpl{" +
                "entity=" + this.entity +
                ", node=" + this.node +
                "}";
    }

}
