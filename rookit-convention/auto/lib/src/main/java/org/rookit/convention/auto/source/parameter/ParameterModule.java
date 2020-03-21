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
package org.rookit.convention.auto.source.parameter;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.property.guice.PropertyModel;

import java.util.Set;

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
    @PropertyModel
    ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> propertyParameterFactory(
            final ConventionParameterVisitors elementVisitors,
            @PropertyModel final Set<ConventionTypeElementVisitor<
                                StreamEx<ParameterSource>, Void>> visitors) {
        return elementVisitors.streamExConventionBuilder(visitors).build();
    }
}
