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

import com.google.inject.Inject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceAdapter;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceAdapter;
import org.rookit.auto.source.parameter.ParameterSourceAdapter;
import org.rookit.auto.source.type.annotation.AnnotationSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;

import static com.squareup.javapoet.MethodSpec.constructorBuilder;
import static com.squareup.javapoet.MethodSpec.methodBuilder;

final class JavaPoetMethodAdapter implements MethodSourceAdapter<MethodSpec> {

    private final AnnotationSourceAdapter<AnnotationSpec> annotationAdapter;
    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final ArbitraryCodeSourceAdapter<CodeBlock> codeAdapter;
    private final TypeVariableSourceAdapter<TypeVariableName> variableAdapter;
    private final ParameterSourceAdapter<ParameterSpec> parameterAdapter;

    @Inject
    private JavaPoetMethodAdapter(
            final AnnotationSourceAdapter<AnnotationSpec> annotationAdapter,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final ArbitraryCodeSourceAdapter<CodeBlock> codeAdapter,
            final TypeVariableSourceAdapter<TypeVariableName> variableAdapter,
            final ParameterSourceAdapter<ParameterSpec> parameterAdapter) {
        this.annotationAdapter = annotationAdapter;
        this.referenceAdapter = referenceAdapter;
        this.codeAdapter = codeAdapter;
        this.variableAdapter = variableAdapter;
        this.parameterAdapter = parameterAdapter;
    }

    @Override
    public MethodSpec adaptMethod(final MethodSource method) {


        final MethodSpec.Builder builder = method.isConstructor()
                ? constructorBuilder()
                : methodBuilder(method.name().toString());

        method.parameters().stream()
                .map(this.parameterAdapter::adaptParameter)
                .forEach(builder::addParameter);

        method.returnType()
                .map(this.referenceAdapter::adaptTypeReference)
                .ifPresent(builder::returns);

        builder.varargs(method.isVarArgs());

        method.typeVariables().stream()
                .map(this.variableAdapter::adaptTypeVariable)
                .forEach(builder::addTypeVariable);

        method.defaultValue()
                .map(this.codeAdapter::adaptArbitraryCodeBlock)
                .ifPresent(builder::defaultValue);

        builder.addModifiers(method.modifiers());

        method.annotations().stream()
                .map(this.annotationAdapter::adaptAnnotation)
                .forEach(builder::addAnnotation);

        method.thrownTypes().stream()
                .map(this.referenceAdapter::adaptTypeReference)
                .forEach(builder::addException);

        method.code().stream()
                .map(this.codeAdapter::adaptArbitraryCodeBlock)
                .forEach(builder::addStatement);

        return builder.build();
    }

}
