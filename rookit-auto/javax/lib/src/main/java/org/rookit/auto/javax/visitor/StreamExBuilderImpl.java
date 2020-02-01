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
package org.rookit.auto.javax.visitor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class StreamExBuilderImpl<V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P>
        implements StreamExBuilder<V, R, P> {

    private final V visitor;
    private final Collection<V> visitors;
    private final Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter;

    StreamExBuilderImpl(
            final V visitor,
            final Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {
        this.visitor = visitor;
        this.downcastAdapter = downcastAdapter;
        this.visitors = Lists.newArrayList();
    }

    @Override
    public StreamExBuilder<V, R, P> withTypeAdapter(
            final Adapter<ExtendedTypeElement> adapter) {
        return new StreamExBuilderImpl<>(this.downcastAdapter.apply(new TypeAdapterVisitor<>(build(), adapter)),
                                         this.downcastAdapter);
    }

    @Override
    public StreamExBuilder<V, R, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<R>> resultReducer) {
        return new StreamExBuilderImpl<>(this.downcastAdapter.apply(new DeepElementVisitor<>(build(), resultReducer)),
                                         downcastAdapter);
    }

    @Override
    public V build() {
        if (!this.visitors.isEmpty()) {
            final Collection<V> visitors = ImmutableList
                    .<V>builder()
                    .add(this.visitor)
                    .addAll(this.visitors)
                    .build();
            return this.downcastAdapter.apply(new MultiVisitor<>(visitors));
        }
        return this.visitor;
    }

    @Override
    public StreamExBuilder<V, R, P> add(final V visitor) {
        this.visitors.add(visitor);
        return this;
    }

    @Override
    public StreamExBuilder<V, R, P> add(final Provider<V> visitor) {
        this.visitors.add(this.downcastAdapter.apply(new LazyVisitor<>(visitor)));
        return this;
    }

    @Override
    public StreamExBuilder<V, R, P> addAll(final Collection<? extends V> visitors) {
        this.visitors.addAll(visitors);
        return this;
    }

    @Override
    public StreamExBuilder<V, R, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<R>, P> visitor) {
        return new StreamExBuilderImpl<>(this.downcastAdapter.apply(new DirtyFallbackVisitor<>(build(), visitor)),
                                         downcastAdapter);
    }

    @Override
    public StreamExBuilder<V, R, P> filterIfAnnotationPresent(
            final Class<? extends Annotation> annotationClass) {
        return filterIfAnyAnnotationPresent(ImmutableSet.of(annotationClass));
    }

    @Override
    public StreamExBuilder<V, R, P> filterIfAnyAnnotationPresent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return new StreamExBuilderImpl<>(this.downcastAdapter.apply(new FilterStreamExVisitor<>(
                new AnyAnnotationsPresentChecker(annotationClasses),
                build()
        )), this.downcastAdapter);
    }

    @Override
    public String toString() {
        return "StreamExBuilderImpl{" +
                "visitor=" + this.visitor +
                ", visitors=" + this.visitors +
                ", downcastAdapter=" + this.downcastAdapter +
                "}";
    }

}
