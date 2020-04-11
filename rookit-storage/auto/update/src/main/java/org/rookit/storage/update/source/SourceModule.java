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
package org.rookit.storage.update.source;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.storage.AutoStorageLibModule;
import org.rookit.storage.api.config.UpdateConfig;
import org.rookit.storage.guice.PartialUpdate;
import org.rookit.storage.guice.Update;
import org.rookit.storage.update.source.config.ConfigurationModule;

@SuppressWarnings("MethodMayBeStatic")
public final class SourceModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new SourceModule(),
            AutoStorageLibModule.getModule(),
            ConfigurationModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private SourceModule() {}

    @Provides
    @Singleton
    @PartialUpdate
    TypeVariableSource typeVariableName(final UpdateConfig config) {
        return config.parameterName();
    }

    @Singleton
    @Provides
    @Update
    ExtendedPackageElement updatePackage(final UpdateConfig config) {
        return config.basePackage();
    }
}
