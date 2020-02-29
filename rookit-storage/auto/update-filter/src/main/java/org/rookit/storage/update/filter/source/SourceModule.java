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
package org.rookit.storage.update.filter.source;

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
import org.rookit.storage.api.config.UpdateFilterConfig;
import org.rookit.storage.guice.PartialUpdateFilter;
import org.rookit.storage.guice.UpdateFilter;
import org.rookit.storage.update.filter.source.config.ConfigurationModule;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new SourceModule(),
            ConfigurationModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

    @Override
    protected void configure() {
        bind(SingleTypeSourceFactory.class).annotatedWith(PartialUpdateFilter.class)
                .to(UpdateFilterPartialTypeSourceFactory.class).in(Singleton.class);
    }

    @Singleton
    @Provides
    @PartialUpdateFilter
    NamingFactory partialUpdateFilterNamingFactory(final NamingFactories factories,
                                                   @UpdateFilter final ExtendedPackageElement packageElement,
                                                   final UpdateFilterConfig config,
                                                   @Self final Template1 noopTemplate) {
        return factories.create(packageElement, config.entityTemplate(), noopTemplate);
    }

    @Singleton
    @Provides
    @UpdateFilter
    NamingFactory updateFilterNamingFactory(final NamingFactories factories,
                                            @UpdateFilter final ExtendedPackageElement packageElement,
                                            final UpdateFilterConfig config,
                                            @Self final Template1 noopTemplate) {
        return factories.create(packageElement, config.entityTemplate(), noopTemplate);
    }

    @Singleton
    @Provides
    @UpdateFilter
    ExtendedPackageElement updateFilterPackage(final UpdateFilterConfig config) {
        return config.basePackage();
    }

    @Provides
    @Singleton
    @PartialUpdateFilter
    TypeVariableSource parameterName(final UpdateFilterConfig config) {
        return config.parameterName();
    }

    @Singleton
    @Provides
    @UpdateFilter
    IdentifierFactory updateFilterIdentifierFactory(final IdentifierFactories factories,
                                                    @UpdateFilter final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

    @Singleton
    @Provides
    @PartialUpdateFilter
    IdentifierFactory partialUpdateFilterIdentifierFactory(
            final IdentifierFactories factories,
            @PartialUpdateFilter final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

}
