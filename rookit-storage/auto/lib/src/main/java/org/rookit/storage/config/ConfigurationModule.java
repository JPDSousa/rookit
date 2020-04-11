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
package org.rookit.storage.config;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.rookit.auto.config.AutoConfig;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.storage.api.config.FilterConfig;
import org.rookit.storage.api.config.QueryConfig;
import org.rookit.storage.api.config.StorageConfig;
import org.rookit.storage.api.config.UpdateConfig;
import org.rookit.storage.api.config.UpdateFilterConfig;
import org.rookit.utils.object.DynamicObject;
import org.rookit.utils.string.template.Template1;
import org.rookit.utils.string.template.TemplateFactory;

@SuppressWarnings("MethodMayBeStatic")
public final class ConfigurationModule extends AbstractModule {

    private static final Module MODULE = new ConfigurationModule();

    public static Module getModule() {
        return MODULE;
    }

    private ConfigurationModule() {}

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    static StorageConfig storageConfig(final AutoConfig config,
                                       final ExtendedPackageElementFactory referenceFactory,
                                       final TemplateFactory templateFactory) {
        final String name = "storage";
        return new StorageConfigImpl(config.getProcessorConfig(name), referenceFactory, name, templateFactory);
    }

    @Provides
    @Singleton
    FilterConfig filterConfig(final StorageConfig config,
                              final TemplateFactory templateFactory,
                              final TypeVariableSourceFactory typeVariableFactory) {

        final String name = "filter";
        return new FilterConfigImpl(
                config.getProcessorConfig(name),
                config.basePackage(),
                config.partialEntityTemplate(),
                name,
                templateFactory,
                typeVariableFactory
        );
    }

    @Provides
    @Singleton
    QueryConfig config(final StorageConfig config,
                       final TemplateFactory templateFactory,
                       final TypeVariableSourceFactory typeVariableFactory) {

        final String name = "query";
        return new QueryConfigImpl(
                config.getProcessorConfig(name),
                config.basePackage(),
                config.partialEntityTemplate(),
                name,
                templateFactory,
                typeVariableFactory
        );
    }

    @Provides
    @Singleton
    UpdateFilterConfig updateFilterConfig(final StorageConfig config,
                                          final TemplateFactory templateFactory,
                                          final TypeVariableSourceFactory typeVariableFactory) {
        final String name = "updateFilter";
        final DynamicObject rawConfig = config.getProcessorConfig(name);
        final Template1 pEntityTemplate = config.partialEntityTemplate();
        return new UpdateFilterConfigImpl(
                rawConfig,
                config.basePackage(),
                pEntityTemplate,
                name,
                templateFactory,
                typeVariableFactory
        );
    }


    @Provides
    @Singleton
    UpdateConfig updateConfig(final StorageConfig config,
                              final TemplateFactory templateFactory,
                              final TypeVariableSourceFactory typeVariableFactory) {

        final String name = "update";
        final DynamicObject rawConfig = config.getProcessorConfig(name);
        final Template1 partialEntityPrefix = config.partialEntityTemplate();
        return new UpdateConfigImpl(
                rawConfig,
                name,
                partialEntityPrefix,
                config.basePackage(),
                templateFactory,
                typeVariableFactory
        );
    }
}
