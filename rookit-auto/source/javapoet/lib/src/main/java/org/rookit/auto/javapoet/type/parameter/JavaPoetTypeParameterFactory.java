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
package org.rookit.auto.javapoet.type.parameter;

import com.google.inject.Inject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.utils.optional.OptionalFactory;

import java.util.function.Function;

final class JavaPoetTypeParameterFactory implements TypeParameterSourceFactory {

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final OptionalFactory optionalFactory;

    @Inject
    private JavaPoetTypeParameterFactory(
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final OptionalFactory optionalFactory) {
        this.referenceAdapter = referenceAdapter;
        this.optionalFactory = optionalFactory;
    }

    private TypeParameterSource newInstance(final TypeName parameterizable, final TypeName[] parameters) {

        final ClassName baseType;

        if (parameterizable instanceof ClassName) {
            baseType = (ClassName) parameterizable;
        }
        else if (parameterizable instanceof ParameterizedTypeName) {
            baseType = ((ParameterizedTypeName) parameterizable).rawType;
        }
        else {
            throw new IllegalArgumentException(parameterizable + " is not a parameterizable type.");
        }

        return new BaseJavaPoetTypeParameter(
                this.optionalFactory,
                baseType,
                parameters
        );
    }

    @Override
    public TypeParameterSource create(
            final ExtendedTypeElement erasure, final ExtendedTypeElement... parameters) {

        return new BaseJavaPoetTypeParameter(
                this.optionalFactory,
                ClassName.get(erasure),
                createParams(parameters)
        );
    }

    private <T> TypeName[] createParams(final T[] original, final Function<T, TypeName> transformation) {

        final int length = original.length;
        final TypeName[] javaPoetParams = new TypeName[length];
        for (int i = 0; i < length; i++) {
            javaPoetParams[i] = transformation.apply(original[i]);
        }

        return javaPoetParams;
    }

    private TypeName[] createParams(final ExtendedTypeElement[] original) {

        return createParams(original, ClassName::get);
    }

    private TypeName[] createParams(final TypeReferenceSource[] original) {

        return createParams(original, this.referenceAdapter::adaptTypeReference);
    }

    @Override
    public TypeParameterSource create(
            final ExtendedTypeMirror erasure, final ExtendedTypeElement... parameters) {

        return newInstance(
                TypeName.get(erasure),
                createParams(parameters)
        );
    }

    @Override
    public TypeParameterSource create(
            final TypeReferenceSource erasure, final ExtendedTypeElement... parameters) {


        return newInstance(
                this.referenceAdapter.adaptTypeReference(erasure),
                createParams(parameters)
        );
    }

    @Override
    public TypeParameterSource create(
            final TypeReferenceSource erasure, final TypeReferenceSource... parameters) {

        return newInstance(
                this.referenceAdapter.adaptTypeReference(erasure),
                createParams(parameters)
        );
    }

    @Override
    public TypeParameterSource create(
            final Class<?> erasure, final TypeReferenceSource... parameters) {

        return new BaseJavaPoetTypeParameter(
                this.optionalFactory, ClassName.get(erasure),
                createParams(parameters)
        );
    }

}
