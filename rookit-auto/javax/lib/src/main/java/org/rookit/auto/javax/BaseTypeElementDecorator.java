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
package org.rookit.auto.javax;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.ExtendedTypeMirror;
import org.rookit.auto.javax.type.ExtendedTypeMirrorFactory;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Objects.equal;

final class BaseTypeElementDecorator implements ExtendedTypeElement {

    private final ExtendedElement extendedElement;
    private final TypeElement delegate;
    private final ExtendedPackageElement extendedPackageElement;
    private final OptionalFactory optionalFactory;
    private final ExtendedTypeMirrorFactory factory;
    private final ExtendedElementFactory elementFactory;

    BaseTypeElementDecorator(final ExtendedElement extendedElement,
                             final ExtendedPackageElement packageElement,
                             final TypeElement delegate,
                             final OptionalFactory optionalFactory,
                             final ExtendedTypeMirrorFactory factory,
                             final ExtendedElementFactory elementFactory) {
        this.extendedElement = extendedElement;
        this.extendedPackageElement = packageElement;
        this.delegate = delegate;
        this.optionalFactory = optionalFactory;
        this.factory = factory;
        this.elementFactory = elementFactory;
    }

    @Override
    public ExtendedPackageElement packageInfo() {
        return this.extendedPackageElement;
    }

    @Override
    public List<? extends ExtendedTypeMirror> getInterfaces() {
        return StreamEx.of(this.delegate.getInterfaces())
                .map(this.factory::create)
                .toImmutableList();
    }

    @Override
    public List<? extends ExtendedElement> getEnclosedElements() {
        return this.extendedElement.getEnclosedElements();
    }

    @Override
    public NestingKind getNestingKind() {
        return this.delegate.getNestingKind();
    }

    @Override
    public Name getQualifiedName() {
        return this.delegate.getQualifiedName();
    }

    @Override
    public Name getSimpleName() {
        return this.extendedElement.getSimpleName();
    }

    @Override
    public List<? extends ExtendedTypeParameterElement> getTypeParameters() {
        return this.delegate.getTypeParameters().stream()
                .map(this.elementFactory::extendParameter)
                .collect(Collectors.toList());
    }

    @Override
    public ExtendedElement getEnclosingElement() {
        return this.extendedElement.getEnclosingElement();
    }

    @Override
    public Optional<ExtendedExecutableElement> getMethod(final String name) {
        final java.util.Optional<ExtendedExecutableElement> result = StreamEx.of(this.delegate.getEnclosedElements())
                .select(ExecutableElement.class)
                .findFirst(method -> method.getSimpleName().contentEquals(name))
                .map(this.elementFactory::extendExecutable);
        return this.optionalFactory.fromJavaOptional(result);
    }

    @Override
    public ExtendedTypeMirror getSuperclass() {
        return this.factory.create(this.delegate.getSuperclass());
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
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return this.extendedElement.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return this.extendedElement.getAnnotation(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return this.extendedElement.accept(v, p);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.extendedElement.getAnnotationsByType(annotationType);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if ((o == null) || (getClass() != o.getClass())) return false;
        final BaseTypeElementDecorator otherElement = (BaseTypeElementDecorator) o;
        return equal(this.delegate, otherElement.delegate)
                && equal(this.extendedElement, otherElement.extendedElement);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.delegate, this.extendedElement);
    }

    @Override
    public String toString() {
        return "BaseTypeElementDecorator{" +
                "extendedElement=" + this.extendedElement +
                ", delegate=" + this.delegate +
                ", extendedPackageElement=" + this.extendedPackageElement +
                ", optionalFactory=" + this.optionalFactory +
                ", factory=" + this.factory +
                ", elementFactory=" + this.elementFactory +
                "}";
    }
}
