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
package org.rookit.auto.javax.runtime.element.variable;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactories;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.element.variable.dependency.DependencyModule;
import org.rookit.auto.javax.runtime.entity.RuntimeEnumEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeParameterEntity;
import org.rookit.javax.runtime.element.Registries;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.MultiRegistry;
import org.rookit.utils.registry.Registry;

@SuppressWarnings("MethodMayBeStatic")
public final class VariableModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new VariableModule(),
            DependencyModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private VariableModule() {}

    @Override
    protected void configure() {
        bind(VariableElementFactory.class).to(VariableElementFactoryImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    RuntimeGenericElementFactory<RuntimeEnumEntity, RuntimeVariableElement> enumFactory(
            final RuntimeGenericElementFactories factories,
            final Registry<RuntimeEnumEntity, RuntimeVariableElement> registry,
            final MultiRegistry<RuntimeEnumEntity, Dependency<?>> dependenciesRegistry) {
        return factories.factory(registry, dependenciesRegistry, RuntimeVariableElement.class);
    }

    @Provides
    @Singleton
    RuntimeGenericElementFactory<RuntimeFieldEntity, RuntimeVariableElement> fieldFactory(
            final RuntimeGenericElementFactories factories,
            final Registry<RuntimeFieldEntity, RuntimeVariableElement> registry,
            final MultiRegistry<RuntimeFieldEntity, Dependency<?>> dependenciesRegistry) {
        return factories.factory(registry, dependenciesRegistry, RuntimeVariableElement.class);
    }

    @Provides
    @Singleton
    RuntimeGenericElementFactory<RuntimeParameterEntity, RuntimeVariableElement> parameterFactory(
            final RuntimeGenericElementFactories factories,
            final Registry<RuntimeParameterEntity, RuntimeVariableElement> registry,
            final MultiRegistry<RuntimeParameterEntity, Dependency<?>> dependenciesRegistry) {
        return factories.factory(registry, dependenciesRegistry, RuntimeVariableElement.class);
    }

    @Provides
    @Singleton
    Registry<RuntimeParameterEntity, RuntimeVariableElement> parameterRegistry(
            final Registries registries,
            final VariableElementFactory factory) {
        return registries.fromFactory(factory::createFromParameter);
    }

    @Provides
    @Singleton
    Registry<RuntimeFieldEntity, RuntimeVariableElement> fieldRegistry(
            final Registries registries,
            final VariableElementFactory factory) {
        return registries.fromFactory(factory::createFromField);
    }

    @Provides
    @Singleton
    Registry<RuntimeEnumEntity, RuntimeVariableElement> enumRegistry(
            final Registries registries,
            final VariableElementFactory factory) {
        return registries.fromFactory(factory::createFromEnum);
    }

}
