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
package org.rookit.convention.module.source.aggregator.property;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.convention.auto.property.aggregator.ExtendedPropertyAggregator;
import org.rookit.convention.auto.property.aggregator.ExtendedPropertyAggregatorFactory;
import org.rookit.convention.auto.source.type.reference.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.guice.MetaType;
import org.rookit.guice.auto.annotation.Guice;
import org.rookit.utils.primitive.VoidUtils;

import javax.annotation.processing.Messager;
import java.util.Collection;

final class ExtendedPropertyMultiAggregatorFactory implements ExtendedPropertyAggregatorFactory<
        Collection<MethodSource>> {

    private final MethodSourceFactory methodFactory;
    private final AnnotationSourceFactory annotationFactory;
    private final TypeParameterSourceFactory parameterFactory;
    private final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterVisitor;
    private final PropertyTypeReferenceSourceFactory propertyRefFactory;
    private final Messager messager;
    private final TypeReferenceSourceFactory apiReferenceFactory;
    private final TypeReferenceSourceFactory implReferenceFactory;
    private final PropertyFactory propertyFactory;
    private final VoidUtils voidUtils;
    private final TypeReferenceSource provides;
    private final TypeReferenceSource singleton;

    @Inject
    private ExtendedPropertyMultiAggregatorFactory(
            final MethodSourceFactory methodFactory,
            final AnnotationSourceFactory annotationFactory,
            final TypeParameterSourceFactory parameterFactory,
            @MetaType(includeAnnotations = true) final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterVisitor,
            @Guice final PropertyTypeReferenceSourceFactory propertyRefFactory,
            @MetaTypeAPI final TypeReferenceSourceFactory apiReferenceFactory,
            final Messager messager,
            @MetaType final TypeReferenceSourceFactory implReferenceFactory,
            final PropertyFactory propertyFactory,
            final VoidUtils voidUtils,
            @From(Provides.class) final TypeReferenceSource provides,
            @From(Singleton.class) final TypeReferenceSource singleton) {
        this.methodFactory = methodFactory;
        this.annotationFactory = annotationFactory;
        this.parameterFactory = parameterFactory;
        this.parameterVisitor = parameterVisitor;
        this.propertyRefFactory = propertyRefFactory;
        this.messager = messager;
        this.apiReferenceFactory = apiReferenceFactory;
        this.implReferenceFactory = implReferenceFactory;
        this.propertyFactory = propertyFactory;
        this.voidUtils = voidUtils;
        this.provides = provides;
        this.singleton = singleton;
    }

    @Override
    public ExtendedPropertyAggregator<Collection<MethodSource>> create(final ConventionTypeElement element) {
        return new ExtendedPropertyMultiMethodAggregator(
                this.methodFactory,
                this.annotationFactory,
                this.parameterVisitor,
                element,
                element.properties(),
                this.propertyFactory,
                this.propertyRefFactory,
                this.messager,
                this.apiReferenceFactory,
                this.implReferenceFactory,
                this.parameterFactory,
                this.voidUtils,
                this.provides,
                this.singleton
        );
    }

}
