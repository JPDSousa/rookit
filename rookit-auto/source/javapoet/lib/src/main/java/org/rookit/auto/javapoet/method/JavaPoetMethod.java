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

import com.google.common.base.Joiner;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatable;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceAdapter;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableCollection;

final class JavaPoetMethod implements MutableMethodSource {

    private static final Object[] ARRAY_INIT = new Object[0];

    private final MethodSpec.Builder builder;

    private final ParameterSourceAdapter<ParameterSpec> parameterAdapter;

    private final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter;

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final TypeReferenceSourceFactory referenceFactory;

    private final JavaPoetMutableAnnotatable annotatable;

    private final Set<Modifier> modifiers;

    private final Set<TypeReferenceSource> thrownTypes;

    JavaPoetMethod(
            final MethodSpec.Builder builder,
            final ParameterSourceAdapter<ParameterSpec> parameterAdapter,
            final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final TypeReferenceSourceFactory referenceFactory,
            final JavaPoetMutableAnnotatable annotatable,
            final Set<Modifier> modifiers,
            final Set<TypeReferenceSource> thrownTypes) {
        this.builder = builder;
        this.annotatable = annotatable;
        this.parameterAdapter = parameterAdapter;
        this.typeVariableAdapter = typeVariableAdapter;
        this.referenceAdapter = referenceAdapter;
        this.referenceFactory = referenceFactory;
        this.modifiers = new LinkedHashSet<>(modifiers);
        this.thrownTypes = new LinkedHashSet<>(thrownTypes);
    }

    MethodSpec build() {
        final MethodSpec.Builder builder = this.builder.build()
                .toBuilder();

        builder.addModifiers(this.modifiers);
        builder.addAnnotations(this.annotatable.annotationSpecs());
        this.thrownTypes.stream()
                .map(this.referenceAdapter::adaptTypeReference)
                .forEach(builder::addException);

        return builder.build();
    }

    @Override
    public MutableMethodSource addParameter(final ParameterSource parameter) {

        this.builder.addParameter(this.parameterAdapter.adaptParameter(parameter));
        return this;
    }

    @Override
    public MutableMethodSource assignParametersToFields(final Iterable<ParameterSource> parameters) {

        addParameters(parameters);
        for (final ParameterSource parameter : parameters) {
            final CharSequence parameterName = parameter.name();
            this.builder.addStatement("this.$L = $L", parameterName, parameterName);
        }
        return this;
    }

    @Override
    public MutableMethodSource returnNewObject(
            final TypeReferenceSource objectType, final Iterable<String> parameters) {

        final String paramString = Joiner.on(",")
                .join(parameters);
        final TypeName typeName = this.referenceAdapter.adaptTypeReference(objectType);
        this.builder.addStatement("return new $T($L)", typeName, paramString);
        this.builder.returns(typeName);
        return this;
    }

    @Override
    public MutableMethodSource returnNewObject(
            final ExtendedTypeElement objectType, final Iterable<String> parameters) {

        return returnNewObject(this.referenceFactory.create(objectType), parameters);
    }

    @Override
    public MutableMethodSource returnInstanceField(final TypeReferenceSource fieldType, final CharSequence fieldName) {

        final TypeName typeName = this.referenceAdapter.adaptTypeReference(fieldType);
        this.builder.returns(typeName);
        this.builder.addStatement("return this.$L", fieldName);
        return this;
    }

    @Override
    public MutableMethodSource returnInstanceField(
            final ExtendedTypeMirror fieldType, final CharSequence fieldName) {

        return returnInstanceField(this.referenceFactory.create(fieldType), fieldName);
    }

    @Override
    public MutableMethodSource withReturnType(final TypeReferenceSource reference) {

        this.builder.returns(this.referenceAdapter.adaptTypeReference(reference));
        return this;
    }

    @Override
    public MutableMethodSource returnMethodCall(
            final TypeReferenceSource returnType, final String delegateCall, final List<Object> args) {

        final TypeName typeName = this.referenceAdapter.adaptTypeReference(returnType);
        this.builder.returns(typeName);
        return returnMethodCall(delegateCall, args);
    }

    @Override
    public MutableMethodSource returnMethodCall(final String delegateCall, final List<Object> args) {

        this.builder.addStatement("return " + delegateCall, args.toArray(ARRAY_INIT));
        return this;
    }

    @Override
    public MutableMethodSource defaultValue(final String format, final List<Object> args) {

        this.builder.defaultValue(format, args.toArray(ARRAY_INIT));
        return this;
    }

    @Override
    public MutableMethodSource addVariable(
            final TypeReferenceSource type,
            final CharSequence name,
            final String initialization,
            final List<Object> args) {

        final TypeName typeName = this.referenceAdapter.adaptTypeReference(type);
        this.builder.addStatement("$T $L = " + initialization, typeName, name, args.toArray(ARRAY_INIT));
        return this;
    }

    @Override
    public MutableMethodSource addStatement(final String statement, final List<Object> args) {

        this.builder.addStatement(statement, args.toArray(ARRAY_INIT));
        return this;
    }

    @Override
    public MutableMethodSource varArgs(final boolean varArgs) {
        this.builder.varargs(varArgs);
        return self();
    }

    @Override
    public MutableMethodSource addThrownType(final TypeReferenceSource exceptionType) {
        this.thrownTypes.add(exceptionType);
        return self();
    }

    @Override
    public MutableMethodSource addThrownType(final ExtendedTypeMirror exceptionType) {
        this.thrownTypes.add(this.referenceFactory.create(exceptionType));
        return self();
    }

    @Override
    public MutableMethodSource self() {
        return this;
    }

    @Override
    public MutableMethodSource addAnnotation(final AnnotationSource annotation) {

        this.annotatable.addAnnotation(annotation);
        return this;
    }

    @Override
    public MutableMethodSource addAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotatable.addAnnotationByClass(annotation);
        return this;
    }

    @Override
    public MutableMethodSource removeAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotatable.removeAnnotationByClass(annotation);
        return this;
    }

    @Override
    public Collection<AnnotationSource> annotations() {

        return this.annotatable.annotations();
    }

    @Override
    public MutableMethodSource removeModifiers(final Collection<Modifier> modifiers) {

        this.modifiers.removeAll(modifiers);
        return this;
    }

    @Override
    public MutableMethodSource resetVisibility() {

        this.modifiers.clear();
        return this;
    }

    @Override
    public MutableMethodSource addModifiers(final Collection<Modifier> modifiers) {

        this.modifiers.addAll(modifiers);
        return this;
    }

    @Override
    public Collection<Modifier> modifiers() {

        return unmodifiableCollection(this.modifiers);
    }

    @Override
    public MutableMethodSource addTypeVariable(final TypeVariableSource typeVariable) {

        this.builder.addTypeVariable(this.typeVariableAdapter.adaptTypeVariable(typeVariable));
        return this;
    }

    @Override
    public String toString() {
        return "JavaPoetMethod{" +
                ", parameterAdapter=" + this.parameterAdapter +
                ", typeVariableAdapter=" + this.typeVariableAdapter +
                ", referenceAdapter=" + this.referenceAdapter +
                ", referenceFactory=" + this.referenceFactory +
                ", annotatable=" + this.annotatable +
                ", modifiers=" + this.modifiers +
                ", thrownTypes=" + this.thrownTypes +
                "}";
    }

}
