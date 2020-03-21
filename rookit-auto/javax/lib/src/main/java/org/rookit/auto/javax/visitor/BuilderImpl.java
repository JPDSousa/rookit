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
package org.rookit.auto.javax.visitor;

import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.utils.adapt.Adapter;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import java.util.function.BinaryOperator;
import java.util.function.Function;

final class BuilderImpl<V extends ExtendedElementVisitor<R, P>, R, P>
        implements Builder<V, R, P> {

    private final V visitor;
    private final Function<ExtendedElementVisitor<R, P>, V> downcastAdapter;
    private final ExtendedElementVisitor<String, Void> idVisitor;
    private final VoidUtils voidUtils;
    private final OptionalFactory optionalFactory;

    BuilderImpl(
            final V visitor,
            final Function<ExtendedElementVisitor<R, P>, V> downcastAdapter,
            final ExtendedElementVisitor<String, Void> idVisitor,
            final VoidUtils voidUtils,
            final OptionalFactory optionalFactory) {
        this.visitor = visitor;
        this.downcastAdapter = downcastAdapter;
        this.idVisitor = idVisitor;
        this.voidUtils = voidUtils;
        this.optionalFactory = optionalFactory;
    }

    private Builder<V, R, P> newStage(final V visitor) {
        return new BuilderImpl<>(
                visitor,
                this.downcastAdapter,
                this.idVisitor,
                this.voidUtils,
                this.optionalFactory
        );
    }

    @Override
    public Builder<V, R, P> withTypeAdapter(final Adapter<ExtendedTypeElement> adapter) {
        return newStage(this.downcastAdapter.apply(new TypeAdapterVisitor<>(this.visitor, adapter)));
    }

    @Override
    public Builder<V, R, P> withRecursiveVisiting(final BinaryOperator<R> resultReducer) {
        return newStage(this.downcastAdapter.apply(new DeepElementVisitor<>(build(), resultReducer,
                                                                            this.idVisitor,
                                                                            this.voidUtils,
                                                                            this.optionalFactory)));
    }

    @Override
    public V build() {
        return this.visitor;
    }

}
