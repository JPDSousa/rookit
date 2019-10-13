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

import com.google.inject.Provider;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.javapoet.doc.JavaPoetJavadocTemplate1;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.javax.visitor.StreamExBuilder;
import org.rookit.utils.adapt.Adapter;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class TypeSpecBuilderImpl<V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P>
        implements TypeSpecBuilder<V, P> {

    private final StreamExBuilder<V, TypeSpec.Builder, P> builder;
    private final ExtendedElementVisitors visitors;
    private final Types types;
    private final Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter;

    TypeSpecBuilderImpl(
            final StreamExBuilder<V, TypeSpec.Builder, P> builder,
            final ExtendedElementVisitors visitors,
            final Types types,
            final Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter) {
        this.builder = builder;
        this.visitors = visitors;
        this.types = types;
        this.downcastAdapter = downcastAdapter;
    }

    private TypeSpecBuilder<V, P> newStage(
            final StreamExBuilder<V, TypeSpec.Builder, P> builder) {
        return new TypeSpecBuilderImpl<>(builder, this.visitors, this.types, this.downcastAdapter);
    }

    @Override
    public TypeSpecBuilder<V, P> withAnnotations(
            final Iterable<AnnotationSpec> annotations) {
        final V visitor = this.downcastAdapter.apply(new AddAnnotationsVisitor<>(build(), annotations));
        return newStage(this.visitors.streamExBuilder(visitor, downcastAdapter));
    }

    @Override
    public TypeSpecBuilder<V, P> copyBodyFrom(final TypeMirror typeMirror) {
        return copyBodyFrom(construct -> typeMirror);
    }

    @Override
    public TypeSpecBuilder<V, P> copyBodyFrom(
            final Function<ExtendedElement, TypeMirror> extractionFunction) {
        return newStage(this.visitors.streamExBuilder(
                this.downcastAdapter.apply(new CopyBodyFromVisitor<>(extractionFunction, build(), this.types)),
                downcastAdapter));
    }

    @Override
    public TypeSpecBuilder<V, P> withClassJavadoc(
            final JavaPoetJavadocTemplate1 template) {
        return newStage(this.visitors.streamExBuilder(
                this.downcastAdapter.apply(new ClassJavadocVisitor<>(build(), template)), downcastAdapter));
    }

    @Override
    public <V1 extends ExtendedElementVisitor<StreamEx<TypeSpec>, P>> StreamExBuilder<V1, TypeSpec, P> buildTypeSpec(
            final Function<ExtendedElementVisitor<StreamEx<TypeSpec>, P>, V1> downcastAdapter) {
        return this.visitors.streamExBuilder(downcastAdapter.apply(new TypeSpecBuildVisitor<>(build())),
                                             downcastAdapter);
    }


    @Override
    public TypeSpecBuilder<V, P> withTypeAdapter(
            final Adapter<ExtendedTypeElement> adapter) {
        return newStage(this.builder.withTypeAdapter(adapter));
    }

    @Override
    public TypeSpecBuilder<V, P> withRecursiveVisiting(
            final BinaryOperator<StreamEx<TypeSpec.Builder>> resultReducer) {
        return newStage(this.builder.withRecursiveVisiting(resultReducer));
    }

    @Override
    public V build() {
        return this.builder.build();
    }

    @Override
    public TypeSpecBuilder<V, P> add(final V visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public TypeSpecBuilder<V, P> add(final Provider<V> visitor) {
        return newStage(this.builder.add(visitor));
    }

    @Override
    public TypeSpecBuilder<V, P> addAll(final Collection<? extends V> visitors) {
        return newStage(this.builder.addAll(visitors));
    }

    @Override
    public TypeSpecBuilder<V, P> withDirtyFallback(
            final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> visitor) {
        return newStage(this.builder.withDirtyFallback(visitor));
    }

    @Override
    public TypeSpecBuilder<V, P> filterIfAnnotationAbsent(
            final Class<? extends Annotation> annotationClass) {
        return newStage(this.builder.filterIfAnnotationAbsent(annotationClass));
    }

    @Override
    public TypeSpecBuilder<V, P> filterIfAllAnnotationsAbsent(
            final Iterable<? extends Class<? extends Annotation>> annotationClasses) {
        return newStage(this.builder.filterIfAllAnnotationsAbsent(annotationClasses));
    }

    @Override
    public String toString() {
        return "TypeSpecBuilderImpl{" +
                "builder=" + this.builder +
                ", visitors=" + this.visitors +
                ", types=" + this.types +
                "}";
    }
}
