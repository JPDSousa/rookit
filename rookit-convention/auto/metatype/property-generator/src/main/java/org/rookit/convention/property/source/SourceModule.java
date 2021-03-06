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
package org.rookit.convention.property.source;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.TypeProcessor;
import org.rookit.auto.javax.naming.MethodNameTransformer;
import org.rookit.auto.javax.naming.MethodNameTransformers;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.ConventionAutoLibModule;
import org.rookit.convention.auto.config.PropertyConfig;
import org.rookit.convention.property.source.config.ConfigurationModule;
import org.rookit.failsafe.FailsafeModule;
import org.rookit.io.IOPathLibModule;
import org.rookit.utils.guice.Self;
import org.rookit.utils.guice.UtilsModule;
import org.rookit.utils.string.template.Template1;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceModule extends AbstractModule {

    private static final Module BASE_MODULE = Modules.override(
            org.rookit.convention.guice.source.SourceModule.getModule()
    ).with(
            org.rookit.convention.api.source.SourceModule.getModule()
    );

    private static final Module MODULE = Modules.override(
            BASE_MODULE
    ).with(
            new SourceModule(),
            ConfigurationModule.getModule(),
            ConventionAutoLibModule.getModule(),
            FailsafeModule.getModule(),
            IOPathLibModule.getModule(),
            UtilsModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

    @Override
    protected void configure() {
        bind(TypeProcessor.class).to(PropertyTypeProcessor.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    MethodNameTransformer namingFactory(final MethodNameTransformers factories, @Self final Template1 noopTemplate) {
        return factories.fromTemplate(noopTemplate);
    }

    @Provides
    @Singleton
    TypeVariableSource typeVariableName(final PropertyConfig config) {
        return config.parameterName();
    }
}
