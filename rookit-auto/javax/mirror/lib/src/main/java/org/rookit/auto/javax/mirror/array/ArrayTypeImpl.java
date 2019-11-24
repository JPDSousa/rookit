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
package org.rookit.auto.javax.mirror.array;

import com.google.common.collect.ImmutableList;
import org.rookit.utils.array.ArrayUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

final class ArrayTypeImpl implements ArrayType {

    private final TypeMirror componentType;
    private final ArrayUtils arrayUtils;

    ArrayTypeImpl(final TypeMirror componentType, final ArrayUtils arrayUtils) {
        this.componentType = componentType;
        this.arrayUtils = arrayUtils;
    }

    @Override
    public TypeMirror getComponentType() {
        return this.componentType;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.ARRAY;
    }

    @Override
    public <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        return v.visitArray(this, p);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return ImmutableList.of();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        //noinspection ReturnOfNull -> to comply with method contract
        return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.arrayUtils.emptyArray();
    }

    @Override
    public String toString() {
        return "ArrayTypeImpl{" +
                "componentType=" + this.componentType +
                ", arrayUtils=" + this.arrayUtils +
                "}";
    }

}
