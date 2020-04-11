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
package org.rookit.storage.method.type.collection;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.guice.Collection;
import org.rookit.storage.guice.filter.Filter;
import org.rookit.utils.adapt.Adapter;

import java.util.Set;
import java.util.function.Function;

public final class CollectionModule extends AbstractModule {

    private static final Module MODULE = new CollectionModule();

    public static Module getModule() {
        return MODULE;
    }

    private CollectionModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        final Multibinder<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> multibinder = Multibinder
                .newSetBinder(binder(), new TypeLiteral<ConventionTypeElementVisitor<
                                      StreamEx<MethodSource>, Void>>() {},
                        Filter.class);
        multibinder.addBinding().toProvider(CollectionMethodVisitor.class);

        final Multibinder<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> opMultiBinder = Multibinder
                .newSetBinder(binder(),
                              new TypeLiteral<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>>() {},
                              Collection.class);
        opMultiBinder.addBinding().toProvider(AddMethodFactoryProvider.class).in(Singleton.class);
        opMultiBinder.addBinding().toProvider(RemoveMethodFactoryProvider.class).in(Singleton.class);
        opMultiBinder.addBinding().toProvider(AddAllMethodFactoryProvider.class).in(Singleton.class);
        opMultiBinder.addBinding().toProvider(RemoveAllMethodFactoryProvider.class).in(Singleton.class);

        bind(new TypeLiteral<Function<Property, java.util.Collection<ParameterSource>>>() {})
                .annotatedWith(Collection.class)
                .to(CollectionParams.class);
    }

    @Provides
    @Singleton
    @Collection
    ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> collection(
            @Collection final Set<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> opFactories,
            @org.rookit.utils.guice.Collection(unwrap = true) final Adapter<ConventionTypeElement> collectionUnwrapper,
            final ConventionTypeElementMethodSourceVisitors visitors) {
        return visitors.streamExConventionBuilder(opFactories)
                .withConventionTypeAdapter(collectionUnwrapper)
                .build();
    }
}
