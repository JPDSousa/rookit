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
package org.rookit.convention.serializer;

import org.apache.commons.lang3.SerializationException;
import org.rookit.convention.MetaType;
import org.rookit.convention.instance.InstanceBuilder;
import org.rookit.convention.instance.InstanceBuilderFactory;
import org.rookit.convention.property.PropertyModel;
import org.rookit.serialization.Serializer;
import org.rookit.serialization.TypeReader;
import org.rookit.serialization.TypeWriter;
import org.rookit.utils.optional.Optional;


public final class ConventionSerializer<T> implements Serializer<T> {

    public static <S> Serializer<S> create(
            final MetaType<S> metaType,
            final InstanceBuilderFactory<S> instanceFactory) {

        return new ConventionSerializer<>(metaType, instanceFactory);
    }

    private final MetaType<T> properties;
    private final InstanceBuilderFactory<T> instanceBuilderFactory;

    private ConventionSerializer(
            final MetaType<T> properties,
            final InstanceBuilderFactory<T> instanceFactory) {
        this.properties = properties;
        this.instanceBuilderFactory = instanceFactory;
    }

    @Override
    public void write(final TypeWriter writer, final T value) {
        writer.startObject();

        this.properties.properties().forEach(property -> writeProperty(writer, value, property));

        writer.endObject();
    }

    private <P> void writeProperty(final TypeWriter writer,
                                   final T value,
                                   final PropertyModel<T, P> propertyModel) {

        if (propertyModel.isPresent(value)) {
            final Serializer<P> serializer = propertyModel.modelSerializer();
            final P propertyValue = propertyModel.get(value);
            writer.name(propertyModel.propertyName());
            serializer.write(writer, propertyValue);
        }
    }

    @Override
    public T read(final TypeReader reader) {
        InstanceBuilder<T> builder = this.instanceBuilderFactory.create();

        reader.startObject();
        builder = readProperties(reader, builder);
        reader.endObject();

        if (!builder.isReady()) {
            // TODO maybe provide more information here on what the actual data is.
            throw new SerializationException("Source object has not enough data to build a consistent genre.");
        }
        return builder.getInstance();
    }

    private <P> InstanceBuilder<T> readProperty(
            final TypeReader reader,
            final InstanceBuilder<T> builder,
            final PropertyModel<T, P> property) {

        final P value = property.modelSerializer()
                .read(reader);

        return builder.withProperty(property, value);
    }

    private InstanceBuilder<T> readProperties(final TypeReader reader, final InstanceBuilder<T> builder) {
        InstanceBuilder<T> builderCopy = builder;
        while(reader.hasNext()) {

            final String propertyName = reader.name();
            final Optional<PropertyModel<T, ?>> propertyOrNone = this.properties.property(propertyName);

            if (propertyOrNone.isPresent()) {
                final PropertyModel<T, ?> property = propertyOrNone.get();

                builderCopy = readProperty(reader, builderCopy, property);
            }
            else {
                // TODO uncomment and handle log.
                // logger.trace("Cannot find property with name {}. Ignoring.", propertyName);
            }
        }
        return builderCopy;
    }

}
