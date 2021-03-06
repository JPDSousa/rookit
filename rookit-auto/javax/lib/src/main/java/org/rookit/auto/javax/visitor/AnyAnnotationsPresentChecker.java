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
package org.rookit.auto.javax.visitor;

import org.rookit.auto.javax.ExtendedElement;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.function.Predicate;

final class AnyAnnotationsPresentChecker implements Predicate<ExtendedElement> {

    private final Iterable<? extends Class<? extends Annotation>> annotationClasses;

    AnyAnnotationsPresentChecker(final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        this.annotationClasses = annotationClasses;
    }

    @Override
    public boolean test(final ExtendedElement construct) {
        for (final Class<? extends Annotation> annotationClass : this.annotationClasses) {
            if (Objects.nonNull(construct.getAnnotation(annotationClass))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AnyAnnotationsPresentChecker{" +
                "annotationClasses=" + this.annotationClasses +
                "}";
    }
}
