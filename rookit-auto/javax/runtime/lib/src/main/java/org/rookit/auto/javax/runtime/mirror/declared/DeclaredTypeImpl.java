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

import io.reactivex.Completable;
import org.rookit.auto.javax.mirror.declared.node.MutableNodeDeclaredType;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class DeclaredTypeImpl implements RuntimeDeclaredType {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DeclaredTypeImpl.class);

    private static final String DELEGATE = "Delegating to erasured instance";

    private final RuntimeDeclaredType erasure;
    private final MutableNodeDeclaredType node;

    DeclaredTypeImpl(
            final RuntimeDeclaredType erasure,
            final MutableNodeDeclaredType node) {
        this.erasure = erasure;
        this.node = node;
    }

    @Override
    public boolean isSubTypeOf(final RuntimeDeclaredType other) {
        logger.trace(DELEGATE);
        return this.erasure.isSubTypeOf(other);
    }

    @Override
    public boolean isAssignableFrom(final RuntimeDeclaredType other) {
        logger.trace(DELEGATE);
        return this.erasure.isAssignableFrom(other);
    }

    @Override
    public RuntimeTypeEntity entity() {
        logger.trace(DELEGATE);
        return this.erasure.entity();
    }

    @Override
    public RuntimeDeclaredType erasure() {
        return this.erasure;
    }

    @Override
    public Completable enclosingType(final TypeMirror enclosingType) {
        logger.trace(DELEGATE);
        return this.erasure.enclosingType(enclosingType);
    }

    @Override
    public Completable typeArguments(final List<? extends TypeMirror> typeArguments) {
        logger.trace("Delegating to node");
        return this.node.typeArguments(typeArguments);
    }

    @Override
    public Completable element(final Element element) {
        logger.trace(DELEGATE);
        return this.erasure.element(element);
    }

    @Override
    public Completable directSubTypes(final List<? extends TypeMirror> directSubTypes) {
        logger.trace(DELEGATE);
        return this.erasure.directSubTypes(directSubTypes);
    }

    @Override
    public TypeMirror enclosingType() {
        logger.trace(DELEGATE);
        return this.erasure.enclosingType();
    }

    @Override
    public List<? extends TypeMirror> typeArguments() {
        logger.trace("Delegating to node");
        return this.node.typeArguments();
    }

    @Override
    public Element element() {
        logger.trace(DELEGATE);
        return this.erasure.element();
    }

    @Override
    public List<? extends TypeMirror> directSubTypes() {
        logger.trace(DELEGATE);
        return this.erasure.directSubTypes();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace(DELEGATE);
        return this.erasure.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace(DELEGATE);
        return this.erasure.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace(DELEGATE);
        return this.erasure.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return "DeclaredTypeImpl{" +
                "erasure=" + this.erasure +
                ", node=" + this.node +
                "}";
    }

}
