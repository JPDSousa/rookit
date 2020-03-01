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
import com.google.inject.util.Modules;
import org.rookit.auto.javapoet.SourceJavaPoetLibModule;
import org.rookit.auto.javax.JavaxLibModule;
import org.rookit.auto.source.SourceLibModule;
import org.rookit.convention.ConventionModule;
import org.rookit.convention.auto.metatype.ConventionLibModule;
import org.rookit.convention.guice.source.config.ConfigModule;
import org.rookit.convention.guice.source.entity.EntityModule;
import org.rookit.convention.guice.source.javapoet.JavaPoetModule;
import org.rookit.convention.guice.source.javax.JavaxModule;
import org.rookit.convention.guice.source.naming.NamingModule;
import org.rookit.failsafe.FailsafeModule;
import org.rookit.guice.auto.GuiceAutoLibModule;
import org.rookit.guice.auto.annotation.config.ConfigurationModule;
import org.rookit.io.IOLibModule;
import org.rookit.io.PathLibModule;
import org.rookit.serializer.SerializationBundleModule;
import org.rookit.utils.guice.UtilsModule;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            ConfigModule.getModule(),
            ConfigurationModule.getModule(),
            ConventionLibModule.getModule(),
            ConventionModule.getModule(),
            EntityModule.getModule(),
            FailsafeModule.getModule(),
            GuiceAutoLibModule.getModule(),
            IOLibModule.getModule(),
            JavaPoetModule.getModule(),
            JavaxModule.getModule(),
            JavaxLibModule.getModule(),
            NamingModule.getModule(),
            PathLibModule.getModule(),
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

}
