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
package org.rookit.storage.method.type.optional;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.guice.Any;
import org.rookit.storage.guice.No;
import org.rookit.storage.guice.Optional;
import org.rookit.storage.guice.Remove;
import org.rookit.utils.string.template.Template1;
import org.rookit.utils.string.template.TemplateFactory;

@SuppressWarnings("MethodMayBeStatic")
public final class OptionalModule extends AbstractModule {

    private static final Module MODULE = new OptionalModule();

    public static Module getModule() {
        return MODULE;
    }

    private OptionalModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        final Multibinder<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> optionalMFactories = Multibinder
                .newSetBinder(binder(), new TypeLiteral<ConventionTypeElementVisitor<StreamEx<MethodSource>,
                                      Void>>() {}, Optional.class);
        optionalMFactories.addBinding().toProvider(NoneMethodFactoryProvider.class);
        optionalMFactories.addBinding().toProvider(SomeMethodFactoryProvider.class);

        bind(new TypeLiteral<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>>() {})
                .annotatedWith(Optional.class)
                .toProvider(OptionalMethodFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @No
    Template1 noTemplate(final TemplateFactory templateFactory) {
        return templateFactory.template1("no{}");
    }

    @Provides
    @Singleton
    @Any
    Template1 anyTemplate(final TemplateFactory templateFactory) {
        return templateFactory.template1("any{}");
    }

    @Provides
    @Singleton
    @Remove
    ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> removeMethodFactory(
            final ConventionTypeElementMethodSourceVisitors visitors,
            final MethodSourceFactory methodFactory,
            @Remove final Template1 template) {
        return visitors.<Void>templateMethodSourceVisitorBuilder(methodFactory, template)
                .build();
    }
}
