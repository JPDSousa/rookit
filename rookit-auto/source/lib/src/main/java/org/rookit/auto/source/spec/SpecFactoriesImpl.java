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
package org.rookit.auto.source.spec;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.utils.primitive.VoidUtils;

final class SpecFactoriesImpl implements SpecFactories {

    private final VoidUtils voidUtils;
    private final ExtendedElementVisitors visitors;

    @Inject
    private SpecFactoriesImpl(
            final VoidUtils voidUtils,
            final ExtendedElementVisitors visitors) {
        this.voidUtils = voidUtils;
        this.visitors = visitors;
    }

    @Override
    public <T> SpecFactory<T> fromVisitor(final ExtendedElementVisitor<StreamEx<T>, Void> visitor) {
        return new VisitorSpecFactory<>(visitor, this.voidUtils::returnVoid);
    }

    @Override
    public <T> SpecFactory<T> fromSingleVisitor(final ExtendedElementVisitor<T, Void> visitor) {
        return fromVisitor(this.visitors.streamEx(visitor));
    }

    @Override
    public String toString() {
        return "SpecFactoriesImpl{" +
                "voidUtils=" + this.voidUtils +
                ", visitors=" + this.visitors +
                "}";
    }

}
