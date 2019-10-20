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

import io.reactivex.Single;
import io.reactivex.functions.Function;
import org.rookit.utils.graph.Dependency;
import org.rookit.utils.graph.MutableNode;
import org.rookit.utils.graph.Node;

public interface BaseRegistries {

    <K, V1, V2> Registry<K, V2> mapValueRegistry(Registry<K, V1> upstream, Function<V1, Single<V2>> mapper);

    <K1, K2, V> Registry<K2, V> mapKeyRegistry(Registry<K1, V> upstream, Function<K2, K1> mapper);

    <K, V> Registry<K, V> memoizeRegistry(Registry<K, V> upstream);

    <K extends Node, V extends MutableNode> Registry<K, V> directedCyclicGraphRegistry(
            Registry<K, V> delegate,
            MultiRegistry<K, Dependency<?>> dependencyRegistry,
            Class<V> vClass);

    <K, V> MultiRegistry<K, V> compositeRegistry(Iterable<? extends MultiRegistry<K, V>> registries);

}
