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
package org.rookit.convention.guice.source;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import one.util.streamex.StreamEx;
import org.rookit.auto.config.ProcessorConfig;
import org.rookit.auto.javapoet.SourceJavaPoetLibModule;
import org.rookit.auto.javax.JavaxLibModule;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.SourceLibModule;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.convention.ConventionModule;
import org.rookit.convention.annotation.LaConvention;
import org.rookit.convention.auto.ConventionAutoLibModule;
import org.rookit.convention.auto.source.type.ConventionTypeElementTypeSourceVisitors;
import org.rookit.convention.guice.source.naming.NamingModule;
import org.rookit.failsafe.FailsafeModule;
import org.rookit.guice.auto.Guice;
import org.rookit.guice.auto.GuiceAutoLibModule;
import org.rookit.guice.auto.config.GuiceConfig;
import org.rookit.io.IOLibModule;
import org.rookit.io.IOPathLibModule;
import org.rookit.serializer.SerializationBundleModule;
import org.rookit.utils.guice.UtilsModule;

import java.lang.annotation.Annotation;
import java.util.Set;

public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.override(
            JavaxLibModule.getModule()
    ).with(
            ConventionAutoLibModule.getModule(),
            ConventionModule.getModule(),
            FailsafeModule.getModule(),
            GuiceAutoLibModule.getModule(),
            IOLibModule.getModule(),
            NamingModule.getModule(),
            IOPathLibModule.getModule(),
            SerializationBundleModule.getModule(),
            SourceJavaPoetLibModule.getModule(),
            SourceLibModule.getModule(),
            UtilsModule.getModule(),
            new SourceModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

    @Override
    protected void configure() {
        bind(ProcessorConfig.class).to(GuiceConfig.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    ExtendedElementVisitor<StreamEx<TypeSource>, Void> typeElementVisitor(
            @Guice final ExtendedElementVisitor<StreamEx<TypeSource>, Void> baseVisitor,
            final ConventionTypeElementTypeSourceVisitors visitors,
            @LaConvention final Set<Class<? extends Annotation>> annotations) {

        return visitors.typeSourceBuilder(baseVisitor)
                .filterIfAnyAnnotationPresent(annotations)
                .withRecursiveVisiting(StreamEx::append)
                .build();
    }

}
