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

import com.google.inject.Inject;

final class OptionalFactoryImpl implements OptionalFactory {

    private final Optional<?> empty;
    private final OptionalShort emptyShort;
    private final OptionalBoolean emptyBoolean;

    @Inject
    private OptionalFactoryImpl() {
        this.emptyShort = new EmptyShort();
        this.emptyBoolean = new EmptyBoolean();
        // TODO this is not a perfect solution, but it is a performance and memory optimization.
        // TODO the other two possibilities were: synchronize the empty() calls and perform lazy initialization,
        // TODO or create an absent per empty() call.
        this.empty = new Absent<>(this);
    }

    @Override
    public <T> Optional<T> empty() {
        //noinspection unchecked
        return (Optional<T>) this.empty;
    }

    @Override
    public <T> Optional<T> of(final T value) {
        return new Present<>(this, value);
    }

    @Override
    public OptionalShort emptyShort() {
        return this.emptyShort;
    }

    @Override
    public OptionalShort ofShort(final short value) {
        return new SomeShort(value);
    }

    @Override
    public OptionalBoolean emptyBoolean() {
        return this.emptyBoolean;
    }

    @SuppressWarnings("BooleanParameter")
    @Override
    public OptionalBoolean ofBoolean(final boolean value) {
        return new SomeBoolean(value);
    }

    @Override
    public String toString() {
        return "OptionalFactoryImpl{" +
                "empty=" + this.empty +
                ", emptyShort=" + this.emptyShort +
                ", emptyBoolean=" + this.emptyBoolean +
                "}";
    }
}
