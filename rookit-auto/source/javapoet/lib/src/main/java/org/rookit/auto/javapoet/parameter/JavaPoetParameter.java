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
package org.rookit.auto.javapoet.parameter;

import com.squareup.javapoet.ParameterSpec;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatable;
import org.rookit.auto.source.parameter.MutableParameterSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.lang.annotation.Annotation;
import java.util.Collection;

final class JavaPoetParameter implements MutableParameterSource {

    private final JavaPoetMutableAnnotatable annotatable;
    private final CharSequence name;
    private final TypeReferenceSource type;
    private final ParameterSpec.Builder builder;
    private boolean usedInSuperclass;

    JavaPoetParameter(
            final JavaPoetMutableAnnotatable annotatable,
            final CharSequence name,
            final TypeReferenceSource type,
            final ParameterSpec.Builder builder) {
        this.annotatable = annotatable;
        this.name = name;
        this.type = type;
        this.builder = builder;
    }

    ParameterSpec build() {
        final ParameterSpec.Builder builder = this.builder.build()
                .toBuilder();

        builder.addAnnotations(this.annotatable.annotationSpecs());

        return builder.build();
    }

    @Override
    public MutableParameterSource toBeUsedInSuperclass(final boolean usedInSuperclass) {
        this.usedInSuperclass = usedInSuperclass;
        return this;
    }

    @Override
    public MutableParameterSource self() {
        return this;
    }

    @Override
    public MutableParameterSource addAnnotation(final AnnotationSource annotation) {
        this.annotatable.addAnnotation(annotation);
        return this;
    }

    @Override
    public MutableParameterSource addAnnotationByClass(final Class<? extends Annotation> annotation) {
        this.annotatable.addAnnotationByClass(annotation);
        return this;
    }

    @Override
    public MutableParameterSource removeAnnotationByClass(final Class<? extends Annotation> annotation) {
        this.annotatable.removeAnnotationByClass(annotation);
        return this;
    }

    @Override
    public Collection<AnnotationSource> annotations() {
        return this.annotatable.annotations();
    }

    @Override
    public CharSequence name() {
        return this.name;
    }

    @Override
    public TypeReferenceSource type() {
        return this.type;
    }

    @Override
    public boolean isToBeUsedInSuperclass() {
        return this.usedInSuperclass;
    }

    @Override
    public String toString() {
        return "JavaPoetParameter{" +
                "annotatable=" + this.annotatable +
                ", name='" + this.name + '\'' +
                ", type=" + this.type +
                ", builder=" + this.builder +
                ", usedInSuperclass=" + this.usedInSuperclass +
                "}";
    }

}
