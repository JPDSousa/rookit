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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;

import java.util.Collection;
import java.util.function.Function;

// This type is useful to facade a multiple method visitors into a single one.
final class MultiVisitor<T, P> implements ExtendedElementVisitor<StreamEx<T>, P> {

    private final Collection<? extends ExtendedElementVisitor<StreamEx<T>, P>> visitors;

    @Inject
    MultiVisitor(final Collection<? extends ExtendedElementVisitor<StreamEx<T>, P>> visitors) {
        this.visitors = ImmutableList.copyOf(visitors);
    }

    private StreamEx<T> visitMulti(final Function<ExtendedElementVisitor<StreamEx<T>, P>, StreamEx<T>> function) {
        return StreamEx.of(this.visitors)
                .flatMap(function);
    }

    @Override
    public StreamEx<T> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return visitMulti(factory -> factory.visitPackage(packageElement, parameter));
    }

    @Override
    public StreamEx<T> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return visitMulti(factory -> factory.visitType(extendedType, parameter));
    }

    @Override
    public StreamEx<T> visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return visitMulti(factory -> visitExecutable(extendedExecutable, parameter));
    }

    @Override
    public StreamEx<T> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return visitMulti(factory -> factory.visitTypeParameter(extendedParameter, parameter));
    }

    @Override
    public StreamEx<T> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return visitMulti(factory -> factory.visitVariable(extendedElement, parameter));
    }

    @Override
    public StreamEx<T> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return visitMulti(factory -> factory.visitUnknown(extendedElement, parameter));
    }

    @Override
    public String toString() {
        return "MultiVisitor{" +
                "visitors=" + this.visitors +
                "}";
    }
}
