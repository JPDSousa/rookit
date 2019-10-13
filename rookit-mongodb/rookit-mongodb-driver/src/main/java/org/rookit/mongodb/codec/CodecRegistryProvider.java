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
package org.rookit.mongodb.codec;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.rookit.serialization.Serializer;

import java.util.Set;

final class CodecRegistryProvider implements Provider<CodecRegistry> {

    private final CodecRegistry defaultCodecRegistry;
    private final Set<Serializer<?>> serializers;

    @Inject
    private CodecRegistryProvider(final Set<Serializer<?>> serializers) {
        this.serializers = serializers;
        this.defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
    }

    @Override
    public CodecRegistry get() {
        final ImmutableList.Builder<Codec<?>> builder = ImmutableList.builder();

        for (final Serializer<?> serializer : this.serializers) {
            builder.add(new SerializerAdapter<>(serializer));
        }
        final CodecRegistry codecRegistry = CodecRegistries.fromCodecs(builder.build());

        return CodecRegistries.fromRegistries(codecRegistry, this.defaultCodecRegistry);
    }

}
