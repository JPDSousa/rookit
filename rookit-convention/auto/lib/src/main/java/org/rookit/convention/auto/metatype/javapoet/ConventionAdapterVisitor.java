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
package org.rookit.convention.auto.metatype.javapoet;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.utils.adapt.Adapter;

final class ConventionAdapterVisitor<T, P> implements ConventionTypeElementVisitor<StreamEx<T>, P> {

    private final ConventionTypeElementVisitor<StreamEx<T>, P> upstream;
    private final Adapter<ConventionTypeElement> conventionAdapter;

    ConventionAdapterVisitor(final ConventionTypeElementVisitor<StreamEx<T>, P> upstream,
                             final Adapter<ConventionTypeElement> conventionAdapter) {
        this.upstream = upstream;
        this.conventionAdapter = conventionAdapter;
    }

    @Override
    public StreamEx<T> visitConventionType(final ConventionTypeElement element, final P parameter) {
        this.conventionAdapter.adapt(element).accept(this.upstream, parameter);
        return this.upstream.visitConventionType(this.conventionAdapter.adapt(element), parameter);
    }

    @Override
    public StreamEx<T> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return packageElement.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<T> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return extendedType.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<T> visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return extendedExecutable.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<T> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return extendedParameter.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<T> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return extendedElement.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<T> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return extendedElement.accept(this.upstream, parameter);
    }

    @Override
    public String toString() {
        return "ConventionAdapterVisitor{" +
                "upstream=" + this.upstream +
                ", conventionAdapter=" + this.conventionAdapter +
                "}";
    }
}
