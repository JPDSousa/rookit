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
package org.rookit.auto.javapoet.method;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatableFactory;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.mirror.variable.ExtendedTypeVariable;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceAdapter;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.parameter.ParameterSourceAdapter;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;

import javax.lang.model.element.Modifier;
import java.util.Set;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.DEFAULT;

final class JavaPoetMethodFactory implements MethodSourceFactory {

    private final ParameterSourceFactory parameterFactory;
    private final ParameterSourceAdapter<ParameterSpec> parameterAdapter;
    private final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter;
    private final TypeVariableSourceFactory typeVariableFactory;

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final TypeReferenceSourceFactory referenceFactory;

    private final JavaPoetMutableAnnotatableFactory annotatableFactory;

    private final ArbitraryCodeSourceAdapter<CodeBlock> codeAdapter;

    @Inject
    private JavaPoetMethodFactory(
            final ParameterSourceFactory parameterFactory,
            final ParameterSourceAdapter<ParameterSpec> parameterAdapter,
            final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter,
            final TypeVariableSourceFactory typeVariableFactory,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final TypeReferenceSourceFactory referenceFactory,
            final JavaPoetMutableAnnotatableFactory annotatableFactory,
            final ArbitraryCodeSourceAdapter<CodeBlock> codeAdapter) {
        this.parameterFactory = parameterFactory;
        this.parameterAdapter = parameterAdapter;
        this.typeVariableAdapter = typeVariableAdapter;
        this.typeVariableFactory = typeVariableFactory;
        this.referenceAdapter = referenceAdapter;
        this.referenceFactory = referenceFactory;
        this.annotatableFactory = annotatableFactory;
        this.codeAdapter = codeAdapter;
    }

    @Override
    public MutableMethodSource from(final ExtendedExecutableElement method) {

        final ExtendedElement enclosingClass = method.getEnclosingElement();
        if (enclosingClass.getModifiers().contains(Modifier.FINAL)) {
            throw new IllegalArgumentException("Cannot override method on final class " + enclosingClass);
        }

        final Set<Modifier> modifiers = method.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)
                || modifiers.contains(Modifier.FINAL)
                || modifiers.contains(Modifier.STATIC)) {
            throw new IllegalArgumentException("cannot override method with modifiers: " + modifiers);
        }

        final String methodName = method.getSimpleName().toString();
        final MutableMethodSource spec = createMutableMethod(methodName)
                .addModifiers(method.getModifiers())
                .removeModifiers(ImmutableList.of(ABSTRACT, DEFAULT))
                .withReturnType(this.referenceFactory.create(method.getReturnType()))
                .addParameters(this.parameterFactory.parametersFor(method))
                .varArgs(method.isVarArgs())
                .addThrownTypes(method.getThrownTypes());

        for (final ExtendedTypeParameterElement typeParameterElement : method.getTypeParameters()) {
            final ExtendedTypeVariable variable = (ExtendedTypeVariable) typeParameterElement.asType();
            spec.addTypeVariable(this.typeVariableFactory.createFromJavaX(variable));
        }

        return spec;
    }

    @Override
    public MutableMethodSource createMutableConstructor() {

        return createJavaMethod(MethodSpec.constructorBuilder());
    }

    private MutableMethodSource createJavaMethod(final MethodSpec.Builder builder) {

        return new JavaPoetMethod(
                builder,
                this.parameterAdapter,
                this.typeVariableAdapter,
                this.referenceAdapter,
                this.referenceFactory,
                this.codeAdapter,
                this.annotatableFactory.createEmpty(),
                ImmutableSet.of(),
                ImmutableSet.of());
    }

    @Override
    public MutableMethodSource createMutableMethod(final CharSequence name) {

        return createJavaMethod(MethodSpec.methodBuilder(name.toString()));
    }

    @Override
    public MutableMethodSource createMutableOverride(final ExtendedExecutableElement method) {

        return from(method)
                .addAnnotationByClass(Override.class);
    }

}
