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
package org.rookit.auto.javax.runtime.mirror.variable;

import com.google.common.base.Objects;
import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;
import org.rookit.auto.javax.runtime.mirror.variable.node.MutableNodeTypeVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class TypeVariableImpl implements RuntimeTypeVariable {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeVariableImpl.class);

    private final RuntimeTypeVariableEntity entity;
    private final MutableNodeTypeVariable node;

    TypeVariableImpl(
            final RuntimeTypeVariableEntity entity,
            final MutableNodeTypeVariable node) {
        this.entity = entity;
        this.node = node;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.TYPEVAR;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.debug("Delegating to node");
        return this.node.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.debug("Delegating to node");
        return this.node.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.debug("Delegating to node");
        return this.node.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable element(final Element element) {
        logger.debug("Delegating to node");
        return this.node.element(element);
    }

    @Override
    public Completable upperBound(final TypeMirror typeMirror) {
        logger.debug("Delegating to node");
        return this.node.upperBound(typeMirror);
    }

    @Override
    public Completable lowerBound(final TypeMirror typeMirror) {
        logger.debug("Delegating to node");
        return this.node.lowerBound(typeMirror);
    }

    @Override
    public Element element() {
        logger.debug("Delegating to node");
        return this.node.element();
    }

    @Override
    public TypeMirror upperBound() {
        logger.debug("Delegating to node");
        return this.node.upperBound();
    }

    @Override
    public TypeMirror lowerBound() {
        logger.debug("Delegating to node");
        return this.node.lowerBound();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final TypeVariableImpl other = (TypeVariableImpl) o;
        return Objects.equal(this.entity, other.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.entity);
    }

    @Override
    public String toString() {
        return "TypeVariableImpl{" +
                "node=" + this.node +
                "}";
    }

}
