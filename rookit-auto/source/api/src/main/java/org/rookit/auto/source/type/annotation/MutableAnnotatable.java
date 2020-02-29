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
package org.rookit.auto.source.type.annotation;

import java.lang.annotation.Annotation;

public interface MutableAnnotatable<S> extends Annotatable {

    S self();

    default S addAnnotations(final Iterable<AnnotationSource> annotations) {
        annotations.forEach(this::addAnnotation);
        return self();
    }

    S addAnnotation(AnnotationSource annotation);

    default S addAnnotationsByClass(final Iterable<Class<? extends Annotation>> annotations) {
        annotations.forEach(this::addAnnotationByClass);
        return self();
    }

    S addAnnotationByClass(Class<? extends Annotation> annotation);

    default S removeAnnotationsByClass(final Iterable<Class<? extends Annotation>> annotations) {
        annotations.forEach(this::removeAnnotationByClass);
        return self();
    }

    S removeAnnotationByClass(Class<? extends Annotation> annotation);

}
