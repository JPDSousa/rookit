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

import com.squareup.javapoet.FieldSpec;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatable;
import org.rookit.auto.source.field.MutableFieldSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedHashSet;

import static java.util.Collections.unmodifiableCollection;

final class JavaPoetField implements MutableFieldSource {

    private static final Modifier[] MODIFIERS = new Modifier[0];

    private final JavaPoetMutableAnnotatable annotatable;
    private final TypeReferenceSource type;
    private final Collection<Modifier> modifiers;

    @SuppressWarnings("FieldNotUsedInToString")
    private final FieldSpec.Builder builder;

    JavaPoetField(
            final JavaPoetMutableAnnotatable annotatable,
            final TypeReferenceSource type,
            final Collection<Modifier> modifiers,
            final FieldSpec.Builder builder) {
        this.annotatable = annotatable;
        this.type = type;
        this.modifiers = new LinkedHashSet<>(modifiers);
        this.builder = builder;
    }

    FieldSpec getJavaPoet() {

        final FieldSpec.Builder builder = this.builder.build()
                .toBuilder();

        builder.addModifiers(this.modifiers.toArray(MODIFIERS));
        builder.addAnnotations(this.annotatable.annotationSpecs());

        return builder.build();
    }

    @Override
    public TypeReferenceSource type() {

        return this.type;
    }

    @Override
    public CharSequence name() {

        return getJavaPoet().name;
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
    public MutableFieldSource removeModifiers(final Collection<Modifier> modifiers) {

        this.modifiers.removeAll(modifiers);
        return self();
    }

    @Override
    public MutableFieldSource resetVisibility() {

        this.modifiers.clear();
        return self();
    }

    @Override
    public MutableFieldSource addModifiers(final Collection<Modifier> modifiers) {

        this.modifiers.addAll(modifiers);
        return self();
    }

    @Override
    public MutableFieldSource self() {

        return this;
    }

    @Override
    public MutableFieldSource addAnnotation(final AnnotationSource annotation) {

        this.annotatable.addAnnotation(annotation);
        return this;
    }

    @Override
    public MutableFieldSource addAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotatable.addAnnotationByClass(annotation);
        return this;
    }

    @Override
    public MutableFieldSource removeAnnotationByClass(final Class<? extends Annotation> annotation) {

        this.annotatable.removeAnnotationByClass(annotation);
        return this;
    }

    @Override
    public String toString() {
        return "JavaPoetField{" +
                "annotatable=" + this.annotatable +
                ", type=" + this.type +
                ", modifiers=" + this.modifiers +
                "}";
    }

}
