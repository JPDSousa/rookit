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
package org.rookit.auto.javapoet.parameter;

import com.google.inject.Inject;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatableFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.parameter.MutableParameterSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;

import static java.lang.String.format;

final class JavaPoetParameterFactory implements ParameterSourceFactory {

    private final JavaPoetMutableAnnotatableFactory annotatableFactory;
    private final TypeReferenceSourceAdapter<TypeName> typeNameAdapter;
    private final TypeReferenceSourceFactory referenceFactory;

    @Inject
    private JavaPoetParameterFactory(
            final JavaPoetMutableAnnotatableFactory annotatableFactory,
            final TypeReferenceSourceAdapter<TypeName> typeNameAdapter,
            final TypeReferenceSourceFactory referenceFactory) {
        this.annotatableFactory = annotatableFactory;
        this.typeNameAdapter = typeNameAdapter;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public MutableParameterSource makeMutable(final ParameterSource source) {

        if (source instanceof MutableParameterSource) {
            return (MutableParameterSource) source;
        }

        final String errMsg = format("Cannot make a %s out of a %s instance.", MutableParameterSource.class,
                                     source.getClass());
        throw new IllegalArgumentException(errMsg);
    }

    @Override
    public MutableParameterSource createMutable(
            final CharSequence name, final TypeReferenceSource type) {

        final TypeName typeName = this.typeNameAdapter.adaptTypeReference(type);
        return createFromJavaPoet(name, type, ParameterSpec.builder(typeName, name.toString()));
    }

    private MutableParameterSource createFromJavaPoet(final CharSequence name,
                                                      final TypeReferenceSource type,
                                                      final ParameterSpec.Builder builder) {

        return new JavaPoetParameter(
                this.annotatableFactory.createEmpty(),
                name,
                type,
                builder
        );
    }

    @Override
    public MutableParameterSource createMutable(final CharSequence name, final ExtendedTypeMirror type) {

        return createMutable(name, this.referenceFactory.create(type));
    }

    @Override
    public MutableParameterSource createMutable(final CharSequence name, final Class<?> type) {

        return createMutable(name, this.referenceFactory.fromClass(type));
    }

    @Override
    public MutableParameterSource createFromElement(final ExtendedVariableElement variable) {

        return createFromJavaPoet(
                variable.getSimpleName(),
                this.referenceFactory.create(variable.asType()),
                ParameterSpec.get(variable).toBuilder()
        );
    }

    @Override
    public MutableParameterSource createFromField(final FieldSource field) {

        return createMutable(field.name(), field.type())
                .addAnnotations(field.annotations());
    }

}
