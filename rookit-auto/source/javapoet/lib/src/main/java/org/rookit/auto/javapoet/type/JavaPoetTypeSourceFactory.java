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

import com.google.inject.Inject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatableFactory;
import org.rookit.auto.source.field.FieldAdapter;
import org.rookit.auto.source.method.MethodSourceAdapter;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;
import org.rookit.utils.guice.Separator;

import java.util.concurrent.Executor;
import java.util.function.Function;

import static java.lang.String.format;

final class JavaPoetTypeSourceFactory implements TypeSourceFactory {

    private final Executor executor;
    private final FieldAdapter<FieldSpec> fieldAdapter;
    private final MethodSourceAdapter<MethodSpec> methodAdapter;
    private final MethodSourceFactory methodFactory;
    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter;
    private final JavaPoetMutableAnnotatableFactory annotatableFactory;
    private final String separator;
    private final ParameterSourceFactory parameterFactory;

    @Inject
    private JavaPoetTypeSourceFactory(
            final Executor executor,
            final FieldAdapter<FieldSpec> fieldAdapter,
            final MethodSourceAdapter<MethodSpec> methodAdapter,
            final MethodSourceFactory methodFactory,
            final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter,
            final JavaPoetMutableAnnotatableFactory annotatableFactory,
            @Separator final String separator,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final ParameterSourceFactory parameterFactory) {
        this.executor = executor;
        this.fieldAdapter = fieldAdapter;
        this.methodAdapter = methodAdapter;
        this.methodFactory = methodFactory;
        this.referenceAdapter = referenceAdapter;
        this.typeVariableAdapter = typeVariableAdapter;
        this.annotatableFactory = annotatableFactory;
        this.separator = separator;
        this.parameterFactory = parameterFactory;
    }

    private TypeSpec createBuilder(final Function<ClassName, TypeSpec.Builder> builderFunc,
                                           final TypeReferenceSource reference) {

        final TypeName typeName = this.referenceAdapter.adaptTypeReference(reference);

        if (typeName instanceof ClassName) {
            return builderFunc.apply((ClassName) typeName).build();
        }
        if (typeName instanceof ParameterizedTypeName) {
            final ParameterizedTypeName parameterized = (ParameterizedTypeName) typeName;
            final TypeSpec.Builder builder = builderFunc.apply(parameterized.rawType);

            for (final TypeName typeArgument : parameterized.typeArguments) {
                if (typeArgument instanceof TypeVariableName) {
                    builder.addTypeVariable((TypeVariableName) typeArgument);
                } else {
                    final String errMsg = format(
                            "%s contains a type parameter '%s' which is not variable, and therefore cannot "
                                    + "be used in the type declaration.",
                            typeName,
                            typeArgument);
                    throw new IllegalArgumentException(errMsg);
                }
            }

            return builder.build();
        }

        final String errMsg = format("%s cannot be used to build a new type.", typeName);
        throw new IllegalArgumentException(errMsg);
    }


    @Override
    public MutableTypeSource createMutableClass(final TypeReferenceSource reference) {

        return createTypeSource(reference, createBuilder(TypeSpec::classBuilder, reference));
    }

    @Override
    public MutableTypeSource createMutableInterface(final TypeReferenceSource reference) {

        return createTypeSource(reference, createBuilder(TypeSpec::interfaceBuilder, reference));
    }

    @Override
    public MutableTypeSource createMutableAnnotation(final TypeReferenceSource reference) {

        return createTypeSource(reference, createBuilder(TypeSpec::annotationBuilder, reference));
    }

    @Override
    public MutableTypeSource makeMutable(final TypeSource typeSource) {

        if (typeSource instanceof MutableTypeSource) {
            return (MutableTypeSource) typeSource;
        }

        // TODO use failsafe
        final String errMsg = format("Cannot make %s mutable.", typeSource);
        throw new IllegalArgumentException(errMsg);
    }

    private MutableTypeSource createTypeSource(final TypeReferenceSource reference, final TypeSpec source) {

        return new JavaPoetTypeSource(
                reference,
                source.toBuilder(),
                this.executor,
                this.fieldAdapter,
                this.methodAdapter,
                this.typeVariableAdapter,
                this.referenceAdapter,
                this.methodFactory.createMutableConstructor(),
                this.annotatableFactory.createEmpty(),
                this.separator,
                this.parameterFactory);
    }

}
