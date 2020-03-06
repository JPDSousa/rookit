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
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;

final class Parameter1GetterMethodVisitor<P> implements ConventionTypeElementVisitor<StreamEx<MethodSource>, P>,
        StreamExtendedElementVisitor<MethodSource, P> {

    private final TypeParameterSourceFactory parameterFactory;
    private final MethodSourceFactory methodSourceFactory;
    private final ExtendedExecutableElement method;

    Parameter1GetterMethodVisitor(
            final TypeParameterSourceFactory parameterFactory,
            final MethodSourceFactory methodSourceFactory,
            final ExtendedExecutableElement method) {
        this.parameterFactory = parameterFactory;
        this.methodSourceFactory = methodSourceFactory;
        this.method = method;
    }

    @Override
    public StreamEx<MethodSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        // TODO we might need to consider the visited type, otherwise this is a constant value.
        return createMethod(extendedType);
    }

    private StreamEx<MethodSource> createMethod(final ExtendedTypeElement extendedType) {
        final TypeReferenceSource returnType = this.parameterFactory.create(this.method.getReturnType(), extendedType);

        return StreamEx.of(
                this.methodSourceFactory.createMutableOverride(this.method)
                        .returnInstanceField(returnType, this.method.getSimpleName())
        );
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(
            final ConventionTypeElement element, final P parameter) {
        return createMethod(element);
    }

    @Override
    public String toString() {
        return "Parameter1GetterMethodVisitor{" +
                "parameterFactory=" + this.parameterFactory +
                ", methodSourceFactory=" + this.methodSourceFactory +
                ", method=" + this.method +
                "}";
    }

}
