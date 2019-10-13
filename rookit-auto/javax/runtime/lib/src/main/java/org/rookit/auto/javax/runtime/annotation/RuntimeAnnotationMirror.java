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

import com.google.common.collect.ImmutableMap;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import java.lang.annotation.Annotation;
import java.util.Map;

final class RuntimeAnnotationMirror implements AnnotationMirror {

    private final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues;
    private final Annotation annotation;
    private final DeclaredType declaredType;

    RuntimeAnnotationMirror(
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues,
            final Annotation annotation,
            final DeclaredType declaredType) {
        this.elementValues = ImmutableMap.copyOf(elementValues);
        this.annotation = annotation;
        this.declaredType = declaredType;
    }

    @Override
    public DeclaredType getAnnotationType() {
        return this.declaredType;
    }

    @Override
    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues() {
        return this.elementValues;
    }

    @Override
    public String toString() {
        return "RuntimeAnnotationMirror{" +
                "elementValues=" + this.elementValues +
                ", annotation=" + this.annotation +
                ", declaredType=" + this.declaredType +
                "}";
    }

}
