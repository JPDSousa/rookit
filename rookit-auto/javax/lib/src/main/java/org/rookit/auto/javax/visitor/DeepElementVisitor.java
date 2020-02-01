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
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;

import java.util.function.BinaryOperator;

final class DeepElementVisitor<R, P> implements ExtendedElementVisitor<R, P> {

    private final ExtendedElementVisitor<R, P> delegate;
    private final BinaryOperator<R> reductionFunction;

    DeepElementVisitor(final ExtendedElementVisitor<R, P> delegate, final BinaryOperator<R> reductionFunction) {
        this.delegate = delegate;
        this.reductionFunction = reductionFunction;
    }

    private R visitEnclosedElements(final ExtendedElement element, final P param) {
        return element.getEnclosedElements().stream()
                .map(nestedElement -> nestedElement.accept(this, param))
                .reduce(element.accept(this.delegate, param), this.reductionFunction);
    }

    @Override
    public R visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return visitEnclosedElements(packageElement, parameter);
    }

    @Override
    public R visitType(final ExtendedTypeElement extendedType, final P parameter) {
        // TODO not sure if this includes
        // TODO 1 - type params
        // TODO 2 - inner classes (anonymous or nested)
        // TODO 3 - inherited members
        return visitEnclosedElements(extendedType, parameter);
    }

    @Override
    public R visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return visitEnclosedElements(extendedExecutable, parameter);
    }

    @Override
    public R visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return visitEnclosedElements(extendedParameter, parameter);
    }

    @Override
    public R visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return visitEnclosedElements(extendedElement, parameter);
    }

    @Override
    public R visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return visitEnclosedElements(extendedElement, parameter);
    }
}
