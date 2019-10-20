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

import com.google.common.collect.ImmutableList;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

final class CompositeMultiRegistry<K, V> implements MultiRegistry<K, V> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CompositeMultiRegistry.class);

    private final Iterable<? extends MultiRegistry<K, V>> registries;

    CompositeMultiRegistry(final Iterable<? extends MultiRegistry<K, V>> registries) {
        this.registries = registries;
    }

    @Override
    public Observable<V> fetch(final K key) {
        return stream(this.registries.spliterator(), false)
                .map(registry -> registry.fetch(key))
                .collect(Collectors.collectingAndThen(
                        ImmutableList.toImmutableList(),
                        Observable::concat
                ));
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

}
