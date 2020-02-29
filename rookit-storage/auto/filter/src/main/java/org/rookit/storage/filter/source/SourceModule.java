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
package org.rookit.storage.filter.source;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.IdentifierFactories;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.convention.auto.source.type.ConventionSingleTypeSourceFactories;
import org.rookit.storage.api.config.FilterConfig;
import org.rookit.storage.filter.source.config.ConfigurationModule;
import org.rookit.storage.filter.source.method.FilterMethodModule;
import org.rookit.storage.filter.source.naming.NamingModule;
import org.rookit.storage.guice.TopFilter;
import org.rookit.storage.guice.filter.Filter;
import org.rookit.storage.guice.filter.PartialFilter;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new SourceModule(),
            ConfigurationModule.getModule(),
            FilterMethodModule.getModule(),
            NamingModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

    @Provides
    @Singleton
    @PartialFilter
    TypeVariableSource typeVariableName(final FilterConfig config) {
        return config.parameterName();
    }

    @Provides
    @PartialFilter
    @Singleton
    IdentifierFactory filterIdentifier(final IdentifierFactories factories,
                                       @PartialFilter final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

    @Provides
    @Filter
    @Singleton
    IdentifierFactory filterEntityIdentifier(final IdentifierFactories factories,
                                             @Filter final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

    @Singleton
    @Provides
    @PartialFilter
    SingleTypeSourceFactory filterTypeSourceFactory(
            final ConventionSingleTypeSourceFactories factories,
            final TypeVariableSourceFactory parameterResolver,
            @TopFilter final ExtendedElementVisitor<StreamEx<MethodSource>, Void> specFactory,
            final ConventionTypeElementFactory elementFactory) {
        return factories.propertyBasedTypeSourceFactory(parameterResolver, specFactory, elementFactory);
    }

}
