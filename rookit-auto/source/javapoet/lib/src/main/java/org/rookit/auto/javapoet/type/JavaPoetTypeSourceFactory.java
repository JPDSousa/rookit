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
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatableFactory;
import org.rookit.auto.source.field.FieldAdapter;
import org.rookit.auto.source.method.MethodSourceAdapter;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;
import org.rookit.utils.guice.Separator;

import java.util.concurrent.Executor;

import static java.lang.String.format;

final class JavaPoetTypeSourceFactory implements TypeSourceFactory {

    private final Executor executor;
    private final FieldAdapter<FieldSpec> fieldAdapter;
    private final MethodSourceAdapter<MethodSpec> methodAdapter;
    private final TypeReferenceSourceAdapter<TypeName> typeReferenceAdapter;
    private final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter;
    private final JavaPoetMutableAnnotatableFactory annotatableFactory;
    private final String separator;
    private final TypeReferenceSourceAdapter<ClassName> referenceAdapter;

    @Inject
    private JavaPoetTypeSourceFactory(
            final Executor executor,
            final FieldAdapter<FieldSpec> fieldAdapter,
            final MethodSourceAdapter<MethodSpec> methodAdapter,
            final TypeReferenceSourceAdapter<TypeName> typeReferenceAdapter,
            final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter,
            final JavaPoetMutableAnnotatableFactory annotatableFactory,
            @Separator final String separator,
            final TypeReferenceSourceAdapter<ClassName> referenceAdapter) {
        this.executor = executor;
        this.fieldAdapter = fieldAdapter;
        this.methodAdapter = methodAdapter;
        this.typeReferenceAdapter = typeReferenceAdapter;
        this.typeVariableAdapter = typeVariableAdapter;
        this.annotatableFactory = annotatableFactory;
        this.separator = separator;
        this.referenceAdapter = referenceAdapter;
    }


    @Override
    public MutableTypeSource createMutableClass(final TypeReferenceSource reference) {

        final ClassName className = this.referenceAdapter.adaptTypeReference(reference);
        return createTypeSource(reference, TypeSpec.classBuilder(className).build());
    }

    @Override
    public MutableTypeSource createMutableInterface(final TypeReferenceSource reference) {

        final ClassName className = this.referenceAdapter.adaptTypeReference(reference);
        return createTypeSource(reference, TypeSpec.interfaceBuilder(className).build());
    }

    @Override
    public MutableTypeSource createMutableAnnotation(final TypeReferenceSource reference) {

        final ClassName className = this.referenceAdapter.adaptTypeReference(reference);
        return createTypeSource(reference, TypeSpec.annotationBuilder(className).build());
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
                this.typeReferenceAdapter,
                this.annotatableFactory.createEmpty(),
                this.separator
        );
    }

}
