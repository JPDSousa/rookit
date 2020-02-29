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
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceBuilder;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;

final class ParameterSourceBuilderImpl<V extends ExtendedElementVisitor<StreamEx<ParameterSource>, P>, P>
        implements ParameterSourceBuilder<V, P> {

    private final StreamExBuilder<V, ParameterSource, P> builder;
    private final ExtendedElementVisitors visitors;
    private final ParameterSourceFactory parameterFactory;

    ParameterSourceBuilderImpl(
            final StreamExBuilder<V, ParameterSource, P> builder,
            final ExtendedElementVisitors visitors,
            final ParameterSourceFactory parameterFactory) {
        this.builder = builder;
        this.visitors = visitors;
        this.parameterFactory = parameterFactory;
    }

    private ParameterSourceBuilder<V, P> newBuilder(final StreamExBuilder<V, ParameterSource, P> builder) {
        return new ParameterSourceBuilderImpl<>(builder, this.visitors, this.parameterFactory);
    }

    @Override
    public ParameterSourceBuilder<V, P> withTypeAdapter(final Adapter<ExtendedTypeElement> adapter) {
        return newBuilder(this.builder.withTypeAdapter(adapter));
    }

    @Override
    public ParameterSourceBuilder<V, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<ParameterSource>> resultReducer) {
        return newBuilder(this.builder.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.builder.build();
    }

    @Override
    public ParameterSourceBuilder<V, P> add(final V visitor) {
        return newBuilder(this.builder.add(visitor));
    }

    @Override
    public ParameterSourceBuilder<V, P> add(final Provider<V> visitor) {
        return newBuilder(this.builder.add(visitor));
    }

    @Override
    public ParameterSourceBuilder<V, P> addAll(final Collection<? extends V> visitors) {
        return newBuilder(this.builder.addAll(visitors));
    }

    @Override
    public ParameterSourceBuilder<V, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<ParameterSource>, P> visitor) {
        return newBuilder(this.builder.withDirtyFallback(visitor));
    }

    @Override
    public ParameterSourceBuilder<V, P> filterIfAnnotationPresent(
            final Class<? extends Annotation> annotationClass) {
        return newBuilder(this.builder.filterIfAnnotationPresent(annotationClass));
    }

    @Override
    public ParameterSourceBuilder<V, P> filterIfAnyAnnotationPresent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newBuilder(this.builder.filterIfAnyAnnotationPresent(annotationClasses));
    }

    @Override
    public String toString() {
        return "ParameterSourceBuilderImpl{" +
                "builder=" + this.builder +
                ", visitors=" + this.visitors +
                ", parameterFactory=" + this.parameterFactory +
                "}";
    }

}
