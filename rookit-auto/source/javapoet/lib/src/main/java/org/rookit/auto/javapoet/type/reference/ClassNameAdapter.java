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
package org.rookit.auto.javapoet.type.reference;

import com.google.inject.Inject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;

import static java.lang.String.format;

final class ClassNameAdapter implements TypeReferenceSourceAdapter<ClassName> {

    private final TypeReferenceSourceAdapter<TypeName> adapter;
    private final TypeName2ClassName classNameUtil;

    @Inject
    private ClassNameAdapter(
            final TypeReferenceSourceAdapter<TypeName> adapter,
            final TypeName2ClassName classNameUtil) {
        this.adapter = adapter;
        this.classNameUtil = classNameUtil;
    }

    @Override
    public ClassName adaptTypeReference(final TypeReferenceSource typeReference) {

        final TypeName typeName = this.adapter.adaptTypeReference(typeReference);
        return this.classNameUtil.extractClass(typeName)
                .orElseThrow(() -> new IllegalArgumentException(format("Cannot extract a %s from %s of type %s.",
                                                                       ClassName.class.getSimpleName(),
                                                                       typeName,
                                                                       typeName.getClass())));
    }

}
