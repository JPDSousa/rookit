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
package org.rookit.auto.javax.runtime;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.runtime.annotation.AnnotationModule;
import org.rookit.auto.javax.runtime.element.ElementModule;
import org.rookit.auto.javax.runtime.mirror.MirrorModule;
import org.rookit.auto.javax.runtime.modifier.ModifierModule;
import org.rookit.auto.javax.runtime.name.NameModule;
import org.rookit.auto.javax.runtime.pack.PackageModule;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.SourceVersion.latestSupported;

public final class JavaxRuntimeModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new JavaxRuntimeModule(),
            AnnotationModule.getModule(),
            ElementModule.getModule(),
            NameModule.getModule(),
            MirrorModule.getModule(),
            ModifierModule.getModule(),
            PackageModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private JavaxRuntimeModule() {}

    @Override
    protected void configure() {
        bind(Elements.class).to(RuntimeElements.class).in(Singleton.class);
        bind(Types.class).to(RuntimeTypes.class).in(Singleton.class);
        bind(ProcessingEnvironment.class).to(RuntimeProcessingEnvironment.class).in(Singleton.class);
        bind(SourceVersion.class).toInstance(latestSupported());
    }

}
