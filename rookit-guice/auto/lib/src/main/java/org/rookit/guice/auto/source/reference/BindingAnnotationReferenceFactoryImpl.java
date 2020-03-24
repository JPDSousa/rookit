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
package org.rookit.guice.auto.source.reference;

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.guice.auto.Guice;
import org.rookit.utils.string.StringUtils;

import javax.lang.model.element.Name;

import static java.util.Objects.isNull;

final class BindingAnnotationReferenceFactoryImpl implements BindingAnnotationReferenceFactory {

    private final IdentifierTransformer idTransformer;
    private final StringUtils stringUtils;
    private final TypeReferenceSourceFactory referenceFactory;

    @Inject
    private BindingAnnotationReferenceFactoryImpl(
            @Guice final IdentifierTransformer idTransformer,
            final StringUtils stringUtils,
            final TypeReferenceSourceFactory referenceFactory) {
        this.idTransformer = idTransformer;
        this.stringUtils = stringUtils;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public TypeReferenceSource fromType(final ExtendedTypeElement typeElement) {

        final Name name = typeElement.getSimpleName();
        final ExtendedPackageElement packageReference = typeElement.packageInfo();

        return referenceFrom(packageReference, name);
    }

    @Override
    public TypeReferenceSource fromExecutable(final ExtendedExecutableElement executable) {

        final ExtendedElement enclosing = executable.getEnclosingElement();
        final String prefix = isNull(enclosing)
                ? ""
                : enclosing.getSimpleName().toString();
        final String executableName = executable.getSimpleName()
                .toString();
        final ExtendedPackageElement packageReference = executable.packageInfo();

        return fromExecutable(packageReference, prefix, executableName);
    }

    @Override
    public TypeReferenceSource fromExecutable(final ExtendedElement enclosing, final CharSequence executableName) {

        final String prefix = enclosing.getSimpleName().toString();
        final ExtendedPackageElement packageReference = enclosing.packageInfo();

        return fromExecutable(packageReference, prefix, executableName);
    }

    private TypeReferenceSource fromExecutable(final ExtendedPackageElement packageReference,
                                               final CharSequence prefix,
                                               final CharSequence executableName) {

        return referenceFrom(
                packageReference,
                prefix + this.stringUtils.capitalizeFirstChar(executableName.toString())
        );
    }

    private TypeReferenceSource referenceFrom(final ExtendedPackageElement packageReference, final CharSequence name) {

        final ExtendedPackageElement transformedPackage = this.idTransformer.transformPackage(packageReference);
        final CharSequence transformedName = this.idTransformer.transformName(name);
        return this.referenceFactory.fromSplitPackageAndName(transformedPackage, transformedName);
    }

}
