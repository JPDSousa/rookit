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

import static java.lang.String.format;

final class ImmutableParentExtendedPackageElement extends AbstractExtendedPackageElement {

    @SuppressWarnings("FieldNotUsedInToString")
    private final ExtendedPackageElementFactory factory;
    private final ExtendedPackageElement parent;
    private final ExtendedElement extendedElement;
    private final PackageElement delegate;
    private final int length;
    private final String name;
    @SuppressWarnings("FieldNotUsedInToString")
    private final JointString fullName;
    private final OptionalFactory optionalFactory;

    ImmutableParentExtendedPackageElement(final ExtendedPackageElementFactory factory,
                                          final ExtendedElement extendedElement,
                                          final String name,
                                          final ExtendedPackageElement parent,
                                          final PackageElement delegate,
                                          final JointString fullName,
                                          final OptionalFactory optionalFactory) {
        this.factory = factory;
        this.extendedElement = extendedElement;
        this.name = name;
        this.delegate = delegate;
        this.parent = parent;
        this.length = parent.length() + 1;
        this.fullName = fullName;
//        this.fullName = ImmutableList.<String>builder()
//                .addAll(this.parent.fullNameAsList())
//                .add(name)
//                .build();
        this.optionalFactory = optionalFactory;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public JointString fullName() {
        return this.fullName;
    }

    @Override
    public boolean isSubPackageOf(final ExtendedPackageElement packageElement) {
        if (equals(packageElement)) {
            return false;
        }

        final boolean isParentSubPackage = this.parent.isSubPackageOf(packageElement);
        if (!isParentSubPackage) {
            return false;
        }

        // at this point we know that the packages are not equal, so it is safe to compare only the specific
        // package type.
        return packageElement.packageAtIndex(length() - 1).name().equals(this.name);
    }

    @Override
    public ExtendedPackageElement root() {
        return this.parent.root();
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public ExtendedPackageElement packageAtIndex(final int index) {
        // TODO use failsafe validations here.
        if (index < 0) {
            //noinspection AutoBoxing no way around.
            throw new IndexOutOfBoundsException(format("Index is negative: %s", index));
        }
        final int length = length();
        if (index >= length) {
            //noinspection AutoBoxing no way around.
            throw new IndexOutOfBoundsException(format("Package has %s and you're trying to access index %d",
                    length, index));
        }
        if (index == (length - 1)) {
            return this.factory.fromFQDN(this.name);
        }
        return this.parent.packageAtIndex(index);
    }

    @Override
    ExtendedPackageElementFactory packageFactory() {
        return this.factory;
    }

    @Override
    OptionalFactory optionalFactory() {
        return this.optionalFactory;
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
    public boolean isUnnamed() {
        return this.delegate.isUnnamed();
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
        return this.delegate.accept(v, p);
    }

}
