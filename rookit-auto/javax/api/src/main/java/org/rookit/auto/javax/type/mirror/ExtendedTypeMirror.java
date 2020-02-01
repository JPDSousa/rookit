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
package org.rookit.auto.javax.type.mirror;

import org.rookit.utils.optional.Optional;
import org.rookit.utils.repetition.Repetition;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

public interface ExtendedTypeMirror extends TypeMirror {

    Repetition repetition();

    Optional<Element> toElement();

    boolean isSameTypeErasure(ExtendedTypeMirror other);

    boolean isSameType(ExtendedTypeMirror other);

    ExtendedTypeMirror erasure();

    List<? extends ExtendedTypeMirror> typeParameters();

    TypeMirror original();

    ExtendedTypeMirror boxIfPrimitive();

    @Override
    default TypeKind getKind() {
        return original().getKind();
    }

    @Override
    default <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        return original().accept(v, p);
    }

    @Override
    default List<? extends AnnotationMirror> getAnnotationMirrors() {
        return original().getAnnotationMirrors();
    }

    @Override
    default <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return original().getAnnotation(annotationType);
    }

    @Override
    default <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return original().getAnnotationsByType(annotationType);
    }

}
