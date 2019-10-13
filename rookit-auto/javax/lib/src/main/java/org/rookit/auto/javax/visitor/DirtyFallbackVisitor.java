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
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;

@Deprecated // since relying on an empty stream to assume that the factory is not compatible is wrong!
final class DirtyFallbackVisitor<T, P> implements ExtendedElementVisitor<StreamEx<T>, P> {

    private final ExtendedElementVisitor<StreamEx<T>, P> primaryVisitor;
    private final ExtendedElementVisitor<StreamEx<T>, P> secondaryVisitor;

    DirtyFallbackVisitor(final ExtendedElementVisitor<StreamEx<T>, P> entityFactory,
                         final ExtendedElementVisitor<StreamEx<T>, P> propertyFactory) {
        this.primaryVisitor = entityFactory;
        this.secondaryVisitor = propertyFactory;
    }

    @Override
    public StreamEx<T> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return this.primaryVisitor.visitPackage(packageElement, parameter)
                .ifEmpty(this.secondaryVisitor.visitPackage(packageElement, parameter));
    }

    @Override
    public StreamEx<T> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return this.primaryVisitor.visitType(extendedType, parameter)
                .ifEmpty(this.secondaryVisitor.visitType(extendedType, parameter));
    }

    @Override
    public StreamEx<T> visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return this.primaryVisitor.visitExecutable(extendedExecutable, parameter)
                .ifEmpty(this.secondaryVisitor.visitExecutable(extendedExecutable, parameter));
    }

    @Override
    public StreamEx<T> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return this.primaryVisitor.visitTypeParameter(extendedParameter, parameter)
                .ifEmpty(this.secondaryVisitor.visitTypeParameter(extendedParameter, parameter));
    }

    @Override
    public StreamEx<T> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return this.primaryVisitor.visitVariable(extendedElement, parameter)
                .ifEmpty(this.secondaryVisitor.visitVariable(extendedElement, parameter));
    }

    @Override
    public StreamEx<T> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return this.primaryVisitor.visitUnknown(extendedElement, parameter)
                .ifEmpty(this.secondaryVisitor.visitUnknown(extendedElement, parameter));
    }

    @Override
    public String toString() {
        return "DirtyFallbackVisitor{" +
                "primaryVisitor=" + this.primaryVisitor +
                ", secondaryVisitor=" + this.secondaryVisitor +
                "}";
    }
}
