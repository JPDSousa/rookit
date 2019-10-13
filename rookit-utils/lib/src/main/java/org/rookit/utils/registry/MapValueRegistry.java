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
package org.rookit.utils.registry;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

final class MapValueRegistry<K, I, O> implements Registry<K, O> {

    private final Registry<K, I> upstream;
    private final Function<I, Single<O>> mapper;

    MapValueRegistry(final Registry<K, I> upstream, final Function<I, Single<O>> mapper) {
        this.upstream = upstream;
        this.mapper = mapper;
    }

    @Override
    public Maybe<O> get(final K key) {
        return this.upstream.get(key)
                .flatMapSingleElement(this.mapper);
    }

    @Override
    public Single<O> fetch(final K key) {
        return this.upstream.fetch(key)
                .flatMap(this.mapper);
    }

    @Override
    public String toString() {
        return "MapValueRegistry{" +
                "upstream=" + this.upstream +
                ", mapper=" + this.mapper +
                "}";
    }

    @Override
    public void close() {
        // nothing to close
    }
}
