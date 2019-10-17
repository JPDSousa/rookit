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
package org.rookit.auto.javax.runtime.element.type.parameter;

import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.element.type.parameter.node.MutableTypeParameterNodeElement;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.optional.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

final class RuntimeTypeParameterElementImpl implements RuntimeTypeParameterElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeTypeParameterElementImpl.class);

    private final MutableTypeParameterNodeElement nodeElement;
    private final Name simpleName;

    RuntimeTypeParameterElementImpl(
            final MutableTypeParameterNodeElement nodeElement,
            final Name simpleName) {
        this.nodeElement = nodeElement;
        this.simpleName = simpleName;
    }

    @Override
    public Element getEnclosingElement() {
        return getGenericElement();
    }

    @Override
    public Element getGenericElement() {
        logger.trace("Delegating to node element (through enclosing element)");
        return this.nodeElement.enclosingElement()
                .orElseThrow(() -> new IllegalStateException("No generic element found for parameter "
                                                                     + getSimpleName()));
    }

    @Override
    public List<? extends TypeMirror> getBounds() {
        logger.trace("Delegating to node element");
        return this.nodeElement.bounds();
    }

    @Override
    public TypeMirror asType() {
        logger.trace("Delegating to node element");
        return this.nodeElement.typeMirror();
    }

    @Override
    public Name getSimpleName() {
        return this.simpleName;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating call to node element");
        return this.nodeElement.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable enclosedElements(final Collection<? extends Element> enclosedElements) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.enclosedElements(enclosedElements);
    }

    @Override
    public Completable addEnclosedElement(final Element enclosedElement) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.addEnclosedElement(enclosedElement);
    }

    @Override
    public Completable enclosingElement(final Element enclosingElement) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.enclosingElement(enclosingElement);
    }

    @Override
    public Completable typeMirror(final TypeMirror typeMirror) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.typeMirror(typeMirror);
    }

    @Override
    public List<? extends Element> enclosedElements() {
        logger.trace("Delegating call to node element");
        return this.nodeElement.enclosedElements();
    }

    @Override
    public Optional<Element> enclosingElement() {
        logger.trace("Delegating call to node element");
        return this.nodeElement.enclosingElement();
    }

    @Override
    public TypeMirror typeMirror() {
        logger.trace("Delegating call to node element");
        return this.nodeElement.typeMirror();
    }

    @Override
    public Completable setDependencies(final Collection<Dependency<?>> dependencies) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.setDependencies(dependencies);
    }

    @Override
    public Completable addDependency(final Dependency<?> dependency) {
        logger.trace("Delegating call to node element");
        return this.nodeElement.addDependency(dependency);
    }

    @Override
    public String toString() {
        return "RuntimeTypeParameterElementImpl{" +
                ", nodeElement=" + this.nodeElement +
                ", simpleName=" + this.simpleName +
                "}";
    }

}
