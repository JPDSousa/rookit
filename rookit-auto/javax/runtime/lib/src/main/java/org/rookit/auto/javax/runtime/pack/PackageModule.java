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
package org.rookit.auto.javax.runtime.pack;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactories;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.element.pack.RuntimePackageElement;
import org.rookit.auto.javax.runtime.element.pack.RuntimePackageElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.MultiRegistry;
import org.rookit.utils.registry.Registry;

@SuppressWarnings("MethodMayBeStatic")
public final class PackageModule extends AbstractModule {

    private static final Module MODULE = new PackageModule();

    public static Module getModule() {
        return MODULE;
    }

    private PackageModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(RuntimePackageElementFactory.class).to(DelegateFactory.class).in(Singleton.class);
        bind(new TypeLiteral<MultiRegistry<RuntimePackageEntity, Dependency>>() {}).to(DependencyRegistry.class)
                .in(Singleton.class);
        bind(new TypeLiteral<Registry<RuntimePackageEntity, RuntimePackageElement>>() {})
                .to(PackageElementFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    RuntimeGenericElementFactory<RuntimePackageEntity, RuntimePackageElement> packageFactory(
            final RuntimeGenericElementFactories factories,
            final Registry<RuntimePackageEntity, RuntimePackageElement> registry,
            final MultiRegistry<RuntimePackageEntity, Dependency> dependenciesRegistry) {
        return factories.factory(registry, dependenciesRegistry, RuntimePackageElement.class);
    }

}
