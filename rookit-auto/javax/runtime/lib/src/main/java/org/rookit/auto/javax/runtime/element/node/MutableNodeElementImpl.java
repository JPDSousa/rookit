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
package org.rookit.auto.javax.runtime.element.node;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.node.dependency.EnclosedDependency;
import org.rookit.auto.javax.runtime.element.node.dependency.ElementDependencyVisitor;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.graph.DependencyWrapper;
import org.rookit.utils.graph.MultiDependencyWrapper;
import org.rookit.utils.optional.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

// TODO add missing logging
final class MutableNodeElementImpl implements MutableNodeElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableNodeElementImpl.class);

    private final MultiDependencyWrapper<Element> enclosedElements;
    private final DependencyWrapper<Element> enclosingElement;
    private final DependencyWrapper<TypeMirror> typeMirror;
    private final AnnotatedConstruct annotatedConstruct;
    private final ElementDependencyVisitor<Single<MutableNodeElement>, MutableNodeElement> visitor;

    MutableNodeElementImpl(
            final MultiDependencyWrapper<Element> enclosedElements,
            final DependencyWrapper<Element> enclosingElement,
            final DependencyWrapper<TypeMirror> typeMirror,
            final AnnotatedConstruct annotatedConstruct,
            final ElementDependencyVisitor<Single<MutableNodeElement>, MutableNodeElement> visitor) {
        this.enclosedElements = enclosedElements;
        this.enclosingElement = enclosingElement;
        this.typeMirror = typeMirror;
        this.annotatedConstruct = annotatedConstruct;
        this.visitor = visitor;
    }

    @Override
    public List<? extends Element> enclosedElements() {
        return this.enclosedElements.get();
    }

    @Override
    public Completable enclosedElements(final Collection<? extends Element> enclosedElements) {
        return this.enclosedElements.set(enclosedElements);
    }

    @Override
    public Completable addEnclosedElement(final Element enclosedElement) {
        return this.enclosedElements.add(enclosedElement);
    }

    @Override
    public Optional<Element> enclosingElement() {
        return this.enclosingElement.get();
    }

    @Override
    public TypeMirror typeMirror() {
        return this.typeMirror.fetch();
    }

    @Override
    public Completable enclosingElement(final Element enclosingElement) {
        return this.enclosingElement.set(enclosingElement);
    }

    @Override
    public Completable typeMirror(final TypeMirror typeMirror) {
        return this.typeMirror.set(typeMirror);
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
        return this.annotatedConstruct.getAnnotationsByType(annotationType);
    }

    @Override
    public Completable setDependencies(final Collection<Dependency<?>> dependencies) {
        // TODO move this logic to its own type
        if (dependencies.stream().anyMatch(EnclosedDependency.class::isInstance)) {
            logger.debug("Resetting enclosed elements, since there's at least one to be set");
            this.enclosedElements.reset();
        }
        return Observable.fromIterable(dependencies)
                .flatMapSingle(dependency -> dependency.accept(this.visitor, this))
                .ignoreElements();
    }

    @Override
    public Completable addDependency(final Dependency<?> dependency) {
        return dependency.accept(this.visitor, this).ignoreElement();
    }

    @Override
    public String toString() {
        return "MutableNodeElementImpl{" +
                "enclosedElements=" + this.enclosedElements +
                ", enclosingElement=" + this.enclosingElement +
                ", typeMirror=" + this.typeMirror +
                ", annotatedConstruct=" + this.annotatedConstruct +
                ", visitor=" + this.visitor +
                "}";
    }

}
