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
package org.rookit.auto;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.config.ConfigurationModule;
import org.rookit.auto.javax.naming.NamingFactories;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.source.SourceModule;
import org.rookit.utils.guice.Dummy;
import org.rookit.utils.guice.Self;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.util.concurrent.Executor;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceUtilsModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new SourceUtilsModule(),
            ConfigurationModule.getModule(),
            SourceModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceUtilsModule() {}

    @Override
    protected void configure() {
        bind(Filer.class).to(IdempotentFiler.class).in(Singleton.class);
        bind(JavaFileObject.class).annotatedWith(Dummy.class).to(DummyJavaFileObject.class).in(Singleton.class);

        bind(TypeProcessor.class).to(StatelessTypeProcessor.class).in(Singleton.class);

        bind(Executor.class).toInstance(MoreExecutors.directExecutor());
    }

    @Singleton
    @Provides
    @Self
    NamingFactory selfNamingFactory(final NamingFactories factories) {
        return factories.selfFactory();
    }

}
