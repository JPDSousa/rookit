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
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.source.field.FieldAdapter;
import org.rookit.auto.source.method.MethodSourceAdapter;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
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
    @SuppressWarnings("FieldNotUsedInToString")
    private final String separator;

    @Inject
    private JavaPoetTypeSourceFactory(
            final Executor executor,
            final FieldAdapter<FieldSpec> fieldAdapter,
            final MethodSourceAdapter<MethodSpec> methodAdapter,
            final TypeReferenceSourceAdapter<TypeName> typeReferenceAdapter,
            final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter,
            final JavaPoetMutableAnnotatableFactory annotatableFactory,
            @Separator final String separator) {
        this.executor = executor;
        this.fieldAdapter = fieldAdapter;
        this.methodAdapter = methodAdapter;
        this.typeReferenceAdapter = typeReferenceAdapter;
        this.typeVariableAdapter = typeVariableAdapter;
        this.annotatableFactory = annotatableFactory;
        this.separator = separator;
    }


    @Override
    public MutableTypeSource createMutableClass(final Identifier identifier) {
        return createTypeSource(identifier, TypeSpec.classBuilder(fromIdentifier(identifier)).build());
    }

    private ClassName fromIdentifier(final Identifier identifier) {
        return ClassName.get(identifier.packageElement().getQualifiedName().toString(), identifier.name());
    }

    @Override
    public MutableTypeSource createMutableInterface(final Identifier identifier) {
        return createTypeSource(identifier, TypeSpec.interfaceBuilder(fromIdentifier(identifier)).build());
    }

    @Override
    public MutableTypeSource createMutableAnnotation(final Identifier identifier) {
        return createTypeSource(identifier, TypeSpec.annotationBuilder(fromIdentifier(identifier)).build());
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

    private MutableTypeSource createTypeSource(final Identifier identifier, final TypeSpec source) {
        return new JavaPoetTypeSource(
                identifier,
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

    @Override
    public String toString() {
        return "JavaPoetTypeSourceFactory{" +
                "executor=" + this.executor +
                ", fieldAdapter=" + this.fieldAdapter +
                ", methodAdapter=" + this.methodAdapter +
                ", typeReferenceAdapter=" + this.typeReferenceAdapter +
                ", typeVariableAdapter=" + this.typeVariableAdapter +
                ", annotatableFactory=" + this.annotatableFactory +
                "}";
    }

}
