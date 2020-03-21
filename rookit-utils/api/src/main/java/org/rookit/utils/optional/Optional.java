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

public interface Optional<T> {

    java.util.Optional<T> toJavaOptional();

    StreamEx<T> stream();

    Maybe<T> maybe();

    Set<T> toImmutableSet();

    T get();

    boolean isPresent();

    default boolean contains(final T expected) {
        return isPresent() && get().equals(expected);
    }

    default void ifPresent(final Consumer<? super T> consumer) {
        if (isPresent()) {
            consumer.accept(get());
        }
    }

    default Optional<T> peekIfPresent(final Consumer<? super T> consumer) {
        ifPresent(consumer);
        return this;
    }

    default void ifAbsent(final Runnable runnable) {
        if (!isPresent()) {
            runnable.run();
        }
    }

    default Optional<T> peekIfAbsent(final Runnable runnable) {
        ifAbsent(runnable);
        return this;
    }

    Optional<T> filter(Predicate<? super T> filter);

    <U> Optional<U> select(Class<U> clazz);

    <U> Optional<U> map(Function<? super T, ? extends U> mapper);

    <U> StreamEx<U> flatMapToStream(Function<? super T, ? extends StreamEx<U>> mapper);

    <U> StreamEx<U> flatMapToStreamFromCollection(Function<? super T, ? extends Collection<U>> mapper);

    OptionalInt mapToInt(ToIntFunction<T> intMapper);

    OptionalShort mapToShort(ToShortFunction<T> shortMapper);

    OptionalLong mapToLong(ToLongFunction<T> longMapper);

    OptionalDouble mapToDouble(ToDoubleFunction<T> doubleMapper);

    OptionalBoolean mapToBoolean(ToBooleanFunction<T> booleanMapper);

    <U> Optional<U> flatMap(Function<? super T, Optional<U>> flatMapper);

    default T orElse(final T other) {
        return isPresent() ? get() : other;
    }

    default Optional<T> orElseMaybe(final Optional<T> fallback) {
        return isPresent() ? this : fallback;
    }

    default T orElseGet(final Supplier<? extends T> supplier) {
        return isPresent() ? get() : supplier.get();
    }

    default Optional<T> orElseMaybe(final Supplier<Optional<T>> supplier) {
        return isPresent() ? this : supplier.get();
    }

    default <X extends Throwable> T orElseThrow(final Supplier<? extends X> exceptionSuppler) throws X {
        if (isPresent()) {
            return get();
        }
        throw exceptionSuppler.get();
    }
}
