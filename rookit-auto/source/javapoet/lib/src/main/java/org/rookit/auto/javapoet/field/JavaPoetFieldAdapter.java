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
package org.rookit.auto.javapoet.field;

import com.google.inject.Inject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.source.field.FieldAdapter;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;

import javax.lang.model.element.Modifier;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class JavaPoetFieldAdapter implements FieldAdapter<FieldSpec> {

    private static final Modifier[] MODIFIERS = new Modifier[0];

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final AnnotationSourceAdapter<AnnotationSpec> annotationAdapter;

    @Inject
    private JavaPoetFieldAdapter(
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final AnnotationSourceAdapter<AnnotationSpec> annotationAdapter) {
        this.referenceAdapter = referenceAdapter;
        this.annotationAdapter = annotationAdapter;
    }

    // TODO create JavaPoetField interface so that these warnings can be removed.
    @SuppressWarnings({"InstanceofConcreteClass", "CastToConcreteClass"}) // performance improvement
    @Override
    public FieldSpec adaptField(final FieldSource field) {

        if (field instanceof JavaPoetField) {
            return ((JavaPoetField) field).getJavaPoet();
        }

        final TypeName javaPoetType = this.referenceAdapter.adaptTypeReference(field.type());
        final List<AnnotationSpec> annotations = field.annotations()
                .stream()
                .map(this.annotationAdapter::adaptAnnotation)
                .collect(toImmutableList());
        final Modifier[] modifiers = field.modifiers()
                .toArray(MODIFIERS);

        return FieldSpec.builder(javaPoetType, field.name().toString(), modifiers)
                .addAnnotations(annotations)
                .build();
    }

    @Override
    public String toString() {
        return "JavaPoetFieldAdapter{" +
                "referenceAdapter=" + this.referenceAdapter +
                ", annotationAdapter=" + this.annotationAdapter +
                "}";
    }

}
