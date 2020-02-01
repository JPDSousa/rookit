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
package org.rookit.auto.javapoet;

import com.squareup.javapoet.AnnotationSpec;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceAdapter;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Collections.unmodifiableCollection;

final class JavaPoetMutableAnnotatableImpl implements JavaPoetMutableAnnotatable {

    private final AnnotationSourceFactory factory;
    private final AnnotationSourceAdapter<AnnotationSpec> adapter;
    private final Collection<AnnotationSource> annotations;

    JavaPoetMutableAnnotatableImpl(
            final AnnotationSourceFactory factory,
            final AnnotationSourceAdapter<AnnotationSpec> adapter,
            final Collection<AnnotationSource> annotations) {
        this.factory = factory;
        this.adapter = adapter;
        this.annotations = new ArrayList<>(annotations);
    }

    @Override
    public Collection<AnnotationSpec> annotationSpecs() {
        
        return this.annotations.stream()
                .map(this.adapter::adaptAnnotation)
                .collect(toImmutableList());
    }

    @Override
    public JavaPoetMutableAnnotatable self() {
        
        return this;
    }

    @Override
    public JavaPoetMutableAnnotatable addAnnotation(final AnnotationSource annotation) {

        this.annotations.add(annotation);
        return this;
    }

    @Override
    public JavaPoetMutableAnnotatable addAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotations.add(this.factory.create(annotation));
        return this;
    }

    @Override
    public JavaPoetMutableAnnotatable removeAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotations.remove(this.factory.create(annotation));
        return this;
    }

    @Override
    public Collection<AnnotationSource> annotations() {

        return unmodifiableCollection(this.annotations);
    }

    @Override
    public String toString() {
        return "JavaPoetMutableAnnotatableImpl{" +
                "factory=" + this.factory +
                ", adapter=" + this.adapter +
                ", annotations=" + this.annotations +
                "}";
    }

}
