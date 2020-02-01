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

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;

public interface StreamExtendedElementVisitor<T, P> extends ExtendedElementVisitor<StreamEx<T>, P> {

    @Override
    default StreamEx<T> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return StreamEx.empty();
    }

    @Override
    default StreamEx<T> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return StreamEx.empty();
    }

    @Override
    default StreamEx<T> visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return StreamEx.empty();
    }

    @Override
    default StreamEx<T> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return StreamEx.empty();
    }

    @Override
    default StreamEx<T> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return StreamEx.empty();
    }

    @Override
    default StreamEx<T> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return StreamEx.empty();
    }
}
