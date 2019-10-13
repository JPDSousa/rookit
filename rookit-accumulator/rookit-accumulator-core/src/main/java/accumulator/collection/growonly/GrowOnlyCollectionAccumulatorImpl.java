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

package accumulator.collection.growonly;

import accumulator.AccumulatorFactory;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import one.util.streamex.StreamEx;
import accumulator.SingleAccumulator;
import org.rookit.utils.collection.MapUtilsImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

class GrowOnlyCollectionAccumulatorImpl<A extends SingleAccumulator<A, T, ?>, T>
        extends AbstractGrowOnlyCollectionAccumulator<A, T> {

    private static final long serialVersionUID = -7712240102284242812L;
    private final Supplier<A> accumulatorSupplier;

    private final Map<T, A> map;

    GrowOnlyCollectionAccumulatorImpl(final Supplier<A> diffSupplier) {
        this.accumulatorSupplier = diffSupplier;
        this.map = Maps.newHashMap();
    }

    protected Supplier<A> getAccumulatorSupplier() {
        return this.accumulatorSupplier;
    }

    @Override
    public void accumulate(final GrowOnlyCollectionAccumulator<A, T> accumulator) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(accumulator, "accumulator");
        accumulator.getCollectionMap()
                .forEach((key, value) -> accumulateEntry(this.map, key, value));
    }

    @Override
    public boolean add(final T source) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(source, "source");
        this.map.compute(source, (key, value) -> {
            final A diff = isNull(value) ? this.accumulatorSupplier.get() : value;
            diff.accumulate(source);
            return diff;
        });
        return true;
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public boolean contains(final Object o) {
        VALIDATOR.checkArgument().isNotNull(o, "o");
        return this.map.containsKey(o);
    }

    @Override
    public boolean containsAll(final Collection<?> collection) {
        //noinspection DuplicateStringLiteralInspection
        VALIDATOR.checkArgument().isNotNull(collection, "collection");
        return this.map.keySet()
                .containsAll(collection);
    }

    @Override
    public GrowOnlyCollectionAccumulator<A, T> createStage(final AccumulatorFactory factory) {
        return new StagedGrowOnlyCollectionAccumulatorImpl<>(this, this.accumulatorSupplier);
    }

    public A get(final T key) {
        VALIDATOR.checkArgument().isNotNull(key, "key");
        return MapUtilsImpl.getOrDefault(this.map, key, this.accumulatorSupplier);
    }

    @Override
    public Map<T, A> getCollectionMap() {
        return Collections.unmodifiableMap(this.map);
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    protected StreamEx<T> materializeAsStream() {
        return StreamEx.of(this.map.values())
                .map(SingleAccumulator::get)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    static <B, C extends SingleAccumulator<C, B, ?>> void accumulateEntry(final Map<B, C> map,
                                                                       final B key,
                                                                       final C value) {
        map.compute(key, (mapKey, mapValue) -> accumulate(mapValue, value));
    }

    private static <A extends SingleAccumulator<A, T, ?>, T> A accumulate(final A value,
                                                                       final A defaultValue) {
        if (isNull(value)) {
            return defaultValue;
        }
        value.accumulate(defaultValue);
        return value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accumulatorSupplier", this.accumulatorSupplier)
                .add("map", this.map)
                .toString();
    }
}
