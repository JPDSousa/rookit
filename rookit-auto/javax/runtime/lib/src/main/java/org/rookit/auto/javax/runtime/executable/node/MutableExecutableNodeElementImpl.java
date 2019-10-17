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
package org.rookit.auto.javax.runtime.executable.node;

import io.reactivex.Completable;
import org.rookit.auto.javax.runtime.element.executable.node.MutableExecutableNodeElement;
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
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

final class MutableExecutableNodeElementImpl implements MutableExecutableNodeElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MutableExecutableNodeElementImpl.class);

    private final MutableNodeElement node;
    private final MultiDependencyWrapper<TypeParameterElement> typeParameters;
    private final DependencyWrapper<TypeMirror> returnType;
    private final MultiDependencyWrapper<VariableElement> parameters;
    private final DependencyWrapper<TypeMirror> receiverType;
    private final MultiDependencyWrapper<TypeMirror> thrownTypes;
    private final NoType noType;

    MutableExecutableNodeElementImpl(
            final MutableNodeElement node,
            final MultiDependencyWrapper<TypeParameterElement> typeParameters,
            final DependencyWrapper<TypeMirror> returnType,
            final MultiDependencyWrapper<VariableElement> parameters,
            final DependencyWrapper<TypeMirror> receiverType,
            final MultiDependencyWrapper<TypeMirror> thrownTypes,
            final NoType noType) {
        this.node = node;
        this.typeParameters = typeParameters;
        this.returnType = returnType;
        this.parameters = parameters;
        this.receiverType = receiverType;
        this.thrownTypes = thrownTypes;
        this.noType = noType;
    }

    @Override
    public Completable typeParameters(final List<? extends TypeParameterElement> typeParameters) {
        logger.trace("Delegating to type parameters");
        return this.typeParameters.set(typeParameters);
    }

    @Override
    public Completable returnType(final TypeMirror returnType) {
        logger.trace("Delegating to return type");
        return this.returnType.set(returnType);
    }

    @Override
    public Completable parameters(final List<? extends VariableElement> parameters) {
        logger.trace("Delegating to parameters");
        return this.parameters.set(parameters);
    }

    @Override
    public Completable receiverType(final TypeMirror receiverType) {
        logger.trace("Delegating to receiver type");
        return this.receiverType.set(receiverType);
    }

    @Override
    public Completable thrownTypes(final List<? extends TypeMirror> thrownTypes) {
        logger.trace("Delegating to thrown types");
        return this.thrownTypes.set(thrownTypes);
    }

    @Override
    public List<? extends TypeParameterElement> typeParameters() {
        logger.trace("Delegating to type parameters");
        return this.typeParameters.get();
    }

    @Override
    public TypeMirror returnType() {
        logger.trace("Delegating to return type");
        return this.returnType.get().orElse(this.noType);
    }

    @Override
    public List<? extends VariableElement> parameters() {
        logger.trace("Delegating to parameters");
        return this.parameters.get();
    }

    @Override
    public TypeMirror receiverType() {
        logger.trace("Delegating to receiver type");
        return this.receiverType.get().orElse(this.noType);
    }

    @Override
    public List<? extends TypeMirror> thrownTypes() {
        logger.trace("Delegating to thrown types");
        return this.thrownTypes.get();
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
        return this.enclosingElement(enclosingElement);
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
    public Completable setDependencies(final Collection<Dependency<?>> dependencies) {
        logger.trace("Delegating to node element");
        return this.node.setDependencies(dependencies);
    }

    @Override
    public Completable addDependency(final Dependency<?> dependency) {
        logger.trace("Delegating to node element");
        return this.node.addDependency(dependency);
    }

    @Override
    public String toString() {
        return "MutableExecutableNodeElementImpl{" +
                "node=" + this.node +
                ", typeParameters=" + this.typeParameters +
                ", returnType=" + this.returnType +
                ", parameters=" + this.parameters +
                ", receiverType=" + this.receiverType +
                ", thrownTypes=" + this.thrownTypes +
                ", noType=" + this.noType +
                "}";
    }

}
