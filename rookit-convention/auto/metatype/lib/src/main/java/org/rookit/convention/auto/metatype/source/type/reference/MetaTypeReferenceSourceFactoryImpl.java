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
package org.rookit.convention.auto.metatype.source.type.reference;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.MetaType;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class MetaTypeReferenceSourceFactoryImpl implements MetaTypeReferenceSourceFactory {

    private static final String GENERIC = "Generic";

    private final TypeReferenceSource metaType;
    private final TypeParameterSourceFactory parameterFactory;
    private final IdentifierTransformer transformer;
    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeVariableSource variableName;

    @Inject
    private MetaTypeReferenceSourceFactoryImpl(
            @MetaTypeAPI final TypeVariableSource variableName,
            @From(MetaType.class) final TypeReferenceSource metaType,
            final TypeParameterSourceFactory parameterFactory,
            final IdentifierTransformer transformer,
            final TypeReferenceSourceFactory referenceFactory) {

        this.variableName = variableName;
        this.metaType = metaType;
        this.parameterFactory = parameterFactory;
        this.transformer = transformer;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public TypeReferenceSource referenceFor(final ConventionTypeElement typeElement) {

        return createBaseReference(EMPTY, typeElement);
    }

    @Override
    public TypeReferenceSource implReferenceFor(final ConventionTypeElement typeElement) {

        return createBaseReference("Base", typeElement);
    }

    @Override
    public TypeReferenceSource genericReferenceFor(final ConventionTypeElement typeElement) {

        final TypeReferenceSource baseReference = createBaseReference(GENERIC, typeElement);

        return this.parameterFactory.create(baseReference, this.variableName);
    }

    @Override
    public TypeReferenceSource genericParentReferenceFor(final ConventionTypeElement typeElement) {

        final TypeReferenceSource baseReference = createBaseReference(GENERIC, typeElement);

        return this.parameterFactory.create(baseReference, referenceFor(typeElement));
    }

    private TypeReferenceSource createBaseReference(final String prefix, final ExtendedTypeElement typeElement) {
        final Identifier identifier = typeElement.identifier();
        final CharSequence sourceName = identifier.name();
        final ExtendedPackageElement sourcePackage = identifier.packageElement();

        final String targetName = prefix + this.transformer.transformName(sourceName);

        final ExtendedPackageElement targetPackage = this.transformer.transformPackage(sourcePackage);

        return this.referenceFactory.fromSplitPackageAndName(targetPackage, targetName);
    }

    @Override
    public Collection<TypeReferenceSource> parentReferencesFor(final ConventionTypeElement typeElement) {

        final boolean isPartial = typeElement.isPartialEntity();
        final boolean isTopLevel = typeElement.isTopLevel();

        // top level partial
        if (isTopLevel && isPartial) {

            return ImmutableList.of(this.parameterFactory.create(this.metaType, this.variableName));
        }

        // top level entity
        if (isTopLevel) {
            return ImmutableList.of(this.parameterFactory.create(this.metaType, typeElement));
        }

        return typeElement.conventionInterfaces()
                .map(this::parentReference)
                .toImmutableList();
    }

    private TypeReferenceSource parentReference(final ExtendedTypeElement parent) {

        final TypeReferenceSource baseReference = createBaseReference(GENERIC, parent);

        return this.parameterFactory.create(baseReference, this.variableName);
    }

}
