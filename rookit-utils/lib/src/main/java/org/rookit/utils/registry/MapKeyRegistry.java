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

final class MapKeyRegistry<I, O, V> implements Registry<O, V> {

    private final Registry<I, V> upstream;
    private final Function<O, I> mapKey;

    MapKeyRegistry(final Registry<I, V> upstream, final Function<O, I> mapKey) {
        this.upstream = upstream;
        this.mapKey = mapKey;
    }

    @Override
    public Maybe<V> get(final O key) {
        return Maybe.just(key)
                .map(this.mapKey)
                .flatMap(this.upstream::get);
    }

    @Override
    public Single<V> fetch(final O key) {
        return Single.just(key)
                .map(this.mapKey)
                .flatMap(this.upstream::fetch);
    }

    @Override
    public String toString() {
        return "MapKeyRegistry{" +
                "upstream=" + this.upstream +
                ", mapKey=" + this.mapKey +
                "}";
    }

    @Override
    public void close() {
        // nothing to close
    }
}
