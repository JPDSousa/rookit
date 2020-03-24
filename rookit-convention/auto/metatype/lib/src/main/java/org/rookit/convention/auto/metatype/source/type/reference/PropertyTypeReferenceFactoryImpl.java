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

import com.google.inject.Inject;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.repetition.KeyedRepetitiveTypeMirror;
import org.rookit.auto.javax.repetition.RepetitiveTypeMirror;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.auto.property.ContainerProperty;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyTypeResolver;

final class PropertyTypeReferenceFactoryImpl implements PropertyTypeReferenceFactory {

    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeParameterSourceFactory parameterFactory;
    private final PropertyTypeResolver propertyTypeResolver;
    private final TypeVariableSource variableName;
    private final IdentifierTransformer idTransformer;

    @Inject
    private PropertyTypeReferenceFactoryImpl(
            final TypeReferenceSourceFactory referenceFactory,
            final TypeParameterSourceFactory parameterFactory,
            final PropertyTypeResolver propertyTypeResolver,
            @MetaTypeAPI final TypeVariableSource variableName,
            final IdentifierTransformer idTransformer) {
        this.referenceFactory = referenceFactory;
        this.parameterFactory = parameterFactory;
        this.propertyTypeResolver = propertyTypeResolver;
        this.variableName = variableName;
        this.idTransformer = idTransformer;
    }

    @Override
    public TypeReferenceSource apiForProperty(final ConventionTypeElement enclosing,
                                              final Property property) {

        return apiForProperty(enclosing, property, false);
    }

    @Override
    public TypeReferenceSource genericApiForProperty(final ConventionTypeElement enclosing, final Property property) {

        return apiForProperty(enclosing, property, true);
    }

    private TypeReferenceSource apiForProperty(final ConventionTypeElement enclosing,
                                               final Property property,
                                               final boolean useGeneric) {

        if (property instanceof ContainerProperty) {
            return apiForContainer(enclosing, (ContainerProperty) property, useGeneric);
        }

        // FIXME the boolean is definitely not a constant value
        final TypeReferenceSource reference = parameterFor(enclosing, useGeneric);
        final ExtendedTypeMirror type = property.type().boxIfPrimitive();
        final TypeReferenceSource propType = unwrapIfRepetitive(type);
        final Class<?> clazz = this.propertyTypeResolver.resolve(property);

        if (type instanceof KeyedRepetitiveTypeMirror) {
            final ExtendedTypeMirror keyType = ((KeyedRepetitiveTypeMirror) type).unwrapKey();
            // TODO this has to be changed, as it is boxing stuff
            final TypeReferenceSource keyClass = this.referenceFactory.create(keyType.boxIfPrimitive());
            return this.parameterFactory.create(clazz, reference, keyClass, propType);
        }

        return this.parameterFactory.create(clazz, reference, propType);
    }

    private TypeReferenceSource unwrapIfRepetitive(final ExtendedTypeMirror typeMirror) {
        if (typeMirror instanceof RepetitiveTypeMirror) {
            return this.referenceFactory.create(((RepetitiveTypeMirror) typeMirror).unwrapValue());
        }

        return this.referenceFactory.create(typeMirror);
    }

    private TypeReferenceSource parameterFor(final ConventionTypeElement element, final boolean useGeneric) {

        if (!element.isPropertyContainer() && (useGeneric || !element.isEntity())) {
            return this.variableName;
        }
        return this.referenceFactory.create(element);
    }

    @Override
    public TypeReferenceSource apiForContainer(final ConventionTypeElement enclosing,
                                               final ContainerProperty container) {

        return apiForContainer(enclosing, container, false);
    }

    @Override
    public TypeReferenceSource genericApiForContainer(final ConventionTypeElement enclosing,
                                                      final ContainerProperty container) {

        return apiForContainer(enclosing, container, true);
    }

    private TypeReferenceSource apiForContainer(final ConventionTypeElement enclosing,
                                                final ContainerProperty container,
                                                final boolean useGeneric) {

        // FIXME the boolean is definitely not a constant value
        final TypeReferenceSource typeName = parameterFor(enclosing, useGeneric);
        final TypeReferenceSource baseName = this.referenceFactory.create(container.typeAsElement());

        return this.parameterFactory.create(baseName, typeName);
    }

    @Override
    public TypeReferenceSource implForProperty(final ConventionTypeElement enclosing, final Property property) {

        final ExtendedPackageElement packageRef
                = this.idTransformer.transformPackage(enclosing.packageInfo());
        final String name = this.idTransformer.transformName(enclosing.getSimpleName()) + property.name() + "Impl";

        return this.referenceFactory.fromSplitPackageAndName(packageRef, name);
    }

    @Override
    public TypeReferenceSource implForContainer(final ConventionTypeElement enclosing,
                                                final ContainerProperty container) {

        return implForProperty(enclosing, container);
    }

}
