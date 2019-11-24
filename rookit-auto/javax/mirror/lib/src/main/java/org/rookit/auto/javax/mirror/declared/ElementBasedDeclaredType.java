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
package org.rookit.auto.javax.mirror.declared;

import com.google.common.collect.ImmutableList;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

final class ElementBasedDeclaredType implements DeclaredType {

    private final TypeMirror enclosing;
    private final TypeElement element;
    private final List<TypeMirror> typeArgs;

    ElementBasedDeclaredType(
            final TypeMirror enclosing,
            final TypeElement element,
            final Collection<TypeMirror> typeArgs) {
        this.enclosing = enclosing;
        this.element = element;
        this.typeArgs = ImmutableList.copyOf(typeArgs);
    }

    @Override
    public Element asElement() {

        return this.element;
    }

    @Override
    public TypeMirror getEnclosingType() {

        return this.enclosing;
    }

    @Override
    public List<? extends TypeMirror> getTypeArguments() {
        return this.typeArgs;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.DECLARED;
    }

    @Override
    public <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        return v.visitDeclared(this, p);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return this.element.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return this.element.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.element.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return "ElementBasedDeclaredType{" +
                "element=" + this.element +
                ", typeArgs=" + this.typeArgs +
                "}";
    }

}
