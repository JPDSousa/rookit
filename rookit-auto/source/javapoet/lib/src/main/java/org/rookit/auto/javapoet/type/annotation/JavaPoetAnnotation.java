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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import org.rookit.auto.source.CodeSource;
import org.rookit.auto.source.CodeSourceVisitor;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.type.annotation.MutableAnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.utils.primitive.VoidUtils;

import java.util.Collections;
import java.util.Map;

final class JavaPoetAnnotation implements MutableAnnotationSource {

    private final TypeReferenceSource reference;

    @SuppressWarnings("FieldNotUsedInToString")
    private final AnnotationSpec.Builder builder;
    private final VoidUtils voidUtils;
    private final CodeSourceVisitor<CodeBlock, Void> javaPoetVisitor;
    private final Map<String, CodeSource> members;

    JavaPoetAnnotation(
            final TypeReferenceSource reference,
            final AnnotationSpec.Builder builder,
            final VoidUtils voidUtils,
            final CodeSourceVisitor<CodeBlock, Void> javaPoetVisitor,
            final Map<String, CodeSource> members) {
        this.reference = reference;
        this.builder = builder;
        this.voidUtils = voidUtils;
        this.javaPoetVisitor = javaPoetVisitor;
        this.members = members;
    }

    AnnotationSpec spec() {
        return this.builder.build();
    }

    @Override
    public MutableAnnotationSource addMember(final String memberName, final ArbitraryCodeSource memberValue) {

        this.builder.addMember(memberName, memberValue.accept(this.javaPoetVisitor, this.voidUtils.returnVoid()));
        this.members.put(memberName, memberValue);
        return this;
    }

    @Override
    public TypeReferenceSource reference() {
        return this.reference;
    }

    @Override
    public Map<String, CodeSource> members() {
        return Collections.unmodifiableMap(this.members);
    }

    @Override
    public String toString() {
        return "JavaPoetAnnotation{" +
                "reference=" + this.reference +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
