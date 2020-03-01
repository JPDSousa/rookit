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
package org.rookit.convention.auto.metatype.javapoet.parameter;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.parameter.ParameterVisitors;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.javax.naming.PropertyIdentifierFactory;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitors;
import org.rookit.convention.auto.metatype.property.Property;
import org.rookit.convention.auto.metatype.source.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.auto.metatype.source.parameter.ConventionParameterVisitors;
import org.rookit.convention.guice.MetaType;
import org.rookit.convention.property.guice.PropertyModel;
import org.rookit.guice.auto.annotation.Guice;

import java.util.Set;
import java.util.function.BiFunction;

@SuppressWarnings("MethodMayBeStatic")
public final class ParameterModule extends AbstractModule {

    private static final Module MODULE = new ParameterModule();

    public static Module getModule() {
        return MODULE;
    }

    private ParameterModule() {}

    @Override
    protected void configure() {
        bind(ConventionParameterVisitors.class).to(ConventionParameterVisitorsImpl.class).in(Singleton.class);
        bindBaseParameterVisitor();
    }

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    private void bindBaseParameterVisitor() {
        final Multibinder<ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void>> mBinder = Multibinder
                .newSetBinder(binder(), new TypeLiteral<ConventionTypeElementVisitor<
                        StreamEx<ParameterSource>, Void>>() {}, PropertyModel.class);
        mBinder.addBinding().toProvider(EmptyParameterProvider.class);
    }

    @Provides
    @Singleton
    @MetaType(includeAnnotations = true)
    BiFunction<Property, ParameterSource, ParameterSource> conversionFunction(
            @Guice final PropertyIdentifierFactory identifierFactory,
            final ParameterSourceFactory parameterFactory,
            final AnnotationSourceFactory annotationFactory) {
        return new MetaTypeParameterWithAnnotationsResultAccumulator(identifierFactory,
                                                                     parameterFactory,
                                                                     annotationFactory);
    }


    @Provides
    @Singleton
    @MetaType
    ExtendedElementVisitor<StreamEx<ParameterSource>, Void> createMetaTypeFactory(
            final ConventionTypeElementVisitors visitors,
            @MetaType final BiFunction<ConventionTypeElement, Property, StreamEx<ParameterSource>> transformation) {
        return visitors.<ParameterSource, Void>createPropertyLevelVisitor(transformation)
                .build();
    }

    @Provides
    @Singleton
    @MetaType(includeAnnotations = true)
    ExtendedElementVisitor<StreamEx<ParameterSource>, Void> createAnnotatedExtendedParameterFactory(
            final ParameterVisitors visitors,
            @MetaType(includeAnnotations = true)
            final ExtendedElementVisitor<StreamEx<ParameterSource>, Void> visitor) {
        return visitors.parameterBuilder(visitor)
                .build();
    }


    @Provides
    @Singleton
    @MetaType(includeAnnotations = true)
    ExtendedElementVisitor<StreamEx<ParameterSource>, Void> createAnnotatedMetaTypeFactory(
            final ConventionTypeElementVisitors visitors,
            @MetaType(includeAnnotations = true) final BiFunction<ConventionTypeElement, Property,
                    StreamEx<ParameterSource>> transformation) {
        return visitors.<ParameterSource, Void>createPropertyLevelVisitor(transformation)
                .build();
    }

    @Provides
    @Singleton
    @MetaType
    BiFunction<ConventionTypeElement, Property, StreamEx<ParameterSource>> baseTransformation(
            @MetaType final PropertyTypeReferenceSourceFactory factory,
            final ParameterSourceFactory parameterFactory) {
        return new MetaTypeParameterTransformation(
                factory,
                (property, parameter) -> parameter,
                parameterFactory);
    }

    @Provides
    @Singleton
    @MetaType(includeAnnotations = true)
    BiFunction<ConventionTypeElement, Property, StreamEx<ParameterSource>> annotationTransformation(
            @MetaType final PropertyTypeReferenceSourceFactory factory,
            @MetaType(includeAnnotations = true) final BiFunction<Property,
                    ParameterSource, ParameterSource> resultAccumulator,
            final ParameterSourceFactory parameterFactory) {
        return new MetaTypeParameterTransformation(
                factory,
                resultAccumulator,
                parameterFactory);
    }

    @Provides
    @Singleton
    @PropertyModel
    ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> propertyParameterFactory(
            final ConventionParameterVisitors elementVisitors,
            @PropertyModel final Set<ConventionTypeElementVisitor<
                                StreamEx<ParameterSource>, Void>> visitors) {
        return elementVisitors.streamExConventionBuilder(visitors).build();
    }
}
