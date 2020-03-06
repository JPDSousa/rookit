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
package org.rookit.auto.javapoet.type.annotation;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.rookit.auto.source.CodeSource;
import org.rookit.auto.source.CodeSourceVisitor;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;
import org.rookit.auto.source.type.annotation.MutableAnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.utils.primitive.VoidUtils;

import java.util.Map;

final class JavaPoetAnnotationFactory implements AnnotationSourceFactory {

    private final VoidUtils voidUtils;
    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeReferenceSourceAdapter<ClassName> classNameAdapter;
    private final CodeSourceVisitor<CodeBlock, Void> javaPoetVisitor;

    @Inject
    private JavaPoetAnnotationFactory(
            final VoidUtils voidUtils,
            final TypeReferenceSourceFactory referenceFactory,
            final TypeReferenceSourceAdapter<ClassName> classNameAdapter,
            final CodeSourceVisitor<CodeBlock, Void> javaPoetVisitor) {
        this.voidUtils = voidUtils;
        this.referenceFactory = referenceFactory;
        this.classNameAdapter = classNameAdapter;
        this.javaPoetVisitor = javaPoetVisitor;
    }

    @Override
    public AnnotationSource fromReference(final TypeReferenceSource reference) {

        final ClassName className = this.classNameAdapter.adaptTypeReference(reference);
        final AnnotationSpec.Builder builder = AnnotationSpec.builder(className);
        // TODO 1 is a magic number -> extract into a proper place
        final Map<String, CodeSource> members = Maps.newHashMapWithExpectedSize(1);

        return new JavaPoetAnnotation(reference, builder, this.voidUtils, this.javaPoetVisitor, members);
    }

    @Override
    public AnnotationSource create(final Class<?> clazz) {
        return createMutable(clazz);
    }

    @Override
    public MutableAnnotationSource createMutable(final Class<?> clazz) {

        final TypeReferenceSource reference = this.referenceFactory.fromClass(clazz);
        // TODO 1 is a magic number -> extract into a proper place
        final Map<String, CodeSource> members = Maps.newHashMapWithExpectedSize(1);

        return new JavaPoetAnnotation(reference, AnnotationSpec.builder(clazz), this.voidUtils,
                                      this.javaPoetVisitor,
                                      members);
    }

}
