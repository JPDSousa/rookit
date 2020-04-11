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

package org.rookit.utils.collection;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
final class MapUtilsImpl implements MapUtils {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MapUtilsImpl.class);

    private final Failsafe failsafe;

    @Inject
    private MapUtilsImpl(final Failsafe failsafe) {
        this.failsafe = failsafe;
    }

    @Override
    public <K, V> V getOrDefault(final Map<K, V> map, final K key, final Supplier<V> supplier) {
        return Optional.ofNullable(map.get(key))
                .orElseGet(supplier);
    }

    @Override
    public <K, V> Map<K, V> mapByIndex(final List<K> keys, final List<V> values) {
        final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();

        for (int i = 0; i < keys.size(); i++) {
            builder.put(keys.get(i), values.get(i));
        }

        return builder.build();
    }

    @Override
    public <K, V> Map<K, V> mapByIteration(final Collection<K> keys,
                                           final Collection<V> values) {

        this.failsafe.checkArgument().collection().isOfSameSize(logger, keys, "keys", values, "values");

        final Map<K, V> map = new HashMap<>(keys.size());

        final Iterator<K> keysIt = keys.iterator();
        final Iterator<V> valuesIt = values.iterator();

        while(keysIt.hasNext() && valuesIt.hasNext()) {
            final K key = keysIt.next();
            final V value = valuesIt.next();
            map.put(key, value);
        }

        return map;
    }

}
