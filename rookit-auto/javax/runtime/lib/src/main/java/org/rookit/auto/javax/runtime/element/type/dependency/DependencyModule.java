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
package org.rookit.auto.javax.runtime.element.type.dependency;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import org.rookit.auto.javax.runtime.element.type.node.TypeDependencyFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.BaseRegistries;
import org.rookit.utils.registry.MultiRegistry;

import java.util.Set;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

@SuppressWarnings("MethodMayBeStatic")
public final class DependencyModule extends AbstractModule {

    private static final Module MODULE = new DependencyModule();

    public static Module getModule() {
        return MODULE;
    }

    private DependencyModule() {}

    @Override
    protected void configure() {
        bind(TypeDependencyFactory.class).to(TypeDependencyFactoryImpl.class).in(Singleton.class);


        final Multibinder<MultiRegistry<RuntimeClassEntity, Dependency<?>>> mBinder
                = newSetBinder(binder(),
                               new TypeLiteral<MultiRegistry<RuntimeClassEntity, Dependency<?>>>() {});

        mBinder.addBinding().to(Class2Entity.class).in(Singleton.class);
        mBinder.addBinding().to(Interface.class).in(Singleton.class);
        mBinder.addBinding().to(TypeParameter.class).in(Singleton.class);
        mBinder.addBinding().to(SuperClass.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    MultiRegistry<RuntimeClassEntity, Dependency<?>> registry(
            final BaseRegistries factory,
            final Set<MultiRegistry<RuntimeClassEntity, Dependency<?>>> registries) {
        return factory.compositeRegistry(registries);
    }

}
