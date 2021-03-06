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
package org.rookit.auto.javax.runtime.element;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.runtime.element.dependency.DependencyModule;
import org.rookit.auto.javax.runtime.element.executable.ExecutableModule;
import org.rookit.auto.javax.runtime.entity.kind.RuntimeElementKindFactory;
import org.rookit.auto.javax.runtime.element.node.NodeModule;
import org.rookit.auto.javax.runtime.element.type.TypeModule;
import org.rookit.auto.javax.runtime.element.variable.VariableModule;
import org.rookit.javax.runtime.element.Registries;

public final class ElementModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new ElementModule(),
            DependencyModule.getModule(),
            ExecutableModule.getModule(),
            NodeModule.getModule(),
            TypeModule.getModule(),
            VariableModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private ElementModule() {}

    @Override
    protected void configure() {
        bind(RuntimeElementKindFactory.class).to(RuntimeElementKindFactoryImpl.class).in(Singleton.class);
        bind(RuntimeGenericElementFactories.class).to(RuntimeGenericElementFactoriesImpl.class).in(Singleton.class);
        bind(Registries.class).to(RegistriesImpl.class).in(Singleton.class);
        bind(RuntimeElementFactory.class).to(RuntimeElementFactoryImpl.class).in(Singleton.class);
    }

}
