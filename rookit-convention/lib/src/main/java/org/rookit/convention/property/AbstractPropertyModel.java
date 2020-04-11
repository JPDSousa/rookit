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
package org.rookit.convention.property;

import org.rookit.convention.MetaType;
import org.rookit.serialization.Serializer;
import org.rookit.utils.optional.Optional;

import java.util.Collection;
import java.util.function.Function;

public abstract class AbstractPropertyModel<E, T> implements PropertyModel<E, T> {

    private final String name;
    private final MetaType<T> metaType;
    private final Function<E, T> getter;

    protected AbstractPropertyModel(
            final String name,
            final MetaType<T> metaType,
            final Function<E, T> getter) {
        this.name = name;
        this.metaType = metaType;
        this.getter = getter;
    }

    @Override
    public String propertyName() {
        return this.name;
    }

    @Override
    public T get(final E entity) {

        return this.getter.apply(entity);
    }

    @Override
    public Collection<PropertyModel<T, ?>> properties() {
        return this.metaType.properties();
    }

    @Override
    public Optional<PropertyModel<T, ?>> property(final String name) {
        return this.metaType.property(name);
    }

    @Override
    public Class<T> modelType() {
        return this.metaType.modelType();
    }

    @Override
    public Serializer<T> modelSerializer() {
        return this.metaType.modelSerializer();
    }

}
