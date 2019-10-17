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
package org.rookit.auto.javax.runtime.element.type;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactories;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeEntity;
import org.rookit.auto.javax.runtime.element.type.node.NodeModule;
import org.rookit.auto.javax.runtime.element.type.parameter.ParameterModule;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.MultiRegistry;
import org.rookit.utils.registry.Registry;

public final class TypeModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new TypeModule(),
            NodeModule.getModule(),
            ParameterModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private TypeModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(RuntimeTypeElementFactory.class).to(DelegateFactory.class).in(Singleton.class);
        bind(new TypeLiteral<Registry<RuntimeTypeEntity, RuntimeTypeElement>>() {})
                .to(RuntimeTypeElementFactoryImpl.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    RuntimeGenericElementFactory<RuntimeTypeEntity, RuntimeTypeElement> typeFactory(
            final RuntimeGenericElementFactories factories,
            final Registry<RuntimeTypeEntity, RuntimeTypeElement> registry,
            final MultiRegistry<RuntimeTypeEntity, Dependency> dependenciesRegistry) {
        return factories.factory(registry, dependenciesRegistry, RuntimeTypeElement.class);
    }

}
