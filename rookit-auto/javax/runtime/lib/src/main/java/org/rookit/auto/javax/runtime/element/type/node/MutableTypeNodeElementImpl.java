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
package org.rookit.auto.javax.runtime.element.type.node;

import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.element.node.MutableNodeElement;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.graph.DependencyWrapper;
import org.rookit.utils.graph.MultiDependencyWrapper;
import org.rookit.utils.optional.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

final class MutableTypeNodeElementImpl implements MutableTypeNodeElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableTypeNodeElementImpl.class);

    private final MutableNodeElement nodeElement;
    private final DependencyWrapper<TypeMirror> superclass;
    private final MultiDependencyWrapper<TypeMirror> interfaces;
    private final MultiDependencyWrapper<TypeParameterElement> typeParameters;

    MutableTypeNodeElementImpl(
            final MutableNodeElement nodeElement,
            final DependencyWrapper<TypeMirror> superclass,
            final MultiDependencyWrapper<TypeMirror> interfaces,
            final MultiDependencyWrapper<TypeParameterElement> typeParameters) {
        this.nodeElement = nodeElement;
        this.superclass = superclass;
        this.interfaces = interfaces;
        this.typeParameters = typeParameters;
    }

    @Override
    public Completable superclass(final TypeMirror typeMirror) {
        logger.trace("Delegating to '{}'", this.superclass);
        return this.superclass.set(typeMirror);
    }

    @Override
    public Completable interfaces(final List<? extends TypeMirror> interfaces) {
        logger.trace("Delegating to '{}'", this.interfaces);
        return this.interfaces.set(interfaces);
    }

    @Override
    public Completable enclosedElements(final Collection<? extends Element> enclosedElements) {
        logger.trace("Delegating to node element");
        return this.nodeElement.enclosedElements(enclosedElements);
    }

    @Override
    public Completable addEnclosedElement(final Element enclosedElement) {
        logger.trace("Delegating to node element");
        return this.nodeElement.addEnclosedElement(enclosedElement);
    }

    @Override
    public Completable enclosingElement(final Element enclosingElement) {
        logger.trace("Delegating to node element");
        return this.nodeElement.enclosingElement(enclosingElement);
    }

    @Override
    public Completable typeMirror(final TypeMirror typeMirror) {
        logger.trace("Delegating to node element");
        return this.nodeElement.typeMirror(typeMirror);
    }

    @Override
    public Completable typeParameters(final List<? extends TypeParameterElement> typeParameters) {
        logger.trace("Delegating to '{}'", this.typeParameters);
        return this.typeParameters.set(typeParameters);
    }

    @Override
    public TypeMirror superclass() {
        logger.trace("Delegating to '{}'", this.superclass);
        return this.superclass.fetch();
    }

    @Override
    public List<? extends TypeMirror> interfaces() {
        logger.trace("Delegating to '{}'", this.interfaces);
        return this.interfaces.get();
    }

    @Override
    public List<? extends Element> enclosedElements() {
        logger.trace("Delegating to node element");
        return this.nodeElement.enclosedElements();
    }

    @Override
    public Optional<Element> enclosingElement() {
        logger.trace("Delegating to node element");
        return this.nodeElement.enclosingElement();
    }

    @Override
    public TypeMirror typeMirror() {
        logger.trace("Delegating to node element");
        return this.nodeElement.typeMirror();
    }

    @Override
    public List<? extends TypeParameterElement> typeParameters() {
        logger.trace("Delegating to '{}'", this.typeParameters);
        return this.typeParameters.get();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to node element");
        return this.nodeElement.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to node element");
        return this.nodeElement.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to node element");
        return this.nodeElement.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable setDependencies(final Collection<Dependency<?>> dependencies) {
        logger.trace("Delegating to node element");
        return this.nodeElement.setDependencies(dependencies);
    }

    @Override
    public Completable addDependency(final Dependency<?> dependency) {
        logger.trace("Delegating to node element");
        return this.nodeElement.addDependency(dependency);
    }

    @Override
    public String toString() {
        return "MutableTypeNodeElementImpl{" +
                "nodeElement=" + this.nodeElement +
                ", superclass=" + this.superclass +
                ", interfaces=" + this.interfaces +
                ", typeParameters=" + this.typeParameters +
                "}";
    }

}
