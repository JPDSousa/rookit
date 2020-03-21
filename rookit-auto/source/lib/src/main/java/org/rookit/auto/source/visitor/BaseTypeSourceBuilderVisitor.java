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
package org.rookit.auto.source.visitor;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.utils.string.StringUtils;

final class BaseTypeSourceBuilderVisitor<P> implements ExtendedElementVisitor<TypeSource, P> {

    private final TypeReferenceSourceFactory referenceFactory;
    private final IdentifierTransformer idTransformer;
    private final StringUtils stringUtils;
    private final TypeSourceCreator typeCreator;

    BaseTypeSourceBuilderVisitor(
            final TypeReferenceSourceFactory referenceFactory,
            final IdentifierTransformer idTransformer,
            final StringUtils stringUtils,
            final TypeSourceCreator typeCreator) {

        this.referenceFactory = referenceFactory;
        this.idTransformer = idTransformer;
        this.stringUtils = stringUtils;
        this.typeCreator = typeCreator;
    }

    private TypeSource createType(final ExtendedElement element) {

        return createType(element.packageInfo(), element.getSimpleName());
    }

    private TypeSource createType(final ExtendedPackageElement packageReference,
                                  final CharSequence name) {

        final ExtendedPackageElement transformedPackage = this.idTransformer.transformPackage(packageReference);
        final CharSequence transformedName = this.idTransformer.transformName(name);
        final TypeReferenceSource reference = this.referenceFactory.fromSplitPackageAndName(transformedPackage, transformedName);

        return this.typeCreator.fromReference(reference)
                .makePublic();
    }

    @Override
    public TypeSource visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return createType(packageElement);
    }

    @Override
    public TypeSource visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return createType(extendedType);
    }

    @Override
    public TypeSource visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {

        final String executableName = extendedExecutable.getSimpleName()
                .toString();
        final String name = extendedExecutable.getEnclosingElement().getSimpleName()
                + this.stringUtils.capitalizeFirstChar(executableName);

        return createType(extendedExecutable.packageInfo(), name);
    }

    @Override
    public TypeSource visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        return createType(extendedParameter);
    }

    @Override
    public TypeSource visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return createType(extendedElement);
    }

    @Override
    public TypeSource visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return createType(extendedElement);
    }

}
