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
package org.rookit.convention.auto.metatype.source.type;

import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.method.PropertyMethodFactory;
import org.rookit.convention.auto.metatype.source.type.reference.MetaTypeReferenceFactory;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class MetaTypeSourceFactoryImpl implements MetaTypeSourceFactory {

    private final TypeSourceFactory typeFactory;
    private final PropertyMethodFactory propertyMethods;
    private final MetaTypeReferenceFactory metaTypeReferences;

    @Inject
    private MetaTypeSourceFactoryImpl(
            final TypeSourceFactory typeFactory,
            final PropertyMethodFactory propertyMethods,
            final MetaTypeReferenceFactory metaTypeReferences) {

        this.typeFactory = typeFactory;
        this.propertyMethods = propertyMethods;
        this.metaTypeReferences = metaTypeReferences;
    }

    @Override
    public TypeSource apiFor(final ConventionTypeElement typeElement) {

        return this.typeFactory.createMutableInterface(this.metaTypeReferences.referenceFor(typeElement))
                .makePublic()
                .addInterface(this.metaTypeReferences.genericParentReferenceFor(typeElement));
    }

    @Override
    public TypeSource genericApiFor(final ConventionTypeElement typeElement) {

        return this.typeFactory.createMutableInterface(this.metaTypeReferences.genericReferenceFor(typeElement))
                .makePublic()
                .addInterfaces(this.metaTypeReferences.parentReferencesFor(typeElement))
                .addMethods(methodsForMetaType(typeElement));
    }

    private List<MethodSource> methodsForMetaType(final ConventionTypeElement typeElement) {

        return typeElement.properties()
                .stream()
                .map(property -> this.propertyMethods.apiFor(typeElement, property))
                .collect(toImmutableList());
    }

}
