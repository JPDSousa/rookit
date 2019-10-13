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

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import accumulator.SingleAccumulator;

import java.util.Map;
import java.util.function.Supplier;

class StagedGrowOnlyCollectionAccumulatorImpl<A extends SingleAccumulator<A, T, ?>, T>
        extends GrowOnlyCollectionAccumulatorImpl<A, T> {

    private static final long serialVersionUID = 6898705177634732365L;
    private final GrowOnlyCollectionAccumulator<A, T> previousStage;

    StagedGrowOnlyCollectionAccumulatorImpl(final GrowOnlyCollectionAccumulator<A, T> previousStage,
            final Supplier<A> accumulatorSupplier) {
        super(accumulatorSupplier);
        //noinspection AssignmentOrReturnOfFieldWithMutableType
        this.previousStage = previousStage;
    }

    @Override
    public Map<T, A> getCollectionMap() {
        final Map<T, A> map = Maps.newHashMap(this.previousStage.getCollectionMap());
        super.getCollectionMap().forEach((key, value) -> accumulateEntry(map, key, value));

        return map;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && this.previousStage.isEmpty();
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("previousStage", this.previousStage)
                .toString();
    }
}
