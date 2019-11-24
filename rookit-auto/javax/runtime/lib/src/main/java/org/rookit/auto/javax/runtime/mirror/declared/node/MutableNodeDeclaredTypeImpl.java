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
package org.rookit.auto.javax.runtime.mirror.declared.node;

import io.reactivex.Completable;
import org.rookit.auto.javax.mirror.declared.node.MutableNodeDeclaredType;
import org.rookit.utils.graph.DependencyWrapper;
import org.rookit.utils.graph.MultiDependencyWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class MutableNodeDeclaredTypeImpl implements MutableNodeDeclaredType {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableNodeDeclaredTypeImpl.class);

    private final AnnotatedConstruct annotatedConstruct;
    private final DependencyWrapper<TypeMirror> enclosingType;
    private final MultiDependencyWrapper<TypeMirror> typeArguments;
    private final DependencyWrapper<Element> element;
    private final MultiDependencyWrapper<TypeMirror> directSubTypes;

    MutableNodeDeclaredTypeImpl(
            final AnnotatedConstruct annotatedConstruct,
            final DependencyWrapper<TypeMirror> enclosingType,
            final MultiDependencyWrapper<TypeMirror> typeArguments,
            final DependencyWrapper<Element> element,
            final MultiDependencyWrapper<TypeMirror> directSubTypes) {
        this.annotatedConstruct = annotatedConstruct;
        this.enclosingType = enclosingType;
        this.typeArguments = typeArguments;
        this.element = element;
        this.directSubTypes = directSubTypes;
    }

    @Override
    public Completable enclosingType(final TypeMirror enclosingType) {
        return this.enclosingType.set(enclosingType);
    }

    @Override
    public Completable typeArguments(final List<? extends TypeMirror> typeArguments) {
        return this.typeArguments.set(typeArguments);
    }

    @Override
    public Completable element(final Element element) {
        return this.element.set(element);
    }

    @Override
    public Completable directSubTypes(final List<? extends TypeMirror> directSubTypes) {
        return this.directSubTypes.set(directSubTypes);
    }

    @Override
    public TypeMirror enclosingType() {
        return this.enclosingType.fetch();
    }

    @Override
    public List<? extends TypeMirror> typeArguments() {
        return this.typeArguments.get();
    }

    @Override
    public Element element() {
        return this.element.fetch();
    }

    @Override
    public List<? extends TypeMirror> directSubTypes() {
        return this.directSubTypes.get();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to annotatedConstruct");
        return this.annotatedConstruct.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to annotatedConstruct");
        return this.annotatedConstruct.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to annotatedConstruct");
        return this.annotatedConstruct.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return "MutableNodeDeclaredTypeImpl{" +
                "annotatedConstruct=" + this.annotatedConstruct +
                ", enclosingType=" + this.enclosingType +
                ", typeArguments=" + this.typeArguments +
                ", element=" + this.element +
                ", directSubTypes=" + this.directSubTypes +
                "}";
    }

}
