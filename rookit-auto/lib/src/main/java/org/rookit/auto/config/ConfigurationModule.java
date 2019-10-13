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
package org.rookit.auto.config;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.rookit.config.guice.Config;
import org.rookit.io.data.DataBucketFactory;
import org.rookit.io.data.DataSource;
import org.rookit.io.object.DataBucketDynamicObjectFactory;
import org.rookit.io.path.PathConfig;
import org.rookit.utils.object.DynamicObject;
import org.rookit.utils.optional.OptionalFactory;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

@SuppressWarnings("MethodMayBeStatic")
public final class ConfigurationModule extends AbstractModule {

    private static final Module MODULE = new ConfigurationModule();

    public static Module getModule() {
        return MODULE;
    }

    private ConfigurationModule() {}

    @Override
    protected void configure() {
        bind(AutoConfigFactory.class).to(AutoConfigFactoryImpl.class).in(Singleton.class);
        bind(Charset.class).annotatedWith(Config.class).to(Charset.class);
    }

    @Provides
    @Singleton
    @Config
    URI pathLocation(final Filer filer,
                     final OptionalFactory optionalFactory) throws IOException {
        final String fileName = "gen.json";
        return optionalFactory.ofNullable(filer.getResource(StandardLocation.CLASS_OUTPUT, "", fileName))
                .map(FileObject::toUri)
                .orElseThrow(() -> new IOException(String.format("Cannot find resource %s", fileName)));
    }

    @Provides
    @Singleton
    @Config
    DataSource configDataSource(@Config final URI configLocation,
                                final DataBucketFactory<URI> factory) {
        return factory.create(configLocation);
    }

    @Provides
    @Singleton
    @Config
    DynamicObject configDynamicObject(@Config final DataSource configLocation,
                                      final DataBucketDynamicObjectFactory dynamicObjectFactory) {
        return dynamicObjectFactory.fromDataSource(configLocation).blockingGet();
    }

    @Provides
    @Singleton
    AutoConfig topConfig(@Config final DynamicObject config, final AutoConfigFactory factory) {
        return factory.create(config);
    }

    @Provides
    @Singleton
    PathConfig pathConfig(final AutoConfig autoConfig) {
        return autoConfig.fileConfig();
    }

}
