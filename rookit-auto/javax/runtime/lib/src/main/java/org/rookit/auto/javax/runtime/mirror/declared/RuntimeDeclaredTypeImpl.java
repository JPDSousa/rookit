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
import org.rookit.auto.javax.runtime.mirror.declared.node.MutableNodeDeclaredType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class RuntimeDeclaredTypeImpl implements RuntimeDeclaredType {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeDeclaredTypeImpl.class);

    private final MutableNodeDeclaredType node;

    RuntimeDeclaredTypeImpl(final MutableNodeDeclaredType node) {
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
    public String toString() {
        return "RuntimeDeclaredTypeImpl{" +
                "node=" + this.node +
                "}";
    }

}
