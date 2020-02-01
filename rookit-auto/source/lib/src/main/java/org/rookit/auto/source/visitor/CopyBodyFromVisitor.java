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
package org.rookit.auto.source.visitor;

import com.google.common.collect.ImmutableList;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;

import java.util.function.Function;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class CopyBodyFromVisitor<P> implements ExtendedElementVisitor<StreamEx<TypeSource>, P> {

    private final TypeSourceFactory typeFactory;
    private final MethodSourceFactory methodFactory;
    private final Function<ExtendedElement, ExtendedTypeElement> extractionFunction;
    private final ExtendedElementVisitor<StreamEx<TypeSource>, P> upstream;

    CopyBodyFromVisitor(
            final TypeSourceFactory typeFactory,
            final MethodSourceFactory methodFactory,
            final Function<ExtendedElement, ExtendedTypeElement> extractionFunction,
            final ExtendedElementVisitor<StreamEx<TypeSource>, P> upstream) {
        this.typeFactory = typeFactory;
        this.methodFactory = methodFactory;
        this.extractionFunction = extractionFunction;
        this.upstream = upstream;
    }

    private Iterable<MethodSource> buildMethodsFrom(final ExtendedElement construct) {
        final ExtendedTypeElement annotationMeta = this.extractionFunction.apply(construct);

        return StreamEx.of(annotationMeta.getEnclosedElements())
                .select(ExtendedExecutableElement.class)
                .map(this::buildMethodFrom)
                .collect(toImmutableList());
    }

    private MethodSource buildMethodFrom(final ExtendedExecutableElement property) {
        return this.methodFactory.from(property)
                .makePublic()
                .makeAbstract()
                .defaultValue("$L", ImmutableList.of(property.getDefaultValue().getValue()));
    }

    @Override
    public StreamEx<TypeSource> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return packageElement.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addMethods(buildMethodsFrom(packageElement)));
    }

    @Override
    public StreamEx<TypeSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return extendedType.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedType)));
    }

    @Override
    public StreamEx<TypeSource> visitExecutable(final ExtendedExecutableElement extendedExecutable,
                                                      final P parameter) {
        return extendedExecutable.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedExecutable)));
    }

    @Override
    public StreamEx<TypeSource> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                         final P parameter) {
        return extendedParameter.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedParameter)));
    }

    @Override
    public StreamEx<TypeSource> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return extendedElement.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedElement)));
    }

    @Override
    public StreamEx<TypeSource> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return extendedElement.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedElement)));
    }

    @Override
    public String toString() {
        return "CopyBodyFromVisitor{" +
                "typeFactory=" + this.typeFactory +
                ", methodFactory=" + this.methodFactory +
                ", extractionFunction=" + this.extractionFunction +
                ", upstream=" + this.upstream +
                "}";
    }

}
