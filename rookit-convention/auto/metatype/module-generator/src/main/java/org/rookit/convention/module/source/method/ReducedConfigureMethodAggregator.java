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
package org.rookit.convention.module.source.method;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.aggregator.ExtendedTypeElementAggregator;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregator;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregatorFactory;
import org.rookit.utils.primitive.VoidUtils;

final class ReducedConfigureMethodAggregator implements ExtendedTypeElementAggregator<MethodSource> {

    private final ExtendedElementAggregatorFactory<MethodSource> factory;
    private final ExtendedElementAggregator<MethodSource> delegate;
    private final MethodSource left;
    private final MethodSource right;
    private final VoidUtils voidUtils;

    ReducedConfigureMethodAggregator(final ExtendedElementAggregatorFactory<MethodSource> factory,
                                     final MethodSource left,
                                     final MethodSource right,
                                     final VoidUtils voidUtils) {
        this.factory = factory;
        this.delegate = factory.create();
        this.left = left;
        this.right = right;
        this.voidUtils = voidUtils;
    }

    @Override
    public boolean accept(final ExtendedElement item) {
        return this.delegate.accept(item);
    }

    @Override
    public ExtendedElementAggregator<MethodSource> reduce(
            final ExtendedElementAggregator<MethodSource> aggregator) {
        return new ReducedConfigureMethodAggregator(this.factory, result(), aggregator.result(), this.voidUtils);
    }

    @Override
    public MethodSource result() {
        // TODO we can do better...
        // TODO for real: this implementation >assumes< that the method signature is the same in each
        // TODO reducted aggregators
        return this.delegate.result().toBuilder()
                .addCode(this.left.code)
                .addCode(this.right.code)
                .build();
    }

    @Override
    public String toString() {
        return "ReducedConfigureMethodAggregator{" +
                "factory=" + this.factory +
                ", delegate=" + this.delegate +
                ", left=" + this.left +
                ", right=" + this.right +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
