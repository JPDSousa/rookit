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
package org.rookit.auto.source.visitor;

import com.google.common.collect.ImmutableList;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.doc.JavadocTemplate1;
import org.rookit.auto.source.type.annotation.AnnotationBuilder;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceBuilder;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class AnnotationBuilderImpl<V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P>
        implements AnnotationBuilder<V, P> {

    private final TypeSourceBuilder<V, P> builder;
    private final Collection<AnnotationSource> annotations;

    AnnotationBuilderImpl(final TypeSourceBuilder<V, P> builder,
                          final Collection<AnnotationSource> annotations) {
        this.builder = builder;
        this.annotations = ImmutableList.copyOf(annotations);
    }

    private AnnotationBuilder<V, P> newStage(
            final TypeSourceBuilder<V, P> builder) {
        return new AnnotationBuilderImpl<>(builder, this.annotations);
    }

    @Override
    public AnnotationBuilder<V, P> withAnnotations(
            final Iterable<AnnotationSource> annotations) {
        return newStage(this.builder.withAnnotations(annotations));
    }

    @Override
    public AnnotationBuilder<V, P> copyBodyFrom(final ExtendedTypeElement source) {
        return newStage(this.builder.copyBodyFrom(source));
    }

    @Override
    public AnnotationBuilder<V, P> copyBodyFrom(
            final Function<ExtendedElement, ExtendedTypeElement> extractionFunction) {
        return newStage(this.builder.copyBodyFrom(extractionFunction));
    }

    @Override
    public AnnotationBuilder<V, P> withClassJavadoc(
            final JavadocTemplate1 template) {
        return newStage(this.builder.withClassJavadoc(template));
    }

    @Override
    public AnnotationBuilder<V, P> bindingAnnotation() {
        return withAnnotations(this.annotations);
    }

    @Override
    public AnnotationBuilder<V, P> withTypeAdapter(
            final Adapter<ExtendedTypeElement> adapter) {
        return newStage(this.builder.withTypeAdapter(adapter));
    }

    @Override
    public AnnotationBuilder<V, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<TypeSource>> resultReducer) {
        return newStage(this.builder.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.builder.build();
    }

    @Override
    public AnnotationBuilder<V, P> add(final V visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public AnnotationBuilder<V, P> add(final Provider<V> visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public AnnotationBuilder<V, P> addAll(final Collection<? extends V> visitors) {
        return newStage(this.builder.addAll(visitors));
    }

    @Override
    public AnnotationBuilder<V, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<TypeSource>, P> visitor) {
        return newStage(this.builder.withDirtyFallback(visitor));
    }

    @Override
    public AnnotationBuilder<V, P> filterIfAnnotationAbsent(
            final Class<? extends Annotation> annotationClass) {
        return newStage(this.builder.filterIfAnnotationAbsent(annotationClass));
    }

    @Override
    public AnnotationBuilder<V, P> filterIfAllAnnotationsAbsent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newStage(this.builder.filterIfAllAnnotationsAbsent(annotationClasses));
    }

    @Override
    public String toString() {
        return "AnnotationBuilderImpl{" +
                "builder=" + this.builder +
                ", annotations=" + this.annotations +
                "}";
    }
}
