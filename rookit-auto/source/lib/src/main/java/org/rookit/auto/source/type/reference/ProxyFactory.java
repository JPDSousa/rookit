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
package org.rookit.auto.source.type.reference;

import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.utils.string.template.Template1;

final class ProxyFactory implements TypeReferenceSourceFactory {

    private final TypeReferenceSourceFactory delegate;
    private final ExtendedPackageElement packageReference;
    private final Template1 classNameTemplate;

    ProxyFactory(
            final TypeReferenceSourceFactory delegate,
            final ExtendedPackageElement packageReference,
            final Template1 classNameTemplate) {
        this.delegate = delegate;
        this.packageReference = packageReference;
        this.classNameTemplate = classNameTemplate;
    }

    @Override
    public TypeReferenceSource create(final ExtendedTypeMirror element) {

        return element.boxIfPrimitive()
                .toElement()
                .select(ExtendedTypeElement.class)
                .map(this::create)
                .orElseThrow(() -> new IllegalArgumentException("Cannot create a reference from " + element));
    }

    @Override
    public TypeReferenceSource fromClass(final Class<?> clazz) {

        // TODO test for arrays and parameterized types
        return fromSplitPackageAndName(this.packageReference, clazz.getSimpleName());
    }

    @Override
    public TypeReferenceSource fromSplitPackageAndName(final ExtendedPackageElement packageReference,
                                                       final CharSequence name) {

        return this.delegate.fromSplitPackageAndName(this.packageReference, this.classNameTemplate.build(name));
    }

    @Override
    public TypeReferenceSource resolveParameters(final ExtendedTypeElement element,
                                                 final TypeReferenceSource... parameters) {

        return this.delegate.resolveParameters(element, parameters);
    }

}
