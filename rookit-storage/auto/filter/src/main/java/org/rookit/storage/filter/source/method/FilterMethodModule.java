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
package org.rookit.storage.filter.source.method;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.metatype.javax.type.filter.TypeFilter;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitors;
import org.rookit.convention.auto.metatype.javax.visitor.TypeBasedMethodVisitor;
import org.rookit.convention.auto.metatype.property.Property;
import org.rookit.convention.auto.metatype.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.filter.source.guice.Type;
import org.rookit.storage.filter.source.method.annotation.MethodAnnotationModule;
import org.rookit.storage.filter.source.method.type.TypeMethodFactoryModule;
import org.rookit.storage.guice.FallbackFilter;
import org.rookit.storage.guice.TopFilter;
import org.rookit.storage.guice.filter.Filter;
import org.rookit.storage.guice.filter.PartialFilter;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("MethodMayBeStatic")
public final class FilterMethodModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(new FilterMethodModule(),
            MethodAnnotationModule.getModule(),
            TypeMethodFactoryModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private FilterMethodModule() {}

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
}
