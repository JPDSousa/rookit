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
package org.rookit.auto.javapoet.type;

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.function.Function;

final class CopyBodyFromVisitor<P> implements ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> {

    private final Function<ExtendedElement, TypeMirror> extractionFunction;
    private final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> upstream;
    private final Types types;

    CopyBodyFromVisitor(final Function<ExtendedElement, TypeMirror> extractionFunction,
                        final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> upstream,
                        final Types types) {
        this.extractionFunction = extractionFunction;
        this.upstream = upstream;
        this.types = types;
    }

    private Iterable<MethodSpec> buildMethodsFrom(final ExtendedElement construct) {
        final TypeMirror typeMirror = this.extractionFunction.apply(construct);
        final ImmutableList.Builder<MethodSpec> specs = ImmutableList.builder();
        final Element annotationMeta = this.types.asElement(typeMirror);

        for (final Element property : annotationMeta.getEnclosedElements()) {
            if (property instanceof ExecutableElement) {
                final ExecutableElement executableProperty = (ExecutableElement) property;
                specs.add(MethodSpec.methodBuilder(executableProperty.getSimpleName().toString())
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(TypeName.get(executableProperty.getReturnType()))
                        .defaultValue("$L", executableProperty.getDefaultValue().getValue())
                        .build());
            }
        }

        return specs.build();
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return this.upstream.visitPackage(packageElement, parameter)
                .map(builder -> builder.addMethods(buildMethodsFrom(packageElement)));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return this.upstream.visitType(extendedType, parameter)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedType)));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitExecutable(final ExtendedExecutableElement extendedExecutable,
                                                      final P parameter) {
        return this.upstream.visitExecutable(extendedExecutable, parameter)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedExecutable)));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                         final P parameter) {
        return this.upstream.visitTypeParameter(extendedParameter, parameter)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedParameter)));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return this.upstream.visitVariable(extendedElement, parameter)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedElement)));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return this.upstream.visitUnknown(extendedElement, parameter)
                .map(builder -> builder.addMethods(buildMethodsFrom(extendedElement)));
    }

    @Override
    public String toString() {
        return "CopyBodyFromVisitor{" +
                "extractionFunction=" + this.extractionFunction +
                ", upstream=" + this.upstream +
                ", types=" + this.types +
                "}";
    }
}
