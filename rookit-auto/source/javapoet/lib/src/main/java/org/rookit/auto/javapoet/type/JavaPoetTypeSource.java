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

import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatable;
import org.rookit.auto.javapoet.type.reference.JavaPoetReference;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.field.FieldAdapter;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceAdapter;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.concurrent.Executor;

import static java.util.Collections.unmodifiableCollection;

final class JavaPoetTypeSource extends AbstractJavaPoetTypeSource implements JavaPoetReference {

    private final FieldAdapter<FieldSpec> fieldAdapter;
    private final MethodSourceAdapter<MethodSpec> methodAdapter;
    private final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter;
    private final TypeReferenceSourceAdapter<TypeName> typeReferenceAdapter;

    private final TypeReferenceSource reference;
    private final TypeSpec.Builder source;
    private final Collection<Modifier> modifiers;
    private final JavaPoetMutableAnnotatable annotatable;
    private final String separator;

    JavaPoetTypeSource(
            final TypeReferenceSource reference,
            final TypeSpec.Builder source,
            final Executor executor,
            final FieldAdapter<FieldSpec> fieldAdapter,
            final MethodSourceAdapter<MethodSpec> methodAdapter,
            final TypeVariableSourceAdapter<TypeVariableName> typeVariableAdapter,
            final TypeReferenceSourceAdapter<TypeName> typeReferenceAdapter,
            final JavaPoetMutableAnnotatable annotatable,
            final String separator) {
        super(executor);
        this.source = source;
        this.reference = reference;
        this.fieldAdapter = fieldAdapter;
        this.methodAdapter = methodAdapter;
        this.typeVariableAdapter = typeVariableAdapter;
        this.typeReferenceAdapter = typeReferenceAdapter;
        this.annotatable = annotatable;
        this.separator = separator;
        this.modifiers = Sets.newHashSetWithExpectedSize(3);
    }

    @Override
    public TypeReferenceSource reference() {

        return this.reference;
    }

    @Override
    public MutableTypeSource resetVisibility() {

        this.modifiers.clear();
        return this;
    }

    @Override
    public MutableTypeSource removeModifiers(final Collection<Modifier> modifiers) {

        this.modifiers.removeAll(modifiers);
        return this;
    }

    @Override
    public MutableTypeSource addModifiers(final Collection<Modifier> modifiers) {

        this.modifiers.addAll(modifiers);
        return this;
    }

    @Override
    public MutableTypeSource addJavadoc(final String javadoc) {

        this.source.addJavadoc(javadoc);
        return this;
    }

    @Override
    public MutableTypeSource addTypeVariable(final TypeVariableSource typeVariable) {

        this.source.addTypeVariable(this.typeVariableAdapter.adaptTypeVariable(typeVariable));
        return this;
    }

    @Override
    public MutableTypeSource withSuperclass(final TypeReferenceSource superclass) {

        this.source.superclass(this.typeReferenceAdapter.adaptTypeReference(superclass));
        return this;
    }

    @Override
    public MutableTypeSource withParameterizedSuperclass(
            final Class<?> parameterized, final ExtendedTypeElement parameter) {

        final ClassName parameterizedName = ClassName.get(parameterized);
        final TypeName parameterName = TypeName.get(parameter.asType());

        this.source.superclass(ParameterizedTypeName.get(parameterizedName, parameterName));
        return this;
    }

    @Override
    public MutableTypeSource addParameterizedInterface(
            final ExtendedTypeElement parameterized, final TypeReferenceSource parameter) {

        final ClassName parameterizedName = ClassName.get(parameterized);
        final TypeName parameterName = this.typeReferenceAdapter.adaptTypeReference(parameter);

        this.source.addSuperinterface(ParameterizedTypeName.get(parameterizedName, parameterName));
        return this;
    }

    @Override
    public MutableTypeSource addInterface(final TypeReferenceSource interfaceSource) {

        this.source.addSuperinterface(this.typeReferenceAdapter.adaptTypeReference(interfaceSource));
        return this;
    }

    @Override
    public MutableTypeSource addMethod(final MethodSource method) {

        this.source.addMethod(this.methodAdapter.adaptMethod(method));
        return this;
    }

    @Override
    public MutableTypeSource addField(final FieldSource field) {

        this.source.addField(this.fieldAdapter.adaptField(field));
        return this;
    }

    @Override
    protected TypeSpec typeSpec() {

        final TypeSpec originalSpec = this.source.build();
        final TypeSpec.Builder builder = originalSpec.toBuilder();

        if ((originalSpec.kind != TypeSpec.Kind.INTERFACE) && (originalSpec.kind != TypeSpec.Kind.ANNOTATION)) {
            builder.addMethod(createToString(originalSpec));
        }

        this.modifiers.forEach(builder::addModifiers);
        builder.addAnnotations(this.annotatable.annotationSpecs());

        return builder.build();
    }

    private MethodSpec createToString(final TypeSpec spec) {
        final CodeBlock.Builder codeBlock = CodeBlock.builder()
                .add("return \"$L{\" +" + this.separator, spec.name);
        for (final FieldSpec field : spec.fieldSpecs) {
            codeBlock.add("\"$L=\" + this.$L +" + this.separator, field.name, field.name);
        }
        codeBlock.add("\"} \" + super.toString();" + this.separator);

        return MethodSpec.methodBuilder("toString")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(String.class)
                .addCode(codeBlock.build())
                .build();
    }

    @Override
    public MutableTypeSource self() {
        return this;
    }

    @Override
    public MutableTypeSource addAnnotation(final AnnotationSource annotation) {

        this.annotatable.addAnnotation(annotation);
        return this;
    }

    @Override
    public MutableTypeSource addAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotatable.addAnnotationByClass(annotation);
        return this;
    }

    @Override
    public MutableTypeSource removeAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotatable.addAnnotationByClass(annotation);
        return this;
    }

    @Override
    public Collection<AnnotationSource> annotations() {

        return this.annotatable.annotations();
    }

    @Override
    public Collection<Modifier> modifiers() {

        return unmodifiableCollection(this.modifiers);
    }

    @Override
    public TypeName buildTypeName() {
        return this.typeReferenceAdapter.adaptTypeReference(reference());
    }

}
