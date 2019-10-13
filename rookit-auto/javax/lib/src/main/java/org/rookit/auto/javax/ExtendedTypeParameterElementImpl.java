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

import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeMirror;
import org.rookit.auto.javax.type.ExtendedTypeMirrorFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeParameterElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

final class ExtendedTypeParameterElementImpl implements ExtendedTypeParameterElement {

    private final ExtendedElement extendedElement;
    private final TypeParameterElement typeParameter;
    private final ExtendedElementFactory elementFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;

    ExtendedTypeParameterElementImpl(final ExtendedElement extendedElement,
                                     final TypeParameterElement typeParameter,
                                     final ExtendedElementFactory elementFactory,
                                     final ExtendedTypeMirrorFactory mirrorFactory) {
        this.extendedElement = extendedElement;
        this.typeParameter = typeParameter;
        this.elementFactory = elementFactory;
        this.mirrorFactory = mirrorFactory;
    }

    @Override
    public ExtendedElement getGenericElement() {
        return this.elementFactory.extendAsSubType(this.typeParameter.getGenericElement());
    }

    @Override
    public List<? extends ExtendedTypeMirror> getBounds() {
        return this.typeParameter.getBounds().stream()
                .map(this.mirrorFactory::create)
                .collect(Collectors.toList());
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

    @Override
    public String toString() {
        return "ExtendedTypeParameterElementImpl{" +
                "extendedElement=" + this.extendedElement +
                ", typeParameter=" + this.typeParameter +
                ", elementFactory=" + this.elementFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                "}";
    }
}
