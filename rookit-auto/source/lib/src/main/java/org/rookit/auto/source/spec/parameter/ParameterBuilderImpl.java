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
package org.rookit.auto.source.spec.parameter;

import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.javax.visitor.StreamExBuilder;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class ParameterBuilderImpl<V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P>
        implements ParameterBuilder<V, R, P> {

    private final StreamExBuilder<V, R, P> builder;
    private final ExtendedElementVisitors visitors;

    ParameterBuilderImpl(
            final StreamExBuilder<V, R, P> builder,
            final ExtendedElementVisitors visitors) {
        this.builder = builder;
        this.visitors = visitors;
    }

    private ParameterBuilder<V, R, P> newBuilder(final StreamExBuilder<V, R, P> builder) {
        return new ParameterBuilderImpl<>(builder, this.visitors);
    }

    @Override
    public ParameterBuilder<V, R, P> withTypeAdapter(final Adapter<ExtendedTypeElement> adapter) {
        return newBuilder(this.builder.withTypeAdapter(adapter));
    }

    @Override
    public ParameterBuilder<V, R, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<R>> resultReducer) {
        return newBuilder(this.builder.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.builder.build();
    }

    @Override
    public ParameterBuilder<V, R, P> add(final V visitor) {
        return newBuilder(this.builder.add(visitor));
    }

    @Override
    public ParameterBuilder<V, R, P> add(final Provider<V> visitor) {
        return newBuilder(this.builder.add(visitor));
    }

    @Override
    public ParameterBuilder<V, R, P> addAll(final Collection<? extends V> visitors) {
        return newBuilder(this.builder.addAll(visitors));
    }

    @Override
    public ParameterBuilder<V, R, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<R>, P> visitor) {
        return newBuilder(this.builder.withDirtyFallback(visitor));
    }

    @Override
    public ParameterBuilder<V, R, P> filterIfAnnotationAbsent(
            final Class<? extends Annotation> annotationClass) {
        return newBuilder(this.builder.filterIfAnnotationAbsent(annotationClass));
    }

    @Override
    public ParameterBuilder<V, R, P> filterIfAllAnnotationsAbsent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newBuilder(this.builder.filterIfAllAnnotationsAbsent(annotationClasses));
    }

    @Override
    public <V1 extends ExtendedElementVisitor<StreamEx<Parameter<R>>, P>>
    StreamExBuilder<V1, Parameter<R>, P> asParameter(
            final Function<ExtendedElementVisitor<StreamEx<Parameter<R>>, P>, V1> downcastAdapter) {
        return this.visitors.streamExBuilder(
                downcastAdapter.apply(new ExtendedParameterAdapterVisitor<>(this.builder.build(), false)),
                downcastAdapter);
    }

    @Override
    public <V1 extends ExtendedElementVisitor<StreamEx<Parameter<R>>, P>>
    StreamExBuilder<V1, Parameter<R>, P> asSuperParameter(
            final Function<ExtendedElementVisitor<StreamEx<Parameter<R>>, P>, V1> downcastAdapter) {
        return this.visitors.streamExBuilder(
                downcastAdapter.apply(new ExtendedParameterAdapterVisitor<>(this.builder.build(), true)),
                downcastAdapter);
    }

    @Override
    public String toString() {
        return "ParameterBuilderImpl{" +
                "builder=" + this.builder +
                ", visitors=" + this.visitors +
                "}";
    }

}
