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
import com.google.inject.util.Modules;
import org.rookit.auto.SourceUtilsModule;
import org.rookit.auto.javapoet.SourceJavaPoetLibModule;
import org.rookit.auto.source.SourceLibModule;
import org.rookit.convention.ConventionModule;
import org.rookit.convention.auto.ConventionAutoLibModule;
import org.rookit.convention.auto.metatype.AutoMetaTypeModule;
import org.rookit.failsafe.FailsafeModule;
import org.rookit.guice.auto.GuiceAutoLibModule;
import org.rookit.io.IOLibModule;
import org.rookit.io.IOPathLibModule;
import org.rookit.serializer.SerializationBundleModule;
import org.rookit.storage.AutoStorageLibModule;
import org.rookit.storage.naming.NamingModule;
import org.rookit.storage.query.source.config.ConfigurationModule;
import org.rookit.utils.guice.UtilsModule;

public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new SourceModule(),
            AutoMetaTypeModule.getModule(),
            AutoStorageLibModule.getModule(),
            ConfigurationModule.getModule(),
            ConventionModule.getModule(),
            ConventionAutoLibModule.getModule(),
            FailsafeModule.getModule(),
            GuiceAutoLibModule.getModule(),
            IOLibModule.getModule(),
            NamingModule.getModule(),
            IOPathLibModule.getModule(),
            SerializationBundleModule.getModule(),
            SourceLibModule.getModule(),
            SourceJavaPoetLibModule.getModule(),
            SourceUtilsModule.getModule(),
            UtilsModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

}
