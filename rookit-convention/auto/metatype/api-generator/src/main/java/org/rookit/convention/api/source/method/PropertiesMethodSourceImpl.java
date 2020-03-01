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
package org.rookit.convention.api.source.method;

import com.google.common.collect.ImmutableList;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.metatype.property.Property;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

final class PropertiesMethodSourceImpl implements PropertiesMethodSource {

    private final CharSequence methodName;
    private final CharSequence varName;
    private final MutableMethodSource delegate;

    PropertiesMethodSourceImpl(
            final CharSequence methodName,
            final CharSequence varName,
            final MutableMethodSource delegate) {

        this.methodName = methodName;
        this.varName = varName;
        this.delegate = delegate;
    }

    @Override
    public PropertiesMethodSource addParameter(final ParameterSource parameter) {

        this.delegate.addParameter(parameter);
        return self();
    }

    @Override
    public PropertiesMethodSource assignParametersToFields(final Iterable<ParameterSource> parameters) {

        this.delegate.assignParametersToFields(parameters);
        return self();
    }

    @Override
    public PropertiesMethodSource returnNewObject(
            final TypeReferenceSource objectType, final Iterable<CharSequence> parameters) {

        this.delegate.returnNewObject(objectType, parameters);
        return self();
    }

    @Override
    public PropertiesMethodSource returnNewObject(
            final ExtendedTypeElement objectType, final Iterable<CharSequence> parameters) {

        this.delegate.returnNewObject(objectType, parameters);
        return self();
    }

    @Override
    public PropertiesMethodSource returnInstanceField(
            final TypeReferenceSource fieldType, final CharSequence fieldName) {

        this.delegate.returnInstanceField(fieldType, fieldName);
        return self();
    }

    @Override
    public PropertiesMethodSource returnInstanceField(
            final ExtendedTypeMirror fieldType, final CharSequence fieldName) {

        this.delegate.returnInstanceField(fieldType, fieldName);
        return self();
    }

    @Override
    public PropertiesMethodSource withReturnType(final TypeReferenceSource reference) {

        this.delegate.withReturnType(reference);
        return self();
    }

    @Override
    public PropertiesMethodSource returnMethodCall(
            final TypeReferenceSource returnType,
            final String delegateCall,
            final List<Object> args) {

        this.delegate.returnMethodCall(returnType, delegateCall, args);
        return self();
    }

    @Override
    public PropertiesMethodSource returnMethodCall(
            final String delegateCall,
            final List<Object> args) {

        this.delegate.returnMethodCall(delegateCall, args);
        return self();
    }

    @Override
    public PropertiesMethodSource self() {

        return this;
    }

    @Override
    public PropertiesMethodSource addAnnotation(final AnnotationSource annotation) {

        this.delegate.addAnnotation(annotation);
        return self();
    }

    @Override
    public PropertiesMethodSource addAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.delegate.addAnnotationByClass(annotation);
        return self();
    }

    @Override
    public PropertiesMethodSource removeAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.delegate.removeAnnotationByClass(annotation);
        return self();
    }

    @Override
    public PropertiesMethodSource removeModifiers(final Collection<Modifier> modifiers) {

        this.delegate.removeModifiers(modifiers);
        return self();
    }

    @Override
    public PropertiesMethodSource resetVisibility() {

        this.delegate.resetVisibility();
        return self();
    }

    @Override
    public PropertiesMethodSource addModifiers(final Collection<Modifier> modifiers) {

        this.delegate.addModifiers(modifiers);
        return self();
    }

    @Override
    public PropertiesMethodSource addTypeVariable(final TypeVariableSource typeVariable) {

        this.delegate.addTypeVariable(typeVariable);
        return self();
    }

    @Override
    public PropertiesMethodSource defaultValue(final String format, final List<Object> args) {

        this.delegate.defaultValue(format, args);
        return self();
    }

    @Override
    public PropertiesMethodSource addVariable(
            final TypeReferenceSource type,
            final CharSequence name,
            final String initialization,
            final List<Object> args) {

        this.delegate.addVariable(type, name, initialization, args);
        return self();
    }

    @Override
    public PropertiesMethodSource addStatement(final String statement, final List<Object> args) {

        this.delegate.addStatement(statement, args);
        return self();
    }

    @Override
    public PropertiesMethodSource addStatement(final ArbitraryCodeSource statement) {

        this.delegate.addStatement(statement);
        return self();
    }

    @Override
    public PropertiesMethodSource varArgs(final boolean varArgs) {

        this.delegate.varArgs(varArgs);
        return self();
    }

    @Override
    public PropertiesMethodSource addThrownType(final TypeReferenceSource exceptionType) {

        this.delegate.addThrownType(exceptionType);
        return self();
    }

    @Override
    public PropertiesMethodSource addThrownType(final ExtendedTypeMirror exceptionType) {

        this.delegate.addThrownType(exceptionType);
        return self();
    }

    @Override
    public PropertiesMethodSource returnStaticField(final TypeReferenceSource returnType,
                                                    final CharSequence fieldName) {

        this.delegate.returnStaticField(returnType, fieldName);
        return self();
    }

    @Override
    public PropertiesMethodSource addFromSuperInterface(final TypeReferenceSource superInterface) {

        this.delegate.addStatement("$L.addAll($T.super.$L())",
                                   ImmutableList.of(this.varName,
                                                    superInterface,
                                                    this.methodName));
        return self();
    }

    @Override
    public PropertiesMethodSource addFromProperty(final Property property) {

        this.delegate.addStatement("$L.addAll($L())", ImmutableList.of(this.varName, property.name()));
        return self();
    }

    @Override
    public Collection<AnnotationSource> annotations() {

        return this.delegate.annotations();
    }

    @Override
    public Collection<Modifier> modifiers() {

        return this.delegate.modifiers();
    }

    @Override
    public String toString() {
        return "PropertiesMethodSourceImpl{" +
                "methodName=" + this.methodName +
                ", varName=" + this.varName +
                ", delegate=" + this.delegate +
                "}";
    }

}
