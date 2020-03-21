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
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.utils.optional.OptionalFactory;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class JavaPoetReferenceFactory implements TypeReferenceSourceFactory {

    private final OptionalFactory optionalFactory;
    private final TypeName2ClassName classNameUtil;

    @Inject
    private JavaPoetReferenceFactory(
            final OptionalFactory optionalFactory,
            final TypeName2ClassName classNameUtil) {
        this.optionalFactory = optionalFactory;
        this.classNameUtil = classNameUtil;
    }

    @Override
    public TypeReferenceSource create(final ExtendedTypeMirror element) {

        final TypeName typeName = TypeName.get(element);
        final String packageName = this.classNameUtil.extractClass(typeName)
                .map(ClassName::packageName)
                .orElse(EMPTY);
        return new BaseJavaPoetReference(packageName, typeName, this.optionalFactory);
    }

    @Override
    public TypeReferenceSource fromClass(final Class<?> clazz) {

        final ClassName className = ClassName.get(clazz);
        return new BaseJavaPoetReference(
                className.packageName(),
                className,
                this.optionalFactory
        );
    }

    @Override
    public TypeReferenceSource fromSplitPackageAndName(final ExtendedPackageElement packageReference,
                                                       final CharSequence name) {

        final String packageName = packageReference.getQualifiedName()
                .toString();
        return new BaseJavaPoetReference(
                packageName,
                ClassName.get(
                        packageName,
                        name.toString()),
                this.optionalFactory);
    }

    @Override
    public TypeReferenceSource resolveParameters(final ExtendedTypeElement element,
                                                 final TypeReferenceSource... parameters) {

        // TODO dummy implementation, because this method is a bad design decision
        return create(element);
    }

}
