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
package org.rookit.utils.optional;

import java.util.Objects;

public interface OptionalFactory {

    <T> Optional<T> empty();

    <T> Optional<T> of(T value);

    default <T> Optional<T> ofNullable(final T value) {
        if (Objects.isNull(value)) {
            return empty();
        }
        return of(value);
    }

    OptionalShort emptyShort();

    OptionalShort ofShort(short value);

    OptionalBoolean emptyBoolean();

    @SuppressWarnings("BooleanParameter")
    OptionalBoolean ofBoolean(boolean value);

    default OptionalBoolean ofNullableBoolean(final Boolean value) {
        if (Objects.isNull(value)) {
            return emptyBoolean();
        }
        //noinspection AutoUnboxing
        return ofBoolean(value);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    default <T> Optional<T> fromJavaOptional(final java.util.Optional<T> optional) {
        return optional.map(this::of)
                .orElse(empty());
    }

    @SuppressWarnings({"Guava", "OptionalUsedAsFieldOrParameterType"})
    default <T> Optional<T> fromGuavaOptional(final com.google.common.base.Optional<T> optional) {
        return optional.transform(this::of)
                .or(empty());
    }

}
