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

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import io.reactivex.Maybe;
import one.util.streamex.StreamEx;
import org.rookit.utils.function.ToBooleanFunction;
import org.rookit.utils.function.ToShortFunction;

import java.util.Collection;
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

final class Present<T> implements Optional<T> {

    private final OptionalFactory factory;
    private final T value;

    Present(final OptionalFactory factory, final T value) {
        this.factory = factory;
        this.value = value;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if ((other == null) || (getClass() != other.getClass())) return false;
        final Present<?> present = (Present<?>) other;
        return Objects.equal(this.value, present.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public java.util.Optional<T> toJavaOptional() {
        return java.util.Optional.of(this.value);
    }

    @Override
    public StreamEx<T> stream() {
        return StreamEx.of(this.value);
    }

    @Override
    public Maybe<T> maybe() {
        return Maybe.just(this.value);
    }

    @Override
    public Set<T> toImmutableSet() {
        return ImmutableSet.of(this.value);
    }

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean contains(final T expected) {
        return this.value.equals(expected);
    }

    @Override
    public void ifPresent(final Consumer<? super T> consumer) {
        consumer.accept(this.value);
    }

    @Override
    public Optional<T> filter(final Predicate<? super T> filter) {
        return filter.test(this.value) ? this : this.factory.empty();
    }

    @Override
    public <U> Optional<U> select(final Class<U> clazz) {
        if (clazz.isAssignableFrom(this.value.getClass())) {
            return (Optional<U>) this;
        }
        return this.factory.empty();
    }

    @Override
    public <U> Optional<U> map(final Function<? super T, ? extends U> mapper) {
        return this.factory.ofNullable(mapper.apply(this.value));
    }

    @Override
    public <U> StreamEx<U> flatMapToStream(final Function<? super T, ? extends StreamEx<U>> mapper) {
        return mapper.apply(this.value);
    }

    @Override
    public <U> StreamEx<U> flatMapToStreamFromCollection(final Function<? super T, ? extends Collection<U>> mapper) {
        return StreamEx.of(mapper.apply(this.value));
    }

    @Override
    public OptionalInt mapToInt(final ToIntFunction<T> intMapper) {
        return OptionalInt.of(intMapper.applyAsInt(this.value));
    }

    @Override
    public OptionalShort mapToShort(final ToShortFunction<T> shortMapper) {
        return this.factory.ofShort(shortMapper.apply(this.value));
    }

    @Override
    public OptionalLong mapToLong(final ToLongFunction<T> longMapper) {
        return OptionalLong.of(longMapper.applyAsLong(this.value));
    }

    @Override
    public OptionalDouble mapToDouble(final ToDoubleFunction<T> doubleMapper) {
        return OptionalDouble.of(doubleMapper.applyAsDouble(this.value));
    }

    @Override
    public OptionalBoolean mapToBoolean(final ToBooleanFunction<T> booleanMapper) {
        return this.factory.ofBoolean(booleanMapper.apply(this.value));
    }

    @Override
    public <U> Optional<U> flatMap(final Function<? super T, Optional<U>> flatMapper) {
        return flatMapper.apply(this.value);
    }

    @Override
    public T orElse(final T other) {
        return this.value;
    }

    @Override
    public Optional<T> orElseMaybe(final Optional<T> fallback) {
        return this;
    }

    @Override
    public T orElseGet(final Supplier<? extends T> supplier) {
        return this.value;
    }

    @Override
    public Optional<T> orElseMaybe(final Supplier<Optional<T>> supplier) {
        return this;
    }

    @Override
    public <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSuppler) {
        return this.value;
    }

    @Override
    public String toString() {
        return "Present{" +
                "factory=" + this.factory +
                ", value=" + this.value +
                "}";
    }
}
