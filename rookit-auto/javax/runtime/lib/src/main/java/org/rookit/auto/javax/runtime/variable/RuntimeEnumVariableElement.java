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
package org.rookit.auto.javax.runtime.variable;

import com.google.common.collect.ImmutableSet;
import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.element.node.MutableNodeElement;
import org.rookit.auto.javax.runtime.element.variable.RuntimeVariableElement;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.optional.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


// TODO Improve logging
final class RuntimeEnumVariableElement implements RuntimeVariableElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeEnumVariableElement.class);

    //private final Element enumElement;
    private final Enum<?> enumValue;
    private final MutableNodeElement node;
    private final Set<Modifier> modifiers;
    private final Name simpleName;

    RuntimeEnumVariableElement(
            final Enum<?> enumValue,
            final MutableNodeElement node,
            final Collection<Modifier> modifiers,
            final Name simpleName) {
        this.enumValue = enumValue;
        this.node = node;
        this.modifiers = ImmutableSet.copyOf(modifiers);
        this.simpleName = simpleName;
    }

    @Override
    public Object getConstantValue() {
        // TODO not sure about this
        return this.enumValue;
    }

    @Override
    public TypeMirror asType() {
        logger.trace("Delegating to node element");
        return this.node.typeMirror();
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.ENUM_CONSTANT;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return this.modifiers;
    }

    @Override
    public Name getSimpleName() {
        return this.simpleName;
    }

    @Override
    public Element getEnclosingElement() {
        return enclosingElement()
                .orElseThrow(() -> new IllegalStateException("No enclosing element found"));
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return enclosedElements();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to node element");
        return this.node.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to node element");
        return this.node.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to node element");
        return this.node.getAnnotationsByType(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return v.visitVariable(this, p);
    }

    @Override
    public Completable enclosedElements(final Collection<? extends Element> enclosedElements) {
        logger.trace("Delegating to node element");
        return this.node.enclosedElements(enclosedElements);
    }

    @Override
    public Completable addEnclosedElement(final Element enclosedElement) {
        logger.trace("Delegating to node element");
        return this.node.addEnclosedElement(enclosedElement);
    }

    @Override
    public Completable enclosingElement(final Element enclosingElement) {
        logger.trace("Delegating to node element");
        return this.node.enclosingElement(enclosingElement);
    }

    @Override
    public Completable typeMirror(final TypeMirror typeMirror) {
        logger.trace("Delegating to node element");
        return this.node.typeMirror(typeMirror);
    }

    @Override
    public List<? extends Element> enclosedElements() {
        logger.trace("Delegating to node element");
        return this.node.enclosedElements();
    }

    @Override
    public Optional<Element> enclosingElement() {
        logger.trace("Delegating to node element");
        return this.node.enclosingElement();
    }

    @Override
    public TypeMirror typeMirror() {
        logger.trace("Delegating to node element");
        return this.node.typeMirror();
    }

    @Override
    public Completable setDependencies(final Collection<Dependency> dependencies) {
        logger.trace("Delegating to node element");
        return this.node.setDependencies(dependencies);
    }

    @Override
    public Completable addDependency(final Dependency dependency) {
        logger.trace("Delegating to node element");
        return this.node.addDependency(dependency);
    }


    @Override
    public String toString() {
        return "RuntimeEnumVariableElement{" +
                "enumValue=" + this.enumValue +
                ", node=" + this.node +
                ", modifiers=" + this.modifiers +
                ", simpleName=" + this.simpleName +
                "}";
    }

}
