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
import com.google.inject.Inject;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.utils.guice.Id;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import javax.lang.model.element.Name;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

final class ExtendedElementVisitorsImpl implements ExtendedElementVisitors {

    private final ExtendedElementVisitor<String, Void> idVisitor;
    private final VoidUtils voidUtils;
    private final OptionalFactory optionalFactory;

    @Inject
    private ExtendedElementVisitorsImpl(
            @Id final ExtendedElementVisitor<String, Void> idVisitor,
            final VoidUtils voidUtils,
            final OptionalFactory optionalFactory) {
        this.idVisitor = idVisitor;
        this.voidUtils = voidUtils;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public <P> ExtendedElementVisitor<Boolean, P> isPresent(final Class<? extends Annotation> annotationClass) {
        return new PresentAnnotationVisitor<>(construct -> Objects.nonNull(construct.getAnnotation(annotationClass)));
    }

    @Override
    public <R, P> ExtendedElementVisitor<StreamEx<R>, P> emptyStreamVisitor() {
        return EmptyStreamExVisitor.create();
    }

    @Override
    public <P> ExtendedElementVisitor<Name, P> qualifiedNameVisitor() {
        return QualifiedNameVisitor.singleton();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            final V visitor,
            final Function<ExtendedElementVisitor<R, P>, V> downcastAdapter) {
        return (B) new BuilderImpl<>(visitor, downcastAdapter, this.idVisitor,
                                     this.voidUtils,
                                     this.optionalFactory
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            final Provider<V> visitor,
            final Function<ExtendedElementVisitor<R, P>, V> downcastAdapter) {
        return (B) new BuilderImpl<>(downcastAdapter.apply(new LazyVisitor<>(visitor)), downcastAdapter,
                                     this.idVisitor, this.voidUtils,
                                     this.optionalFactory);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            final V visitor,
            final Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {
        return new StreamExBuilderImpl<>(visitor, downcastAdapter, idVisitor, voidUtils, optionalFactory);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            final Iterable<? extends V> visitors,
            final Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {
        StreamExBuilder<V, R, P> builder = null;
        for (final V visitor : visitors) {
            builder = (Objects.isNull(builder)) ? builder(visitor, downcastAdapter) : builder.add(visitor);
        }
        if (Objects.isNull(builder)) {
            throw new IllegalArgumentException("Must provide at least one visitor");
        }

        return builder;
    }

    @Override
    public <R, P> ExtendedElementVisitor<StreamEx<R>, P> streamEx(
            final Collection<? extends ExtendedElementVisitor<R, P>> visitors) {
        return visitors.stream()
                .map(this::streamEx)
                .collect(Collectors.collectingAndThen(
                        ImmutableList.toImmutableList(),
                        streamedVisitors -> streamExBuilder(streamedVisitors, visitor -> visitor).build()
                ));
    }

    @Override
    public <R, P> ExtendedElementVisitor<StreamEx<R>, P> streamEx(final ExtendedElementVisitor<R, P> visitor) {
        return new SingleAdapterVisitor<>(visitor);
    }

}
