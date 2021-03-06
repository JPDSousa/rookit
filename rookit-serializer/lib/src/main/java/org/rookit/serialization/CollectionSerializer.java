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

import java.util.ArrayList;
import java.util.Collection;

final class CollectionSerializer<E> implements Serializer<Collection<E>> {

    private final Serializer<E> itemSerializer;

    CollectionSerializer(final Serializer<E> itemSerializer) {
        this.itemSerializer = itemSerializer;
    }

    @Override
    public void write(final TypeWriter writer, final Collection<E> collection) {

        writer.startArray();
        writer.value(collection.size());

        collection.forEach(item -> this.itemSerializer.write(writer, item));

        writer.endArray();
    }

    @Override
    public Collection<E> read(final TypeReader reader) {

        reader.startArray();
        final int size = reader.readInt();
        final Collection<E> list = new ArrayList<>(size);

        while (reader.hasNext()) {
            list.add(this.itemSerializer.read(reader));
        }

        reader.endArray();

        return list;
    }

}
