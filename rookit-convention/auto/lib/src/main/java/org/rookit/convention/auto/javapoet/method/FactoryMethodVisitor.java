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
package org.rookit.convention.auto.javapoet.method;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;

import java.util.Collection;
import java.util.List;

import static javax.lang.model.element.Modifier.STATIC;

final class FactoryMethodVisitor<P> implements StreamExtendedElementVisitor<MethodSource, P>,
        ConventionTypeElementVisitor<StreamEx<MethodSource>, P> {

    private final ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterFactory;
    private final TypeVariableSource variableName;
    private final MethodSourceFactory methodSourceFactory;

    FactoryMethodVisitor(
            final ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterFactory,
            final TypeVariableSource variableName,
            final MethodSourceFactory methodSourceFactory) {
        this.parameterFactory = parameterFactory;
        this.variableName = variableName;
        this.methodSourceFactory = methodSourceFactory;
    }

    @Override
    public StreamEx<MethodSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return createMethodSource(extendedType, parameter);
    }

    private StreamEx<MethodSource> createMethodSource(final ExtendedTypeElement extendedType, final P parameter) {
        final Collection<ParameterSource> parameters = extendedType.accept(this.parameterFactory, parameter)
                .toImmutableList();

        final List<CharSequence> paramValues = StreamEx.of(parameters)
                .map(ParameterSource::name)
                .toImmutableList();

        return StreamEx.of(
                this.methodSourceFactory.createMutableMethod("createMutable")
                        .makePublic()
                        .addModifier(STATIC)
                        .addTypeVariable(this.variableName)
                        .addParameters(parameters)
                        .returnNewObject(extendedType, paramValues)
        );
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(final ConventionTypeElement element, final P parameter) {
        return createMethodSource(element, parameter);
    }

    @Override
    public String toString() {
        return "FactoryMethodVisitor{" +
                "parameterFactory=" + this.parameterFactory +
                ", variableName=" + this.variableName +
                ", methodSourceFactory=" + this.methodSourceFactory +
                "}";
    }

}
