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
package org.rookit.auto.javax.runtime.annotation.node;

import io.reactivex.Completable;
import org.rookit.auto.javax.mirror.annotation.node.MutableNodeAnnotation;
import org.rookit.auto.javax.mirror.declared.ExtendedDeclaredType;
import org.rookit.utils.graph.DependencyWrapper;

import javax.lang.model.element.AnnotationMirror;
import java.lang.annotation.Annotation;
import java.util.List;

final class MutableNodeImpl implements MutableNodeAnnotation {

    private final DependencyWrapper<ExtendedDeclaredType> annotationType;

    MutableNodeImpl(final DependencyWrapper<ExtendedDeclaredType> annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    public Completable annotationType(final ExtendedDeclaredType declaredType) {
        return this.annotationType.set(declaredType);
    }

    @Override
    public ExtendedDeclaredType annotationType() {
        return this.annotationType.fetch();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        throw new IllegalStateException("Annotation Mirror cannot be treated as TypeMirrors");
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        throw new IllegalStateException("Annotation Mirror cannot be treated as TypeMirrors");
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        throw new IllegalStateException("Annotation Mirror cannot be treated as TypeMirrors");
    }

    @Override
    public String toString() {
        return "MutableNodeImpl{" +
                "annotationType=" + this.annotationType +
                "}";
    }

}
