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
package org.rookit.auto.javax.runtime.annotation;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.List;

final class RuntimeAnnotatedConstruct implements AnnotatedConstruct {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeAnnotatedConstruct.class);

    private final AnnotatedElement annotatedElement;
    private final List<? extends AnnotationMirror> annotationMirrors;

    RuntimeAnnotatedConstruct(
            final AnnotatedElement annotatedElement,
            final Collection<? extends AnnotationMirror> annotationMirrors) {
        this.annotatedElement = annotatedElement;
        this.annotationMirrors = ImmutableList.copyOf(annotationMirrors);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return this.annotationMirrors;
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating call to {}", this.annotatedElement);
        return this.annotatedElement.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating call to {}", this.annotatedElement);
        return this.annotatedElement.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return "RuntimeAnnotatedConstruct{" +
                "annotatedElement=" + this.annotatedElement +
                ", annotationMirrors=" + this.annotationMirrors +
                "}";
    }

}
