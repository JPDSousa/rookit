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
package org.rookit.storage.query.source;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.naming.IdentifierFactories;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.naming.NamingFactories;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.storage.api.config.QueryConfig;
import org.rookit.storage.guice.ElementQuery;
import org.rookit.storage.guice.PartialQuery;
import org.rookit.storage.guice.Query;
import org.rookit.storage.guice.filter.PartialFilter;
import org.rookit.storage.query.source.config.ConfigurationModule;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.override(
            org.rookit.storage.filter.source.SourceModule.getModule()
    ).with(
            new SourceModule(),
            ConfigurationModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

    @Override
    protected void configure() {

        bind(SingleTypeSourceFactory.class).annotatedWith(PartialQuery.class).to(QueryPartialTypeSourceFactory.class)
                .in(Singleton.class);
    }

    @Singleton
    @Provides
    @PartialQuery
    ExtendedPackageElement queryPackage(final QueryConfig config) {
        return config.basePackage();
    }

    @Provides
    @Singleton
    @PartialQuery
    TypeVariableSource typeVariableName(final QueryConfig config) {
        return config.parameterName();
    }

    @Provides
    @Singleton
    @ElementQuery
    TypeVariableSource elementTypeVariableName(final QueryConfig config) {
        return config.elementParameterName();
    }

    @Provides
    @PartialQuery
    @Singleton
    IdentifierFactory queryIdentifier(final IdentifierFactories factories,
                                      @PartialQuery final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

    @Provides
    @Query
    @Singleton
    IdentifierFactory queryEntityIdentifier(final IdentifierFactories factories,
                                            @Query final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

    @Singleton
    @Provides
    @PartialQuery
    NamingFactory queryNamingFactory(final NamingFactories factories,
                                     @PartialQuery final ExtendedPackageElement packageElement,
                                     final QueryConfig config) {
        return factories.create(packageElement, config.entityTemplate(), config.methodTemplate());
    }

    @Singleton
    @Provides
    @Query
    NamingFactory queryEntityNamingFactory(final NamingFactories factories,
                                           @PartialQuery final ExtendedPackageElement packageElement,
                                           final QueryConfig config) {
        // TODO should this have the exact same binding as the one above??????
        return factories.create(packageElement, config.entityTemplate(), config.methodTemplate());
    }

    @Provides
    @Singleton
    @PartialFilter
    TypeVariableSource filterTypeVariableName(final QueryConfig config) {
        return config.parameterName();
    }

}
