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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.javapoet.JavaPoetMutableAnnotatableFactory;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.field.MutableFieldSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;

final class JavaPoetFieldFactory implements FieldSourceFactory {

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final JavaPoetMutableAnnotatableFactory annotatableFactory;

    @Inject
    private JavaPoetFieldFactory(
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final JavaPoetMutableAnnotatableFactory annotatableFactory) {
        this.referenceAdapter = referenceAdapter;
        this.annotatableFactory = annotatableFactory;
    }

    @Override
    public MutableFieldSource createMutable(
            final TypeReferenceSource type, final String name) {

        final FieldSpec.Builder builder = FieldSpec.builder(this.referenceAdapter.adaptTypeReference(type), name);

        return new JavaPoetField(
                this.annotatableFactory.createEmpty(),
                type,
                ImmutableList.of(),
                builder
        );
    }

    @Override
    public String toString() {
        return "JavaPoetFieldFactory{" +
                "referenceAdapter=" + this.referenceAdapter +
                ", annotatableFactory=" + this.annotatableFactory +
                "}";
    }

}
