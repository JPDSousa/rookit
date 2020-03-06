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
package org.rookit.storage.filter.source.method.annotation;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.source.ConventionAutoFactories;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.convention.auto.javax.type.adapter.ConventionTypeAdapters;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.convention.auto.property.filter.ConventionPropertyFilters;
import org.rookit.convention.auto.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.guice.FilterEntity;
import org.rookit.storage.guice.FilterProperty;
import org.rookit.storage.guice.filter.Filter;
import org.rookit.storage.guice.filter.PartialFilter;

import java.util.function.BiFunction;
import java.util.function.Predicate;

@SuppressWarnings("MethodMayBeStatic")
public final class MethodAnnotationModule extends AbstractModule {

    private static final Module MODULE = new MethodAnnotationModule();

    public static Module getModule() {
        return MODULE;
    }

    private MethodAnnotationModule() {}

    @Override
    protected void configure() {

    }

    @Singleton
    @Provides
    @FilterEntity
    // TODO so much stuff in here. Please break it down
    ExtendedElementVisitor<StreamEx<MethodSource>, Void> entityMethodFactory(
            final ConventionTypeElementMethodSourceVisitors visitors,
            final ConventionAutoFactories javaPoetFactories,
            final ConventionTypeAdapters conventionAdapters,
            @PartialFilter final MethodSourceFactory MethodSourceFactory,
            final ConventionTypeElementFactory elementFactory,
            final PropertyFactory propertyFactory) {
        final Predicate<Property> propertyFilter = ConventionPropertyFilters.createEntityFilter(propertyFactory);
        final BiFunction<ConventionTypeElement, Property, StreamEx<MethodSource>> typeTransformation =
                javaPoetFactories.createTypeTransformation(MethodSourceFactory);

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
}
