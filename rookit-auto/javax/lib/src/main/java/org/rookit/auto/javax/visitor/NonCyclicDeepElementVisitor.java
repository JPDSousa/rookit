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
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.utils.optional.Optional;

import java.util.function.BinaryOperator;

final class NonCyclicDeepElementVisitor<R, P> implements ExtendedElementVisitor<R, VisitorContext<P>> {


    private final ExtendedElementVisitor<R, P> delegate;
    private final BinaryOperator<R> reductionFunction;

    NonCyclicDeepElementVisitor(
            final ExtendedElementVisitor<R, P> delegate,
            final BinaryOperator<R> reductionFunction) {
        this.delegate = delegate;
        this.reductionFunction = reductionFunction;
    }

    private R visitEnclosedElements(final ExtendedElement element, final VisitorContext<P> context) {
        return element.getEnclosedElements().stream()
                .map(nestedElement -> context.tryVisit(nestedElement, this))
                .flatMap(Optional::stream)
                .reduce(element.accept(this.delegate, context.param()), this.reductionFunction);
    }

    @Override
    public R visitPackage(final ExtendedPackageElement packageElement, final VisitorContext<P> context) {
        return visitEnclosedElements(packageElement, context);
    }

    @Override
    public R visitType(final ExtendedTypeElement extendedType, final VisitorContext<P> context) {
        // TODO not sure if this includes
        // TODO 1 - type params
        // TODO 2 - inner classes (anonymous or nested)
        // TODO 3 - inherited members
        return visitEnclosedElements(extendedType, context);
    }

    @Override
    public R visitExecutable(final ExtendedExecutableElement extendedExecutable, final VisitorContext<P> context) {
        return visitEnclosedElements(extendedExecutable, context);
    }

    @Override
    public R visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final VisitorContext<P> context) {
        return visitEnclosedElements(extendedParameter, context);
    }

    @Override
    public R visitVariable(final ExtendedVariableElement extendedElement, final VisitorContext<P> context) {
        return visitEnclosedElements(extendedElement, context);
    }

    @Override
    public R visitUnknown(final ExtendedElement extendedElement, final VisitorContext<P> context) {
        return visitEnclosedElements(extendedElement, context);
    }

}
