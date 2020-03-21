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

import com.google.inject.Inject;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.guice.GuiceBindAnnotation;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.javax.visitor.GenericBuilder;
import org.rookit.auto.javax.visitor.StreamExBuilder;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.ExtendedElementTypeSourceVisitors;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceBuilder;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.annotation.AnnotationBuilder;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.inter.face.InterfaceBuilder;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.utils.string.StringUtils;

import javax.lang.model.element.Name;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

final class ExtendedElementTypeSourceVisitorsImpl implements ExtendedElementTypeSourceVisitors {

    private final MethodSourceFactory methodFactory;
    private final TypeSourceFactory typeFactory;
    private final ExtendedElementVisitors delegate;
    private final Collection<AnnotationSource> bindingAnnotations;
    private final IdentifierTransformer idTransformer;
    private final StringUtils stringUtils;
    private final TypeReferenceSourceFactory referenceFactory;

    @Inject
    private ExtendedElementTypeSourceVisitorsImpl(
            final MethodSourceFactory methodFactory,
            final TypeSourceFactory typeFactory,
            final ExtendedElementVisitors delegate,
            @GuiceBindAnnotation final Set<AnnotationSource> bindingAnnotations,
            final IdentifierTransformer idTransformer,
            final StringUtils stringUtils,
            final TypeReferenceSourceFactory referenceFactory) {

        this.methodFactory = methodFactory;
        this.typeFactory = typeFactory;
        this.delegate = delegate;
        this.bindingAnnotations = bindingAnnotations;
        this.idTransformer = idTransformer;
        this.stringUtils = stringUtils;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> TypeSourceBuilder<V, P> typeSourceBuilder(
            final V visitor,
            final Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter) {

        return new TypeSourceBuilderImpl<>(this.typeFactory,
                                           this.methodFactory,
                                           this.delegate.streamExBuilder(visitor, downcastAdapter),
                                           this.delegate,
                                           downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> AnnotationBuilder<V, P>
    annotationBuilder(
            final Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter) {

        final ExtendedElementVisitor<StreamEx<TypeSource>, P> baseVisitor
                = this.delegate.streamEx(new BaseTypeSourceBuilderVisitor<>(
                this.referenceFactory,
                this.idTransformer,
                this.stringUtils,
                this.typeFactory::createMutableAnnotation
        ));

        return new AnnotationBuilderImpl<>(typeSourceBuilder(downcastAdapter.apply(baseVisitor), downcastAdapter),
                                           this.bindingAnnotations);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> AnnotationBuilder<V, P>
    annotationBuilder(
            final Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter,
            final Class<P> parameterClass) {

        return annotationBuilder(downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> InterfaceBuilder<V, P> interfaceBuilder(
            final Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter,
            final Class<P> parameterClass) {

        final ExtendedElementVisitor<StreamEx<TypeSource>, P> baseVisitor
                = this.delegate.streamEx(new BaseTypeSourceBuilderVisitor<>(
                this.referenceFactory,
                this.idTransformer,
                this.stringUtils,
                this.typeFactory::createMutableInterface
        ));

        return new InterfaceBuilderImpl<>(typeSourceBuilder(downcastAdapter.apply(baseVisitor), downcastAdapter));
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
            final Function<ExtendedElementVisitor<R, P>, V> downcastAdapter) {

        return this.delegate.builder(visitor, downcastAdapter);
    }

    @Override
    public <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            final Provider<V> visitor,
            final Function<ExtendedElementVisitor<R, P>, V> downcastAdapter) {

        return this.delegate.builder(visitor, downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            final V visitor,
            final Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {

        return this.delegate.streamExBuilder(visitor, downcastAdapter);
    }

    @Override
    public <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            final Iterable<? extends V> visitors,
            final Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter) {

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

}
