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
package org.rookit.convention.auto.javapoet.type;

import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.type.TypeSourceBuilder;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.doc.JavadocTemplate1;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitors;
import org.rookit.convention.auto.source.type.ConventionTypeSourceBuilder;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class ConventionTypeSourceBuilderImpl<V extends ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P>
        implements ConventionTypeSourceBuilder<V, P> {

    private final TypeSourceBuilder<V, P> builder;
    private final ConventionTypeElementVisitors visitors;

    ConventionTypeSourceBuilderImpl(
            final TypeSourceBuilder<V, P> builder,
            final ConventionTypeElementVisitors visitors) {
        this.builder = builder;
        this.visitors = visitors;
    }

    private ConventionTypeSourceBuilder<V, P> newStage(
            final TypeSourceBuilder<V, P> builder) {
        return new ConventionTypeSourceBuilderImpl<>(builder, this.visitors);
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> withAnnotations(
            final Iterable<AnnotationSource> annotations) {
        return newStage(this.builder.withAnnotations(annotations));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> copyBodyFrom(
            final ExtendedTypeElement source) {
        return newStage(this.builder.copyBodyFrom(source));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> copyBodyFrom(
            final Function<ExtendedElement, ExtendedTypeElement> extractionFunction) {
        return newStage(this.builder.copyBodyFrom(extractionFunction));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> withClassJavadoc(
            final JavadocTemplate1 template) {
        return newStage(this.builder.withClassJavadoc(template));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> withTypeAdapter(
            final Adapter<ExtendedTypeElement> adapter) {
        return newStage(this.builder.withTypeAdapter(adapter));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<TypeSource>> resultReducer) {
        return newStage(this.builder.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.builder.build();
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> add(final V visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> add(final Provider<V> visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> addAll(
            final Collection<? extends V> visitors) {
        return newStage(this.builder.addAll(visitors));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<TypeSource>, P> visitor) {
        return newStage(this.builder.withDirtyFallback(visitor));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> filterIfAnnotationPresent(
            final Class<? extends Annotation> annotationClass) {
        return newStage(this.builder.filterIfAnnotationPresent(annotationClass));
    }

    @Override
    public ConventionTypeSourceBuilder<V, P> filterIfAnyAnnotationPresent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newStage(this.builder.filterIfAnyAnnotationPresent(annotationClasses));
    }

    @Override
    public String toString() {
        return "ConventionTypeSourceBuilderImpl{" +
                "builder=" + this.builder +
                ", visitors=" + this.visitors +
                "}";
    }

}
