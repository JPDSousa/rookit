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

import com.google.inject.Inject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import one.util.streamex.EntryStream;
import org.rookit.auto.source.CodeSourceVisitor;
import org.rookit.auto.source.type.annotation.AnnotationSourceAdapter;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.utils.primitive.VoidUtils;

import static java.lang.String.format;

final class JavaPoetAnnotationAdapter implements AnnotationSourceAdapter<AnnotationSpec> {

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final CodeSourceVisitor<CodeBlock, Void> memberVisitor;
    private final VoidUtils voidUtils;

    @Inject
    private JavaPoetAnnotationAdapter(
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final CodeSourceVisitor<CodeBlock, Void> memberVisitor,
            final VoidUtils voidUtils) {
        this.referenceAdapter = referenceAdapter;
        this.memberVisitor = memberVisitor;
        this.voidUtils = voidUtils;
    }

    @Override
    public AnnotationSpec adaptAnnotation(final AnnotationSource annotation) {

        if (annotation instanceof JavaPoetAnnotation) {
            return ((JavaPoetAnnotation) annotation).spec();
        }

        final ClassName className = getClassName(annotation);

        return EntryStream.of(annotation.members())
                .mapValues(member -> member.accept(this.memberVisitor, this.voidUtils.returnVoid()))
                .collect(new AnnotationCollector(className));
    }

    private ClassName getClassName(final AnnotationSource source) {

        final TypeReferenceSource reference = source.reference();
        final TypeName typeName = this.referenceAdapter.adaptTypeReference(reference);
        if (!(typeName instanceof ClassName)) {
            // TODO add more info on why the reference is not valid
            final String errMsg = format("%s is not a valid reference for an annotation source.", reference);
            throw new IllegalArgumentException(errMsg);
        }

        return (ClassName) typeName;
    }

    @Override
    public String toString() {
        return "JavaPoetAnnotationAdapter{" +
                "referenceAdapter=" + this.referenceAdapter +
                ", memberVisitor=" + this.memberVisitor +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
