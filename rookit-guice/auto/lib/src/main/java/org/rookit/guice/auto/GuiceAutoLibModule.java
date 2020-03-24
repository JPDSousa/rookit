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
package org.rookit.guice.auto;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.naming.IdentifierTransformers;
import org.rookit.auto.javax.naming.MethodNameTransformer;
import org.rookit.auto.javax.naming.MethodNameTransformers;
import org.rookit.guice.auto.aggregator.AggregatorModule;
import org.rookit.guice.auto.annotation.AnnotationModule;
import org.rookit.guice.auto.bind.BindModule;
import org.rookit.guice.auto.config.ConfigModule;
import org.rookit.guice.auto.config.GuiceConfig;
import org.rookit.guice.auto.module.ModuleModule;
import org.rookit.guice.auto.source.SourceModule;
import org.rookit.utils.adapt.Adapter;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

@SuppressWarnings("MethodMayBeStatic")
public final class GuiceAutoLibModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new GuiceAutoLibModule(),
            AggregatorModule.getModule(),
            AnnotationModule.getModule(),
            BindModule.getModule(),
            ConfigModule.getModule(),
            ModuleModule.getModule(),
            SourceModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private GuiceAutoLibModule() {}

    @Provides
    @Singleton
    @Guice
    MethodNameTransformer namingFactory(final MethodNameTransformers factories, @Self final Template1 noopTemplate) {
        return factories.fromTemplate(noopTemplate);
    }

    @Provides
    @Singleton
    @Guice
    IdentifierTransformer identifierTransformer(final IdentifierTransformers transformers,
                                                final GuiceConfig config) {

        return transformers.fromFunctions(
                reference -> reference.resolve(config.basePackage()),
                Adapter.identity()
        ) ;
    }
}
