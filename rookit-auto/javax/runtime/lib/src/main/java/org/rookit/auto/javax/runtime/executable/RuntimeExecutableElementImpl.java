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
package org.rookit.auto.javax.runtime.executable;

import com.google.common.collect.ImmutableSet;
import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.element.executable.RuntimeExecutableElement;
import org.rookit.auto.javax.runtime.element.executable.node.MutableExecutableNodeElement;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.optional.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Set;

final class RuntimeExecutableElementImpl implements RuntimeExecutableElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeExecutableElementImpl.class);

    private final MutableExecutableNodeElement nodeElement;
    private final ElementKind kind;
    private final Set<Modifier> modifiers;
    private final Name simpleName;

    private final boolean isVarArgs;
    private final AnnotationValue defaultValue;

    RuntimeExecutableElementImpl(
            final MutableExecutableNodeElement nodeElement,
            final ElementKind kind,
            final Collection<Modifier> modifiers,
            final Name simpleName,
            final boolean isVarArgs,
            @Nullable final AnnotationValue defaultValue) {
        this.nodeElement = nodeElement;
        this.kind = kind;
        this.modifiers = ImmutableSet.copyOf(modifiers);
        this.simpleName = simpleName;
        this.isVarArgs = isVarArgs;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean isVarArgs() {
        return this.isVarArgs;
    }

    @Override
    public boolean isDefault() {
        logger.warn("Reflection does not permit default modifier inspection. Returning false");
        return false;
    }

    @Nullable
    @Override
    public AnnotationValue getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public ElementKind getKind() {
        return this.kind;
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
                .orElseThrow(() -> new IllegalStateException("Enclosing element for " + getSimpleName()
                                                                     + " has not been set"));
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return enclosedElements();
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
    public Completable typeParameters(final List<? extends TypeParameterElement> typeParameters) {
        logger.trace("Delegating to node element");
        return this.nodeElement.typeParameters(typeParameters);
    }

    @Override
    public Completable returnType(final TypeMirror returnType) {
        logger.trace("Delegating to node element");
        return this.nodeElement.returnType(returnType);
    }

    @Override
    public Completable parameters(final List<? extends VariableElement> parameters) {
        logger.trace("Delegating to node element");
        return this.nodeElement.parameters(parameters);
    }

    @Override
    public Completable receiverType(final TypeMirror receiverType) {
        logger.trace("Delegating to node element");
        return this.nodeElement.receiverType(receiverType);
    }

    @Override
    public Completable thrownTypes(final List<? extends TypeMirror> thrownTypes) {
        logger.trace("Delegating to node element");
        return this.nodeElement.thrownTypes(thrownTypes);
    }

    @Override
    public List<? extends TypeParameterElement> typeParameters() {
        logger.trace("Delegating to node element");
        return this.nodeElement.typeParameters();
    }

    @Override
    public TypeMirror returnType() {
        logger.trace("Delegating to node element");
        return this.nodeElement.returnType();
    }

    @Override
    public List<? extends VariableElement> parameters() {
        logger.trace("Delegating to node element");
        return this.nodeElement.parameters();
    }

    @Override
    public TypeMirror receiverType() {
        logger.trace("Delegating to node element");
        return this.nodeElement.receiverType();
    }

    @Override
    public List<? extends TypeMirror> thrownTypes() {
        logger.trace("Delegating to node element");
        return this.nodeElement.thrownTypes();
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
        return "RuntimeExecutableElementImpl{" +
                "nodeElement=" + this.nodeElement +
                ", kind=" + this.kind +
                ", modifiers=" + this.modifiers +
                ", simpleName=" + this.simpleName +
                ", isVarArgs=" + this.isVarArgs +
                ", defaultValue=" + this.defaultValue +
                "}";
    }

}
