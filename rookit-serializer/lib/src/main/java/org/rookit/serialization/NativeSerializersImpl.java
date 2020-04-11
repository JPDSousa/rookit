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

import com.google.inject.Inject;
import org.rookit.utils.collection.MapUtils;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

import java.util.Collection;
import java.util.Map;

final class NativeSerializersImpl implements NativeSerializers {

    private final MapUtils mapUtils;
    private final OptionalFactory optionalFactory;

    @Inject
    private NativeSerializersImpl(final MapUtils mapUtils, final OptionalFactory optionalFactory) {
        this.mapUtils = mapUtils;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public <K, V> Serializer<Map<K, V>> mapSerializer(final Serializer<K> keySerializer,
                                                      final Serializer<V> valueSerializer) {

        return new MapSerializer<>(
                collectionSerializer(keySerializer),
                collectionSerializer(valueSerializer),
                this.mapUtils
        );
    }

    @Override
    public <E> Serializer<Optional<E>> optionalSerializer(final Serializer<E> valueSerializer) {

        return new OptionalSerializer<>(
                valueSerializer,
                this.optionalFactory
        );
    }

    @Override
    public <E> Serializer<Collection<E>> collectionSerializer(final Serializer<E> itemSerializer) {

        return new CollectionSerializer<>(itemSerializer);
    }

}
