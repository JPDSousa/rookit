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

import org.rookit.auto.javapoet.JavaPoetMutableAnnotatable;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableCollection;

final class JavaPoetMethod implements MutableMethodSource {

    private final TypeReferenceSourceFactory referenceFactory;
    private final ArbitraryCodeSourceFactory codeFactory;
    private final OptionalFactory optionalFactory;

    private final CharSequence name;

    @Nullable
    private TypeReferenceSource returnType;

    private final JavaPoetMutableAnnotatable annotatable;
    private final Set<Modifier> modifiers;
    private final Set<TypeReferenceSource> thrownTypes;
    private final Collection<ParameterSource> parameters;
    private final boolean isConstructor;
    private boolean isVarArgs;
    private final Collection<ArbitraryCodeSource> statements;
    private final Collection<TypeVariableSource> typeVariables;

    @Nullable
    private ArbitraryCodeSource defaultValue;

    JavaPoetMethod(
            final TypeReferenceSourceFactory referenceFactory,
            final ArbitraryCodeSourceFactory codeFactory,
            final OptionalFactory optionalFactory, final CharSequence name,
            final JavaPoetMutableAnnotatable annotatable,
            final Set<Modifier> modifiers,
            final Set<TypeReferenceSource> thrownTypes,
            final Collection<ParameterSource> parameters,
            final boolean isConstructor,
            final Collection<ArbitraryCodeSource> statements,
            final Collection<TypeVariableSource> typeVariables) {

        this.codeFactory = codeFactory;
        this.optionalFactory = optionalFactory;
        this.name = name;
        this.annotatable = annotatable;
        this.referenceFactory = referenceFactory;
        this.modifiers = new LinkedHashSet<>(modifiers);
        this.thrownTypes = new LinkedHashSet<>(thrownTypes);
        this.parameters = new ArrayList<>(parameters);
        this.isConstructor = isConstructor;
        this.statements = new ArrayList<>(statements);
        this.typeVariables = new ArrayList<>(typeVariables);
    }

    @Override
    public MutableMethodSource addParameter(final ParameterSource parameter) {

        this.parameters.add(parameter);
        return this;
    }

    @Override
    public MutableMethodSource assignParameterToField(final ParameterSource parameter) {

        addParameter(parameter);

        return addStatement(this.codeFactory.initializeDependency(parameter.name()));
    }

    @Override
    public MutableMethodSource returnNewObject(
            final TypeReferenceSource objectType, final Iterable<CharSequence> parameters) {

        this.statements.add(this.codeFactory.returnNew(objectType, parameters));
        withReturnType(objectType);

        return this;
    }

    @Override
    public MutableMethodSource returnNewObject(
            final ExtendedTypeElement objectType, final Iterable<CharSequence> parameters) {

        return returnNewObject(this.referenceFactory.create(objectType), parameters);
    }

    @Override
    public MutableMethodSource returnInstanceField(final TypeReferenceSource fieldType, final CharSequence fieldName) {

        withReturnType(fieldType);
        return addStatement(this.codeFactory.returnInstanceField(fieldName));
    }

    @Override
    public MutableMethodSource returnInstanceField(
            final ExtendedTypeMirror fieldType, final CharSequence fieldName) {

        return returnInstanceField(this.referenceFactory.create(fieldType), fieldName);
    }

    @Override
    public MutableMethodSource withReturnType(final TypeReferenceSource reference) {

        this.returnType = reference;
        return self();
    }

    @Override
    public MutableMethodSource defaultValue(final String format, final List<Object> args) {

        this.defaultValue = this.codeFactory.createFromFormat(format, args);
        return self();
    }

    @Override
    public MutableMethodSource addVariable(
            final TypeReferenceSource type,
            final CharSequence name,
            final String initialization,
            final List<Object> args) {

        final ArbitraryCodeSource initializer = this.codeFactory.createFromFormat(initialization, args);
        return addStatement(this.codeFactory.newValue(type, name, initializer));
    }

    @Override
    public MutableMethodSource addStatement(final String statement, final List<Object> args) {

        return addStatement(this.codeFactory.createFromFormat(statement, args));
    }

    @Override
    public MutableMethodSource addStatement(final ArbitraryCodeSource statement) {

        this.statements.add(statement);
        return self();
    }

    @Override
    public MutableMethodSource varArgs(final boolean varArgs) {

        this.isVarArgs = varArgs;
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
    public MutableMethodSource returnStaticField(final TypeReferenceSource returnType, final CharSequence fieldName) {

        withReturnType(returnType);
        return addStatement(this.codeFactory.returnField(fieldName));
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

        this.typeVariables.add(typeVariable);
        return this;
    }

    @Override
    public boolean isConstructor() {
        return this.isConstructor;
    }

    @Override
    public Collection<ParameterSource> parameters() {

        return unmodifiableCollection(this.parameters);
    }

    @Override
    public Collection<ArbitraryCodeSource> code() {

        return unmodifiableCollection(this.statements);
    }

    @Override
    public Collection<TypeReferenceSource> thrownTypes() {

        return unmodifiableCollection(this.thrownTypes);
    }

    @Override
    public CharSequence name() {

        return this.name;
    }

    @Override
    public Optional<TypeReferenceSource> returnType() {

        return this.optionalFactory.ofNullable(this.returnType);
    }

    @Override
    public Optional<ArbitraryCodeSource> defaultValue() {

        return this.optionalFactory.ofNullable(this.defaultValue);
    }

    @Override
    public Collection<TypeVariableSource> typeVariables() {

        return unmodifiableCollection(this.typeVariables);
    }

    @Override
    public boolean isVarArgs() {

        return this.isVarArgs;
    }

}
