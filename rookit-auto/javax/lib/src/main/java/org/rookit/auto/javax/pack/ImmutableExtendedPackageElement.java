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
package org.rookit.auto.javax.pack;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.string.join.JointString;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

class ImmutableExtendedPackageElement extends AbstractExtendedPackageElement {

    @SuppressWarnings("FieldNotUsedInToString")
    private final ExtendedPackageElementFactory packageFactory;
    private final PackageElement element;
    private final ExtendedElement extendedElement;
    private final JointString fullName;
    private final OptionalFactory optionalFactory;

    ImmutableExtendedPackageElement(final ExtendedPackageElementFactory packageFactory,
                                    final PackageElement element,
                                    final ExtendedElement extendedElement,
                                    final JointString fullName,
                                    final OptionalFactory optionalFactory) {
        this.packageFactory = packageFactory;
        this.element = element;
        this.extendedElement = extendedElement;
        this.fullName = fullName;
        this.optionalFactory = optionalFactory;
    }

    @Override
    ExtendedPackageElementFactory packageFactory() {
        return this.packageFactory;
    }

    @Override
    OptionalFactory optionalFactory() {
        return this.optionalFactory;
    }

    @Override
    public String name() {
        return getSimpleName().toString();
    }

    @Override
    public JointString fullName() {
        return this.fullName;
    }

    @Override
    public boolean isSubPackageOf(final ExtendedPackageElement packageElement) {
        return equals(packageElement.packageAtIndex(0));
    }

    @Override
    public ExtendedPackageElement root() {
        return this;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public ExtendedPackageElement packageAtIndex(final int index) {
        if (index == 0) {
            return this;
        }
        //noinspection AutoBoxing no way around it.
        final String errMsg = String.format("Invalid index %d. Package has length %d.", index, length());
        throw new IndexOutOfBoundsException(errMsg);
    }

    @Override
    public Name getQualifiedName() {
        return this.element.getQualifiedName();
    }

    @Override
    public Name getSimpleName() {
        return this.element.getSimpleName();
    }

    @Override
    public boolean isUnnamed() {
        return this.element.isUnnamed();
    }

    @Override
    public ExtendedTypeMirror asType() {
        return this.extendedElement.asType();
    }

    @Override
    public ElementKind getKind() {
        return this.element.getKind();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return this.element.getModifiers();
    }

    @Override
    public ExtendedElement getEnclosingElement() {
        return this;
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
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return this.element.accept(v, p);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.extendedElement.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return "ImmutableExtendedPackageElement{" +
                ", element=" + this.element +
                ", extendedElement=" + this.extendedElement +
                ", fullName=" + this.fullName +
                ", optionalFactory=" + this.optionalFactory +
                "} ";
    }
}
