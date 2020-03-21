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

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import java.util.HashSet;
import java.util.Set;

final class RecursiveContext<P> implements VisitorContext<P> {

    private final P param;
    private final Set<String> visitedNodes;
    private final ExtendedElementVisitor<String, Void> idVisitor;
    private final VoidUtils voidUtils;
    private final OptionalFactory optionalFactory;

    RecursiveContext(
            final P param,
            final Set<String> visitedNodes,
            final ExtendedElementVisitor<String, Void> idVisitor,
            final VoidUtils voidUtils,
            final OptionalFactory optionalFactory) {

        this.param = param;
        this.visitedNodes = new HashSet<>(visitedNodes);
        this.idVisitor = idVisitor;
        this.voidUtils = voidUtils;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public P param() {

        return this.param;
    }

    @Override
    public <R> Optional<R> tryVisit(final ExtendedElement element,
                                    final ExtendedElementVisitor<R, VisitorContext<P>> visitor) {

        final String nodeId = idFor(element);
        if (this.visitedNodes.add(nodeId)) {

            return this.optionalFactory.of(element.accept(visitor, this));
        }

        return this.optionalFactory.empty();
    }

    private String idFor(final ExtendedElement element) {

        return element.accept(this.idVisitor, this.voidUtils.returnVoid());
    }

}
