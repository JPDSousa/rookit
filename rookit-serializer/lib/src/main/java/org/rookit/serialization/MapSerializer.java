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
package org.rookit.serialization;

import org.rookit.utils.collection.MapUtils;

import java.util.Collection;
import java.util.Map;

final class MapSerializer<K, V> implements Serializer<Map<K, V>> {

    private static final String KEYS = "keys";
    private static final String VALUES = "values";

    private final Serializer<Collection<K>> keysSerializer;
    private final Serializer<Collection<V>> valuesSerializer;
    private final MapUtils mapUtils;

    MapSerializer(
            final Serializer<Collection<K>> keysSerializer,
            final Serializer<Collection<V>> valuesSerializer,
            final MapUtils mapUtils) {
        this.keysSerializer = keysSerializer;
        this.valuesSerializer = valuesSerializer;
        this.mapUtils = mapUtils;
    }

    @Override
    public void write(final TypeWriter writer, final Map<K, V> map) {

        writer.startObject();

        writer.name(KEYS);
        this.keysSerializer.write(writer, map.keySet());

        writer.name(VALUES);
        this.valuesSerializer.write(writer, map.values());

        writer.endObject();
    }

    @Override
    public Map<K, V> read(final TypeReader reader) {

        reader.startObject();

        final Collection<K> keys = this.keysSerializer.read(reader);
        final Collection<V> values = this.valuesSerializer.read(reader);

        reader.endObject();

        return this.mapUtils.mapByIteration(keys, values);
    }

}
