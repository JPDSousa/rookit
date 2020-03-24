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

import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.javax.visitor.StreamExBuilder;
import org.rookit.auto.source.doc.JavadocTemplate1;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceBuilder;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.utils.adapt.Adapter;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

final class TypeSourceBuilderImpl<V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P>
        implements TypeSourceBuilder<V, P> {

    private final TypeSourceFactory typeFactory;
    private final MethodSourceFactory methodFactory;
    private final StreamExBuilder<V, TypeSource, P> builder;
    private final ExtendedElementVisitors visitors;
    private final Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter;

    TypeSourceBuilderImpl(
            final TypeSourceFactory typeFactory,
            final MethodSourceFactory methodFactory,
            final StreamExBuilder<V, TypeSource, P> builder,
            final ExtendedElementVisitors visitors,
            final Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter) {

        this.typeFactory = typeFactory;
        this.methodFactory = methodFactory;
        this.builder = builder;
        this.visitors = visitors;
        this.downcastAdapter = downcastAdapter;
    }

    private TypeSourceBuilder<V, P> newStage(
            final StreamExBuilder<V, TypeSource, P> builder) {

        return new TypeSourceBuilderImpl<>(
                this.typeFactory,
                this.methodFactory,
                builder,
                this.visitors,
                this.downcastAdapter
        );
    }

    @Override
    public TypeSourceBuilder<V, P> withAnnotations(
            final Iterable<AnnotationSource> annotations) {

        final V visitor = this.downcastAdapter.apply(
                new AddAnnotationsVisitor<>(this.typeFactory, build(), annotations)
        );
        return newStage(this.visitors.streamExBuilder(visitor, this.downcastAdapter));
    }

    @Override
    public TypeSourceBuilder<V, P> copyBodyFrom(final ExtendedTypeElement source) {

        return copyBodyFrom(construct -> source);
    }

    @Override
    public TypeSourceBuilder<V, P> copyBodyFrom(
            final Function<ExtendedElement, ExtendedTypeElement> extractionFunction) {

        return newStage(this.visitors.streamExBuilder(
                this.downcastAdapter.apply(new CopyBodyFromVisitor<>(this.typeFactory,
                                                                     this.methodFactory,
                                                                     extractionFunction,
                                                                     build()
                )),
                this.downcastAdapter));
    }

    @Override
    public TypeSourceBuilder<V, P> withClassJavadoc(
            final JavadocTemplate1 template) {

        return newStage(this.visitors.streamExBuilder(
                this.downcastAdapter.apply(new ClassJavadocVisitor<>(this.typeFactory,
                                                                     build(), template)
                ), this.downcastAdapter));
    }

    @Override
    public TypeSourceBuilder<V, P> withTypeAdapter(
            final Adapter<ExtendedTypeElement> adapter) {

        return newStage(this.builder.withTypeAdapter(adapter));
    }

    @Override
    public TypeSourceBuilder<V, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<TypeSource>> resultReducer) {
        return newStage(this.builder.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.builder.build();
    }

    @Override
    public TypeSourceBuilder<V, P> add(final V visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public TypeSourceBuilder<V, P> add(final Provider<V> visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public TypeSourceBuilder<V, P> addAll(final Collection<? extends V> visitors) {
        return newStage(this.builder.addAll(visitors));
    }

    @Override
    public TypeSourceBuilder<V, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<TypeSource>, P> visitor) {
        return newStage(this.builder.withDirtyFallback(visitor));
    }

    @Override
    public TypeSourceBuilder<V, P> filter(final Predicate<ExtendedElement> predicate) {
        return newStage(this.builder.filter(predicate));
    }

    @Override
    public TypeSourceBuilder<V, P> filterIfAnnotationPresent(
            final Class<? extends Annotation> annotationClass) {
        return newStage(this.builder.filterIfAnnotationPresent(annotationClass));
    }

    @Override
    public TypeSourceBuilder<V, P> filterIfAnyAnnotationPresent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newStage(this.builder.filterIfAnyAnnotationPresent(annotationClasses));
    }

}
