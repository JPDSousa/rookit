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

import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.graph.MutableNode;
import org.rookit.utils.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DirectedCyclicGraph<K extends Node, V extends MutableNode> implements Registry<K, V> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DirectedCyclicGraph.class);

    private final Registry<K, V> detachedRegistry;
    private final MultiRegistry<K, Dependency> dependencyRegistry;
    private final Class<V> vClass;

    DirectedCyclicGraph(
            final Registry<K, V> detachedRegistry,
            final MultiRegistry<K, Dependency> dependencyRegistry,
            final Class<V> vClass) {
        this.detachedRegistry = detachedRegistry;
        this.dependencyRegistry = dependencyRegistry;
        this.vClass = vClass;
    }

    @Override
    public Maybe<V> get(final K key) {
        return fetch(key).toMaybe();
    }

    @Override
    public Single<V> fetch(final K key) {
        logger.trace("Creating '{}' for '{}'", this.vClass, key);
        return this.detachedRegistry.fetch(key)
                .doOnSuccess(output -> logger.trace("Created '{}'", output))
                .flatMap(value -> setDependencies(key, value));
    }

    private Single<V> setDependencies(final K input, final V value) {
        logger.trace("Fetching dependencies for '{}'", input);
        return this.dependencyRegistry.fetch(input)
                .doOnNext(dependency -> logger.trace("Setting '{}' as dependency for '{}'", dependency, input))
                .flatMapCompletable(value::addDependency)
                .doOnComplete(() -> logger.trace("Dependencies set"))
                .andThen(Single.just(value));
    }

    @Override
    public void close() {
        logger.trace("Nothing to close");
    }

    @Override
    public String toString() {
        return "DirectedCyclicGraph{" +
                "detachedRegistry=" + this.detachedRegistry +
                ", dependencyRegistry=" + this.dependencyRegistry +
                ", vClass=" + this.vClass +
                "}";
    }

}
