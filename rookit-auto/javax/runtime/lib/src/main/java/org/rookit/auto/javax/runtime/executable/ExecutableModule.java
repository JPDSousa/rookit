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
package org.rookit.auto.javax.runtime.executable;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactories;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.element.executable.ExecutableElementFactory;
import org.rookit.auto.javax.runtime.element.executable.RuntimeExecutableElement;
import org.rookit.auto.javax.runtime.entity.RuntimeExecutableEntity;
import org.rookit.auto.javax.runtime.executable.dependency.DependencyModule;
import org.rookit.auto.javax.runtime.executable.node.NodeModule;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.MultiRegistry;
import org.rookit.utils.registry.Registry;

@SuppressWarnings("MethodMayBeStatic")
public final class ExecutableModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new ExecutableModule(),
            NodeModule.getModule(),
            DependencyModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private ExecutableModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(ExecutableElementFactory.class).to(ExecutableElementFactoryImpl.class).in(Singleton.class);
        bind(new TypeLiteral<Registry<RuntimeExecutableEntity, RuntimeExecutableElement>>() {})
                .to(ExecutableFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    RuntimeGenericElementFactory<RuntimeExecutableEntity, RuntimeExecutableElement> parameterFactory(
            final RuntimeGenericElementFactories factories,
            final Registry<RuntimeExecutableEntity, RuntimeExecutableElement> registry,
            final MultiRegistry<RuntimeExecutableEntity, Dependency> dependenciesRegistry) {
        return factories.factory(registry, dependenciesRegistry, RuntimeExecutableElement.class);
    }

}
