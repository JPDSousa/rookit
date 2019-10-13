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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

@Deprecated
final class AbsentPackageElement implements PackageElement {

    private final Name qualifiedName;

    AbsentPackageElement(final Name qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    @Override
    public Name getQualifiedName() {
        return this.qualifiedName;
    }

    @Override
    public TypeMirror asType() {
        throw new UnsupportedOperationException("Package " + this.qualifiedName + " does not exist");
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PACKAGE;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return ImmutableSet.of();
    }

    @Override
    public Name getSimpleName() {
        return this.qualifiedName;
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
        return ImmutableList.of();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return ImmutableList.of();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return null;
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return v.visitPackage(this, p);
    }

    @Override
    public boolean isUnnamed() {
        return false;
    }

    @Override
    public Element getEnclosingElement() {
        return null;
    }

}
