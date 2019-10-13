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

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.utils.adapt.Adapter;

final class TypeAdapterVisitor<T, P> implements ExtendedElementVisitor<T, P> {

    private final ExtendedElementVisitor<T, P> delegate;
    private final Adapter<ExtendedTypeElement> adapter;

    @Inject
    TypeAdapterVisitor(final ExtendedElementVisitor<T, P> delegate,
                       final Adapter<ExtendedTypeElement> adapter) {
        this.delegate = delegate;
        this.adapter = adapter;
    }

    @Override
    public String toString() {
        return "TypeAdapterVisitor{" +
                "delegate=" + this.delegate +
                ", adapter=" + this.adapter +
                "}";
    }

    @Override
    public T visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return this.delegate.visitPackage(packageElement, parameter);
    }

    @Override
    public T visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return this.delegate.visitType(this.adapter.adapt(extendedType), parameter);
    }

    @Override
    public T visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return this.delegate.visitExecutable(extendedExecutable, parameter);
    }

    @Override
    public T visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return this.delegate.visitTypeParameter(extendedParameter, parameter);
    }

    @Override
    public T visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return this.delegate.visitVariable(extendedElement, parameter);
    }

    @Override
    public T visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return this.delegate.visitUnknown(extendedElement, parameter);
    }
}
