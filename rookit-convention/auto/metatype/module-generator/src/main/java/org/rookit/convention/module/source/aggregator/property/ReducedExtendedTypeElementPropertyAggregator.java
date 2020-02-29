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
package org.rookit.convention.module.source.aggregator.property;

import com.google.common.collect.ImmutableSet;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.aggregator.ExtendedTypeElementAggregator;
import org.rookit.auto.source.method.MethodSource;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.Collection;

final class ReducedExtendedTypeElementPropertyAggregator
        implements ExtendedTypeElementAggregator<Collection<MethodSource>> {

    private final ExtendedTypeElementAggregator<Collection<MethodSource>> left;
    private final ExtendedTypeElementAggregator<Collection<MethodSource>> right;
    private final Messager messager;

    ReducedExtendedTypeElementPropertyAggregator(final ExtendedTypeElementAggregator<Collection<MethodSource>> left,
                                                 final ExtendedTypeElementAggregator<Collection<MethodSource>> right,
                                                 final Messager messager) {
        this.left = left;
        this.right = right;
        this.messager = messager;
    }

    @Override
    public boolean accept(final ExtendedElement item) {
        this.messager.printMessage(Diagnostic.Kind.WARNING, "This aggregator is already in reduction stage. " +
                "It can no longer accept more items.");
        return false;
    }

    @Override
    public ExtendedTypeElementAggregator<Collection<MethodSource>> reduce(
            final ExtendedTypeElementAggregator<Collection<MethodSource>> aggregator) {

        return new ReducedExtendedTypeElementPropertyAggregator(this, aggregator, this.messager);
    }

    @Override
    public Collection<MethodSource> result() {
        return ImmutableSet.<MethodSource>builder()
                .addAll(this.left.result())
                .addAll(this.right.result())
                .build();
    }

    @Override
    public String toString() {
        return "ReducedExtendedTypeElementPropertyAggregator{" +
                "left=" + this.left +
                ", right=" + this.right +
                ", messager=" + this.messager +
                "}";
    }
}
