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
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

final class MemoizeRegistry<K, V> implements Registry<K, V>  {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MemoizeRegistry.class);

    private final Registry<K, V> delegate;
    private final Map<K, V> map;

    MemoizeRegistry(final Registry<K, V> delegate,
                    final Map<K, V> map) {
        this.delegate = delegate;
        this.map = Maps.newHashMap(map);
    }

    @Override
    public Maybe<V> get(final K key) {
        final V value = this.map.get(key);
        if (Objects.nonNull(value)) {
            logger.trace("Entry for '{}' already exists in map", key);
            return Maybe.just(value);
        }
        logger.trace("No entry found for key '{}'. Delegating", key);
        return this.delegate.get(key)
                .doAfterSuccess(delegateValue -> put(key, delegateValue));
    }

    private void put(final K key, final V value) {
        logger.trace("Delegate returned a value for key '{}'", key);
        this.map.put(key, value);
    }

    @Override
    public Single<V> fetch(final K key) {
        final V value = this.map.get(key);
        if (Objects.nonNull(value)) {
            logger.trace("Entry for '{}' already exists in map", key);
            return Single.just(value);
        }
        logger.trace("No entry found for key '{}'. Delegating", key);
        return this.delegate.fetch(key)
                .doAfterSuccess(delegateValue -> logger.trace("Delegate returned a value for key '{}'", key))
                .doAfterSuccess(delegateValue -> this.map.put(key, delegateValue));
    }

    @Override
    public void close() {
        logger.debug("Cleaning map");
        this.map.clear();
    }

    @Override
    public String toString() {
        return "MemoizeRegistry{" +
                "delegate=" + this.delegate +
                ", map=" + this.map +
                "}";
    }

}
