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
package org.rookit.storage.method;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.convention.auto.javax.adapter.ConventionTypeAdapters;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitors;
import org.rookit.convention.auto.javax.visitor.TypeBasedMethodVisitor;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.convention.auto.source.ConventionAutoFactories;
import org.rookit.convention.auto.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.api.config.UpdateConfig;
import org.rookit.storage.guice.FallbackFilter;
import org.rookit.storage.guice.FallbackUpdate;
import org.rookit.storage.guice.FilterEntity;
import org.rookit.storage.guice.FilterProperty;
import org.rookit.storage.guice.PartialUpdate;
import org.rookit.storage.guice.TopFilter;
import org.rookit.storage.guice.TopUpdate;
import org.rookit.storage.guice.Type;
import org.rookit.storage.guice.Update;
import org.rookit.storage.guice.UpdateEntity;
import org.rookit.storage.guice.UpdateProperty;
import org.rookit.storage.guice.filter.Filter;
import org.rookit.storage.guice.filter.PartialFilter;
import org.rookit.storage.method.type.TypeModule;
import org.rookit.utils.adapt.Adapter;
import org.rookit.utils.guice.Mutable;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class MethodModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new MethodModule(),
            TypeModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private MethodModule() {}

    @Singleton
    @Provides
    @TopFilter
    ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> topFilterFactory(
            final ConventionTypeElementVisitors visitors,
            @Filter final ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> annotationFactory,
            @Type final ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> typeVisitor,
            @Filter final Predicate<Property> annotationPredicate) {
        return visitors.streamExConventionBuilder(annotationFactory)
                .routeThroughFilter(typeVisitor, annotationPredicate)
                .build();
    }

    @Provides
    @Singleton
    @Type
    ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> typeFactory(
            final ConventionTypeElementVisitors visitors,
            @Filter final Set<TypeBasedMethodVisitor<Void>> typeVisitors,
            @FallbackFilter final ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> genericMethodVisitor,
            @Type final Predicate<Property> typeFilter) {
        return visitors.streamExConventionBuilder(typeVisitors)
                .routeThroughFilter(genericMethodVisitor, typeFilter)
                .build();
    }

    @Provides
    @Singleton
    @Type
    Predicate<Property> typeFilter(
            @Filter final Set<TypeBasedMethodVisitor<Void>> typeFactories) {
        return TypeFilter.create(typeFactories);
    }

    @Singleton
    @Provides
    @FallbackFilter
    ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> fallbackFilter(
            final ConventionTypeElementMethodSourceVisitors visitors,
            @PartialFilter final MethodSourceFactory factory,
            @Self final Template1 noopTemplate) {
        return visitors.<Void>templateMethodSourceVisitorBuilder(factory, noopTemplate)
                .build();
    }

    @Singleton
    @Provides
    @FilterEntity
        // TODO so much stuff in here. Please break it down
    ExtendedElementVisitor<StreamEx<MethodSource>, Void> entityMethodFactory(
            final ConventionTypeElementMethodSourceVisitors visitors,
            final ConventionAutoFactories javaPoetFactories,
            final ConventionTypeAdapters conventionAdapters,
            @PartialFilter final MethodSourceFactory methodFactory,
            final ConventionTypeElementFactory elementFactory,
            final PropertyFactory propertyFactory) {
        final Predicate<Property> propertyFilter = ConventionPropertyFilters.createEntityFilter(propertyFactory);
        final BiFunction<ConventionTypeElement, Property, MethodSource> typeTransformation =
                javaPoetFactories.createTypeTransformation(methodFactory);

        return visitors.<MethodSource, Void>createPropertyLevelVisitor(typeTransformation)
                .withConventionTypeAdapter(conventionAdapters
                                                   .createPropertyFilterAdapter(propertyFilter, elementFactory))
                .build();
    }

    @Singleton
    @Provides
    @Filter
    ExtendedElementVisitor<StreamEx<MethodSource>, Void> genericAnnotationMethodFactory(
            final ExtendedElementVisitors visitors,
            @FilterEntity final ExtendedElementVisitor<StreamEx<MethodSource>, Void> entityFactory,
            @FilterProperty final ExtendedElementVisitor<StreamEx<MethodSource>, Void> propertyFactory) {
        return visitors.streamExBuilder(entityFactory)
                .withDirtyFallback(propertyFactory)
                .build();
    }


    @Singleton
    @Provides
    @FallbackUpdate
    ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> fallbackUpdate(
            final ConventionTypeElementMethodSourceVisitors visitors,
            @PartialUpdate final MethodSourceFactory methodFactory,
            final UpdateConfig config,
            @Mutable final Adapter<ConventionTypeElement> adapter) {
        // TODO inject me
        return visitors.<Void>templateMethodSourceVisitorBuilder(methodFactory, config.methodTemplate())
                .withConventionTypeAdapter(adapter)
                .build();
    }

    @Singleton
    @Provides
    @Update
    ExtendedElementVisitor<StreamEx<MethodSource>, Void> genericAnnotationUpdateMethodFactory(
            final ExtendedElementVisitors visitors,
            @UpdateEntity final ExtendedElementVisitor<StreamEx<MethodSource>, Void> entityFactory,
            @UpdateProperty final ExtendedElementVisitor<StreamEx<MethodSource>, Void> propertyFactory) {
        return visitors.streamExBuilder(entityFactory)
                .withDirtyFallback(propertyFactory)
                .build();
    }

    @Singleton
    @Provides
    @UpdateEntity
    ExtendedElementVisitor<StreamEx<MethodSource>, Void> entityMethodFactory(
            final ConventionTypeElementVisitors visitors,
            final ConventionAutoFactories factories,
            @PartialUpdate final MethodSourceFactory methodFactory) {
        return visitors.<MethodSource, Void>createPropertyLevelVisitor(
                factories.createTypeTransformation(methodFactory))
                .build();
    }

}
