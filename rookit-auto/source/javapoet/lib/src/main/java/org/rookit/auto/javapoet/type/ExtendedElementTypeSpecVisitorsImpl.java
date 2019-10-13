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
package org.rookit.auto.javapoet.type;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.guice.GuiceBindAnnotation;
import org.rookit.auto.javapoet.identifier.JavaPoetIdentifierFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.javax.visitor.GenericBuilder;
import org.rookit.auto.javax.visitor.StreamExBuilder;

import javax.lang.model.element.Name;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

final class ExtendedElementTypeSpecVisitorsImpl implements ExtendedElementTypeSpecVisitors {

    private final ExtendedElementVisitors delegate;
    private final Collection<AnnotationSpec> bindingAnnotations;
    private final Types types;

    @Inject
    private ExtendedElementTypeSpecVisitorsImpl(final ExtendedElementVisitors delegate,
                                                @GuiceBindAnnotation final Set<AnnotationSpec> bindingAnnotations,
                                                final Types types) {
        this.delegate = delegate;
        this.bindingAnnotations = bindingAnnotations;
        this.types = types;
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> TypeSpecBuilder<V, P> typeSpecBuilder(
            final V visitor,
            final Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter) {
        return new TypeSpecBuilderImpl<>(this.delegate.streamExBuilder(visitor, downcastAdapter), this.delegate,
                                         this.types,
                                         downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> AnnotationBuilder<V, P>
    annotationBuilder(
            final JavaPoetIdentifierFactory identifierFactory,
            final Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter) {
        final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> baseVisitor
                = this.delegate.streamEx(new BaseAnnotationBuilderVisitor<>(identifierFactory));
        return new AnnotationBuilderImpl<>(typeSpecBuilder(downcastAdapter.apply(baseVisitor), downcastAdapter),
                                           this.bindingAnnotations);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> AnnotationBuilder<V, P>
    annotationBuilder(
            final JavaPoetIdentifierFactory identifierFactory,
            final Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter,
            final Class<P> parameterClass) {
        return annotationBuilder(identifierFactory, downcastAdapter);
    }

    @Override
    public <P> ExtendedElementVisitor<Boolean, P> isPresent(final Class<? extends Annotation> annotationClass) {
        return this.delegate.isPresent(annotationClass);
    }

    @Override
    public <R, P> ExtendedElementVisitor<StreamEx<R>, P> emptyStreamVisitor() {
        return this.delegate.emptyStreamVisitor();
    }

    @Override
    public <P> ExtendedElementVisitor<Name, P> qualifiedNameVisitor() {
        return this.delegate.qualifiedNameVisitor();
    }

    @Override
    public <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            final V visitor,
            Function<ExtendedElementVisitor<R, P>, V> downcastAdapter) {
        return this.delegate.builder(visitor, downcastAdapter);
    }

    @Override
    public <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            final Provider<V> visitor,
            Function<ExtendedElementVisitor<R, P>, V> downcastAdapter) {
        return this.delegate.builder(visitor, downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            final V visitor,
            Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {
        return this.delegate.streamExBuilder(visitor, downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            final Iterable<? extends V> visitors,
            Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {
        return this.delegate.streamExBuilder(visitors, downcastAdapter);
    }

    @Override
    public <R, P> ExtendedElementVisitor<StreamEx<R>, P> streamEx(
            final Collection<? extends ExtendedElementVisitor<R, P>> visitors) {
        return this.delegate.streamEx(visitors);
    }

    @Override
    public <R, P> ExtendedElementVisitor<StreamEx<R>, P> streamEx(final ExtendedElementVisitor<R, P> visitor) {
        return this.delegate.streamEx(visitor);
    }

    @Override
    public String toString() {
        return "ExtendedElementTypeSpecVisitorsImpl{" +
                "delegate=" + this.delegate +
                ", bindingAnnotations=" + this.bindingAnnotations +
                ", types=" + this.types +
                "}";
    }
}
