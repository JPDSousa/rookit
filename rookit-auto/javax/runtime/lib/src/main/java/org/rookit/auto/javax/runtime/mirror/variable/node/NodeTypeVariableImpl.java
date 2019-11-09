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
package org.rookit.auto.javax.runtime.mirror.variable.node;

import io.reactivex.Completable;
import org.rookit.utils.graph.DependencyWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class NodeTypeVariableImpl implements MutableNodeTypeVariable {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(NodeTypeVariableImpl.class);

    private final AnnotatedConstruct construct;
    private final DependencyWrapper<Element> element;

    // TODO replace me by a "range" wrapper
    private final DependencyWrapper<TypeMirror> upperBound;
    private final DependencyWrapper<TypeMirror> lowerBound;

    NodeTypeVariableImpl(
            final AnnotatedConstruct construct,
            final DependencyWrapper<Element> element,
            final DependencyWrapper<TypeMirror> upperBound,
            final DependencyWrapper<TypeMirror> lowerBound) {
        this.construct = construct;
        this.element = element;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    @Override
    public Element element() {
        return this.element.fetch();
    }

    @Override
    public TypeMirror upperBound() {
        return this.upperBound.fetch();
    }

    @Override
    public TypeMirror lowerBound() {
        return this.lowerBound.fetch();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to annotated construct");
        return this.construct.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to annotated construct");
        return this.construct.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to annotated construct");
        return this.construct.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable element(final Element element) {
        return this.element.set(element);
    }

    @Override
    public Completable upperBound(final TypeMirror typeMirror) {
        return this.upperBound.set(typeMirror);
    }

    @Override
    public Completable lowerBound(final TypeMirror typeMirror) {
        return this.lowerBound.set(typeMirror);
    }

    @Override
    public String toString() {
        return "NodeTypeVariableImpl{" +
                "construct=" + this.construct +
                ", element=" + this.element +
                ", upperBound=" + this.upperBound +
                ", lowerBound=" + this.lowerBound +
                "}";
    }

}
