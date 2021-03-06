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
package org.rookit.convention.auto.source.method;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.javax.visitor.TypeBasedMethodVisitor;

final class TypeBasedMethodVisitorDecorator<P> implements TypeBasedMethodVisitor<P> {

    private final ExtendedTypeMirror type;
    private final ConventionTypeElementVisitor<StreamEx<MethodSource>, P> upstream;

    TypeBasedMethodVisitorDecorator(final ExtendedTypeMirror type,
                                    final ConventionTypeElementVisitor<StreamEx<MethodSource>, P> upstream) {
        this.type = type;
        this.upstream = upstream;
    }

    @Override
    public ExtendedTypeMirror type() {
        return this.type;
    }

    @Override
    public StreamEx<MethodSource> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return packageElement.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<MethodSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return extendedType.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<MethodSource> visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        return extendedExecutable.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<MethodSource> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                     final P parameter) {
        return extendedParameter.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<MethodSource> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return extendedElement.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<MethodSource> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return extendedElement.accept(this.upstream, parameter);
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(final ConventionTypeElement element, final P parameter) {
        return element.accept(this.upstream, parameter);
    }

    @Override
    public String toString() {
        return "TypeBasedMethodVisitorDecorator{" +
                "type=" + this.type +
                ", upstream=" + this.upstream +
                "}";
    }
}
