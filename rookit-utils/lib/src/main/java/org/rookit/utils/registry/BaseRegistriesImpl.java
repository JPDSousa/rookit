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
package org.rookit.utils.registry;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.graph.MutableNode;
import org.rookit.utils.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class BaseRegistriesImpl implements BaseRegistries {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseRegistriesImpl.class);

    @Inject
    private BaseRegistriesImpl() {}

    @Override
    public <K, I, O> Registry<K, O> mapValueRegistry(final Registry<K, I> upstream,
                                                     final Function<I, Single<O>> mapper) {
        logger.trace("Creating map value registry for '{}'", upstream);
        return new MapValueRegistry<>(upstream, mapper);
    }

    @Override
    public <K1, K2, V> Registry<K2, V> mapKeyRegistry(final Registry<K1, V> upstream, final Function<K2, K1> mapper) {
        logger.trace("Creating map key registry for '{}'", upstream);
        return new MapKeyRegistry<>(upstream, mapper);
    }

    @Override
    public <K, V> Registry<K, V> memoizeRegistry(final Registry<K, V> upstream) {
        if (upstream instanceof MemoizeRegistry) {
            logger.debug("The provided registry is already a {}", MemoizeRegistry.class);
            return upstream;
        }
        logger.trace("Creating memoize registry for '{}'", upstream);
        return new MemoizeRegistry<>(upstream, Maps.newHashMap());
    }

    @Override
    public <K extends Node, V extends MutableNode> Registry<K, V> directedCyclicGraphRegistry(
            final Registry<K, V> delegate,
            final MultiRegistry<K, Dependency<?>> dependencyRegistry,
            final Class<V> vClass) {
        logger.trace("Creating a directed cyclic graph registry");
        return new DirectedCyclicGraph<>(memoizeRegistry(delegate), dependencyRegistry, vClass);
    }

    @Override
    public <K, V> MultiRegistry<K, V> compositeRegistry(final Iterable<? extends MultiRegistry<K, V>> multiRegistries) {
        return new CompositeMultiRegistry<>(multiRegistries);
    }

}
