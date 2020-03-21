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
package org.rookit.auto.javax.executable;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElementFactory;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.variable.ExtendedVariableElementFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class ExtendedExecutableElementImpl implements ExtendedExecutableElement {

    private final ExtendedElement extendedElement;
    private final ExecutableElement delegate;
    private final ExtendedTypeMirror returnType;
    private final List<ExtendedTypeParameterElement> typeParameters;
    private final List<ExtendedVariableElement> parameters;
    private final List<ExtendedTypeMirror> thrownTypes;

    ExtendedExecutableElementImpl(
            final ExtendedElement extendedElement,
            final ExecutableElement delegate,
            final ExtendedTypeMirrorFactory typeMirrorFactory,
            final ExtendedTypeParameterElementFactory typeParameterFactory,
            final ExtendedVariableElementFactory parameterFactory) {
        this.extendedElement = extendedElement;
        this.delegate = delegate;
        this.returnType = typeMirrorFactory.extend(delegate.getReturnType());
        this.typeParameters = delegate.getTypeParameters().stream()
                .map(typeParameterFactory::extend)
                .collect(toImmutableList());
        this.parameters = delegate.getParameters().stream()
                .map(parameterFactory::extend)
                .collect(toImmutableList());
        this.thrownTypes = delegate.getThrownTypes().stream()
                .map(typeMirrorFactory::extend)
                .collect(toImmutableList());
    }

    @Override
    public List<? extends ExtendedTypeParameterElement> getTypeParameters() {
        return this.typeParameters;
    }

    @Override
    public ExtendedTypeMirror getReturnType() {
        return this.returnType;
    }

    @Override
    public List<? extends ExtendedVariableElement> getParameters() {
        return this.parameters;
    }

    @Override
    public TypeMirror getReceiverType() {
        return this.delegate.getReceiverType();
    }

    @Override
    public boolean isVarArgs() {
        return this.delegate.isVarArgs();
    }

    @Override
    public boolean isDefault() {
        return this.delegate.isDefault();
    }

    @Override
    public List<? extends ExtendedTypeMirror> getThrownTypes() {
        return this.thrownTypes;
    }

    @Override
    public AnnotationValue getDefaultValue() {
        return this.delegate.getDefaultValue();
    }

    @Override
    public ExtendedPackageElement packageInfo() {
        return this.extendedElement.packageInfo();
    }

    @Override
    public ExtendedTypeMirror asType() {
        return this.extendedElement.asType();
    }

    @Override
    public ElementKind getKind() {
        return this.extendedElement.getKind();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return this.extendedElement.getModifiers();
    }

    @Override
    public Name getSimpleName() {
        return this.extendedElement.getSimpleName();
    }

    @Override
    public ExtendedElement getEnclosingElement() {
        return this.extendedElement.getEnclosingElement();
    }

    @Override
    public List<? extends ExtendedElement> getEnclosedElements() {
        return this.extendedElement.getEnclosedElements();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return this.extendedElement.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return this.extendedElement.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.extendedElement.getAnnotationsByType(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return this.extendedElement.accept(v, p);
    }

}
