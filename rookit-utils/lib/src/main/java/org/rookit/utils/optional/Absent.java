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

import com.google.common.collect.ImmutableSet;
import io.reactivex.Maybe;
import one.util.streamex.StreamEx;
import org.rookit.utils.function.ToBooleanFunction;
import org.rookit.utils.function.ToShortFunction;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

final class Absent<T> implements Optional<T> {

    @SuppressWarnings("FieldNotUsedInToString") // will generate stack overflow
    private final OptionalFactory factory;

    Absent(final OptionalFactory factory) {
        this.factory = factory;
    }

    @Override
    public java.util.Optional<T> toJavaOptional() {
        return java.util.Optional.empty();
    }

    @Override
    public StreamEx<T> stream() {
        return StreamEx.empty();
    }

    @Override
    public Maybe<T> maybe() {
        return Maybe.empty();
    }

    @Override
    public Set<T> toImmutableSet() {
        return ImmutableSet.of();
    }

    @Override
    public T get() {
        throw new NoSuchElementException("This optional has no value.");
    }

    @Override
    public boolean isPresent() {
        // since it is empty
        return false;
    }

    @Override
    public boolean contains(final T expected) {
        // since it is empty
        return false;
    }

    @Override
    public void ifPresent(final Consumer<? super T> consumer) {
        // since it is empty
    }

    @Override
    public Optional<T> peekIfPresent(final Consumer<? super T> consumer) {
        // since it is empty
        return this;
    }

    @Override
    public Optional<T> filter(final Predicate<? super T> filter) {
        // since it is empty
        return this;
    }

    @Override
    public <U> Optional<U> select(final Class<U> clazz) {
        return (Optional<U>) this;
    }

    @Override
    public <U> Optional<U> map(final Function<? super T, ? extends U> mapper) {
        // since it is empty
        //noinspection unchecked
        return (Optional<U>) this;
    }

    @Override
    public <U> StreamEx<U> flatMapToStream(final Function<? super T, ? extends StreamEx<U>> mapper) {
        return StreamEx.empty();
    }

    @Override
    public <U> StreamEx<U> flatMapToStreamFromCollection(final Function<? super T, ? extends Collection<U>> mapper) {
        return StreamEx.empty();
    }

    @Override
    public OptionalInt mapToInt(final ToIntFunction<T> intMapper) {
        return OptionalInt.empty();
    }

    @Override
    public OptionalShort mapToShort(final ToShortFunction<T> shortMapper) {
        return this.factory.emptyShort();
    }

    @Override
    public OptionalLong mapToLong(final ToLongFunction<T> longMapper) {
        return OptionalLong.empty();
    }

    @Override
    public OptionalDouble mapToDouble(final ToDoubleFunction<T> doubleMapper) {
        return OptionalDouble.empty();
    }

    @Override
    public OptionalBoolean mapToBoolean(final ToBooleanFunction<T> booleanMapper) {
        return this.factory.emptyBoolean();
    }

    @Override
    public <U> Optional<U> flatMap(final Function<? super T, Optional<U>> flatMapper) {
        //noinspection unchecked
        return (Optional<U>) this;
    }

    @Override
    public T orElse(final T other) {
        return other;
    }

    @Override
    public Optional<T> orElseMaybe(final Optional<T> fallback) {
        return fallback;
    }

    @Override
    public T orElseGet(final Supplier<? extends T> supplier) {
        return supplier.get();
    }

    @Override
    public Optional<T> orElseMaybe(final Supplier<Optional<T>> supplier) {
        return supplier.get();
    }

    @Override
    public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSuppler) throws X {
        throw exceptionSuppler.get();
    }

    @Override
    public String toString() {
        return "Absent{" + "}";
    }
}
