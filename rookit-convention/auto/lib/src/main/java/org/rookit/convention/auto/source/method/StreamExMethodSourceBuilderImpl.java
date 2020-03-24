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
package org.rookit.convention.auto.source.method;

import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.javax.visitor.StreamExConventionBuilder;
import org.rookit.convention.auto.javax.visitor.TypeBasedMethodVisitor;
import org.rookit.convention.auto.property.Property;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

final class StreamExMethodSourceBuilderImpl<V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
        implements StreamExMethodSourceBuilder<V, P> {

    private final StreamExConventionBuilder<V, MethodSource, P> delegate;

    StreamExMethodSourceBuilderImpl(
            final StreamExConventionBuilder<V, MethodSource, P> delegate) {
        this.delegate = delegate;
    }

    private StreamExMethodSourceBuilder<V, P> newStage(
            final StreamExConventionBuilder<V, MethodSource, P> builder) {
        return new StreamExMethodSourceBuilderImpl<>(builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypeBasedMethodVisitorBuilder<TypeBasedMethodVisitor<P>, P> withType(
            final ExtendedTypeMirror type) {
        return new TypeBasedMethodVisitorBuilderImpl<>(
                (StreamExConventionBuilder<TypeBasedMethodVisitor<P>, MethodSource, P>) this.delegate,
                type
        );
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> withTypeAdapter(
            final Adapter<ExtendedTypeElement> adapter) {
        return newStage(this.delegate.withTypeAdapter(adapter));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> withConventionTypeAdapter(
            final Adapter<ConventionTypeElement> adapter) {
        return newStage(this.delegate.withConventionTypeAdapter(adapter));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<MethodSource>> resultReducer) {
        return newStage(this.delegate.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.delegate.build();
    }

    @Override
    public String toString() {
        return "StreamExMethodSourceBuilderImpl{" +
                "delegate=" + this.delegate +
                "}";
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> routeThroughFilter(
            final ConventionTypeElementVisitor<StreamEx<MethodSource>, P> fallbackVisitor,
            final Predicate<Property> filter) {
        return newStage(this.delegate.routeThroughFilter(fallbackVisitor, filter));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> add(final V visitor) {
        return newStage(this.delegate.add(visitor));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> add(final Provider<V> visitor) {
        return newStage(this.delegate.add(visitor));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> addAll(
            final Collection<? extends V> visitors) {
        return newStage(this.delegate.addAll(visitors));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<MethodSource>, P> visitor) {
        return newStage(this.delegate.withDirtyFallback(visitor));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> filter(final Predicate<ExtendedElement> predicate) {

        return newStage(this.delegate.filter(predicate));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> filterIfAnnotationPresent(
            final Class<? extends Annotation> annotationClass) {
        return newStage(this.delegate.filterIfAnnotationPresent(annotationClass));
    }

    @Override
    public StreamExMethodSourceBuilder<V, P> filterIfAnyAnnotationPresent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newStage(this.delegate.filterIfAnyAnnotationPresent(annotationClasses));
    }
}
