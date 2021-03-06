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

import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.element.node.MutableNodeElement;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.registry.BaseRegistries;
import org.rookit.utils.registry.MultiRegistry;
import org.rookit.utils.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;

final class RuntimeGenericElementFactoriesImpl implements RuntimeGenericElementFactories {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeGenericElementFactoriesImpl.class);

    private final BaseRegistries registries;

    @Inject
    private RuntimeGenericElementFactoriesImpl(final BaseRegistries registries) {
        this.registries = registries;
    }

    @Override
    public <I extends RuntimeEntity, O extends Element & MutableNodeElement> RuntimeGenericElementFactory<I, O> factory(
            final Registry<I, O> registry,
            final MultiRegistry<I, Dependency<?>> dependenciesRegistry,
            final Class<O> outputClass) {
        logger.trace("Creating cyclic registry");
        final Registry<I, O> cyclicRegistry = this.registries
                .directedCyclicGraphRegistry(registry, dependenciesRegistry, outputClass);

        return new ElementConnectorDecorator<>(cyclicRegistry);
    }

    @Override
    public String toString() {
        return "RuntimeGenericElementFactoriesImpl{" +
                "registries=" + this.registries +
                "}";
    }

}
