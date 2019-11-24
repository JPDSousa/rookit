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
package org.rookit.auto.javax.runtime.element.variable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.apache.commons.lang3.NotImplementedException;
import org.rookit.auto.javax.runtime.element.node.MutableNodeElement;
import org.rookit.auto.javax.runtime.mirror.declared.RuntimeDeclaredType;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.optional.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Set;

final class RuntimeFieldVariableElement implements RuntimeVariableElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeFieldVariableElement.class);

    private final MutableNodeElement node;
    private final Set<Modifier> modifiers;
    private final Name fieldName;

    RuntimeFieldVariableElement(
            final MutableNodeElement node,
            final Collection<Modifier> modifiers,
            final Name fieldName) {
        this.node = node;
        this.modifiers = ImmutableSet.copyOf(modifiers);
        this.fieldName = fieldName;
    }

    @Nullable
    @Override
    public Object getConstantValue() {
        logger.warn("It is not possible to fetch a default initialization value for a field through reflection");
        return null;
    }

    @Override
    public TypeMirror asType() {
        logger.trace("Delegating to node element");
        return this.node.typeMirror();
    }

    @Override
    public Single<TypeMirror> asMemberOf(final RuntimeDeclaredType enclosing) {
        // TODO guess what :)
        throw new NotImplementedException("Needs to be implemented");
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.FIELD;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return this.modifiers;
    }

    @Override
    public Name getSimpleName() {
        return this.fieldName;
    }

    @Override
    public Element getEnclosingElement() {
        logger.trace("Delegating to node element");
        return this.node.enclosingElement()
                .orElseThrow(() -> new IllegalStateException("No enclosing element found for field "
                                                                     + getSimpleName()));
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        // fields have no enclosed elements
        return ImmutableList.of();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating call to node");
        return this.node.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating call to node");
        return this.node.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating call to node");
        return this.node.getAnnotationsByType(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        logger.trace("Visiting variable fieldTypeElement");
        return v.visitVariable(this, p);
    }

    @Override
    public Completable enclosedElements(final Collection<? extends Element> enclosedElements) {
        logger.trace("Delegating call to node");
        return this.node.enclosedElements(enclosedElements);
    }

    @Override
    public Completable addEnclosedElement(final Element enclosedElement) {
        logger.trace("Delegating call to node");
        return this.node.addEnclosedElement(enclosedElement);
    }

    @Override
    public Completable enclosingElement(final Element enclosingElement) {
        logger.trace("Delegating call to node");
        return this.node.enclosingElement(enclosingElement);
    }

    @Override
    public Completable typeMirror(final TypeMirror typeMirror) {
        logger.trace("Delegating call to node");
        return this.node.typeMirror(typeMirror);
    }

    @Override
    public List<? extends Element> enclosedElements() {
        logger.trace("Delegating call to node");
        return this.node.enclosedElements();
    }

    @Override
    public Optional<Element> enclosingElement() {
        logger.trace("Delegating call to node");
        return this.node.enclosingElement();
    }

    @Override
    public TypeMirror typeMirror() {
        logger.trace("Delegating call to node");
        return this.node.typeMirror();
    }

    @Override
    public Completable setDependencies(final Collection<Dependency<?>> dependencies) {
        logger.trace("Delegating call to node");
        return this.node.setDependencies(dependencies);
    }

    @Override
    public Completable addDependency(final Dependency<?> dependency) {
        logger.trace("Delegating call to node");
        return this.node.addDependency(dependency);
    }

    @Override
    public String toString() {
        return "RuntimeFieldVariableElement{" +
                "node=" + this.node +
                ", modifiers=" + this.modifiers +
                ", fieldName=" + this.fieldName +
                "}";
    }

}
