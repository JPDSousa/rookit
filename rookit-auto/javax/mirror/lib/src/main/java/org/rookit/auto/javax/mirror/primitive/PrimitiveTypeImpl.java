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
package org.rookit.auto.javax.mirror.primitive;

import com.google.common.collect.ImmutableList;
import org.rookit.utils.array.ArrayUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

final class PrimitiveTypeImpl implements ExtendedPrimitiveType {

    private final TypeKind kind;
    private final TypeElement boxedClass;
    private final ArrayUtils arrayUtils;

    PrimitiveTypeImpl(final TypeKind kind, final TypeElement boxedClass, final ArrayUtils arrayUtils) {
        this.kind = kind;
        this.boxedClass = boxedClass;
        this.arrayUtils = arrayUtils;
    }

    @Override
    public TypeElement boxedClass() {
        return this.boxedClass;
    }

    @Override
    public TypeKind getKind() {
        return this.kind;
    }

    @Override
    public <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        return v.visitPrimitive(this, p);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return ImmutableList.of();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        //noinspection ReturnOfNull -> to comply with interface contract
        return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.arrayUtils.emptyArray();
    }

    @Override
    public String toString() {
        return "PrimitiveTypeImpl{" +
                "kind=" + this.kind +
                ", boxedClass=" + this.boxedClass +
                ", arrayUtils=" + this.arrayUtils +
                "}";
    }

}
