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
package org.rookit.auto.javax.runtime;

import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.mirror.no.NoTypeFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import java.util.List;

final class RuntimeTypes implements Types {

    private final NoTypeFactory noTypeFactory;

    @Inject
    private RuntimeTypes(final NoTypeFactory noTypeFactory) {
        this.noTypeFactory = noTypeFactory;
    }

    @Override
    public Element asElement(final TypeMirror t) {
        return null;
    }

    @Override
    public boolean isSameType(final TypeMirror t1, final TypeMirror t2) {
        return false;
    }

    @Override
    public boolean isSubtype(final TypeMirror t1, final TypeMirror t2) {
        return false;
    }

    @Override
    public boolean isAssignable(final TypeMirror t1, final TypeMirror t2) {
        return false;
    }

    @Override
    public boolean contains(final TypeMirror t1, final TypeMirror t2) {
        return false;
    }

    @Override
    public boolean isSubsignature(final ExecutableType m1, final ExecutableType m2) {
        return false;
    }

    @Override
    public List<? extends TypeMirror> directSupertypes(final TypeMirror t) {
        return null;
    }

    @Override
    public TypeMirror erasure(final TypeMirror t) {
        return null;
    }

    @Override
    public TypeElement boxedClass(final PrimitiveType p) {
        return null;
    }

    @Override
    public PrimitiveType unboxedType(final TypeMirror t) {
        return null;
    }

    @Override
    public TypeMirror capture(final TypeMirror t) {
        return null;
    }

    @Override
    public PrimitiveType getPrimitiveType(final TypeKind kind) {
        return null;
    }

    @Override
    public NullType getNullType() {
        return null;
    }

    @Override
    public NoType getNoType(final TypeKind kind) {
        return this.noTypeFactory.noType();
    }

    @Override
    public ArrayType getArrayType(final TypeMirror componentType) {
        return null;
    }

    @Override
    public WildcardType getWildcardType(
            final TypeMirror extendsBound, final TypeMirror superBound) {
        return null;
    }

    @Override
    public DeclaredType getDeclaredType(
            final TypeElement typeElem, final TypeMirror... typeArgs) {
        return null;
    }

    @Override
    public DeclaredType getDeclaredType(
            final DeclaredType containing, final TypeElement typeElem, final TypeMirror... typeArgs) {
        return null;
    }

    @Override
    public TypeMirror asMemberOf(final DeclaredType containing, final Element element) {
        return null;
    }

}
