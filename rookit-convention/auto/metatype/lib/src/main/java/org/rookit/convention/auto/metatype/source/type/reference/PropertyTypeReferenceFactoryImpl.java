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
import org.rookit.auto.source.type.variable.WildcardVariableSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.property.PropertyModel;
import org.rookit.utils.string.StringUtils;

final class PropertyTypeReferenceFactoryImpl implements PropertyTypeReferenceFactory {

    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final WildcardVariableSourceFactory wildcardFactory;
    private final TypeVariableSource variableName;
    private final IdentifierTransformer idTransformer;
    private final StringUtils stringUtils;

    @Inject
    private PropertyTypeReferenceFactoryImpl(
            final TypeReferenceSourceFactory referenceFactory,
            final TypeParameterSourceFactory parameterFactory,
            final WildcardVariableSourceFactory wildcardFactory,
            @MetaTypeAPI final TypeVariableSource variableName,
            final IdentifierTransformer idTransformer,
            final StringUtils stringUtils) {

        this.referenceFactory = referenceFactory;
        this.typeParameterFactory = parameterFactory;
        this.wildcardFactory = wildcardFactory;
        this.variableName = variableName;
        this.idTransformer = idTransformer;
        this.stringUtils = stringUtils;
    }

    @Override
    public TypeReferenceSource apiFor(final ConventionTypeElement enclosing,
                                      final Property property) {

        return apiForProperty(enclosing, property, false);
    }

    @Override
    public TypeReferenceSource nativeApiFor(final ConventionTypeElement enclosing,
                                            final Property property) {

        return apiForProperty(enclosing, property, true);
    }

    @Override
    public TypeReferenceSource genericFor(final ExtendedTypeMirror mirror) {

        return this.typeParameterFactory.create(
                PropertyModel.class,
                this.referenceFactory.create(mirror.boxIfPrimitive()),
                this.wildcardFactory.newWildcard()
        );
    }

    private TypeReferenceSource apiForProperty(final ConventionTypeElement enclosing,
                                               final Property property,
                                               final boolean useNative) {

        // FIXME the boolean is definitely not a constant value
        final TypeReferenceSource reference = parameterFor(enclosing, useNative);
        final ExtendedTypeMirror type = property.type().boxIfPrimitive();
        final TypeReferenceSource propType = unwrapIfRepetitive(type);
        final Class<?> clazz = property.propertyModel();

        if (type instanceof KeyedRepetitiveTypeMirror) {
            final ExtendedTypeMirror keyType = ((KeyedRepetitiveTypeMirror) type).unwrapKey();
            // TODO this has to be changed, as it is boxing stuff
            final TypeReferenceSource keyClass = this.referenceFactory.create(keyType.boxIfPrimitive());
            return this.typeParameterFactory.create(clazz, reference, keyClass, propType);
        }

        return this.typeParameterFactory.create(clazz, reference, propType);
    }

    private TypeReferenceSource unwrapIfRepetitive(final ExtendedTypeMirror typeMirror) {
        if (typeMirror instanceof RepetitiveTypeMirror) {
            return this.referenceFactory.create(((RepetitiveTypeMirror) typeMirror).unwrapValue());
        }

        return this.referenceFactory.create(typeMirror);
    }

    private TypeReferenceSource parameterFor(final ConventionTypeElement element, final boolean useNative) {

        if (!element.isPropertyContainer() && (useNative || !element.isEntity())) {
            return this.variableName;
        }
        return this.referenceFactory.create(element);
    }

    @Override
    public TypeReferenceSource implFor(final ConventionTypeElement enclosing, final Property property) {

        final ExtendedPackageElement packageRef
                = this.idTransformer.transformPackage(enclosing.packageInfo());
        final String name = this.idTransformer.transformName(
                enclosing.getSimpleName())
                + this.stringUtils.capitalizeFirstChar(property.name().toString())
                + "Impl";

        final TypeReferenceSource baseName = this.referenceFactory.fromSplitPackageAndName(packageRef, name);

        if (enclosing.isPartialEntity()) {
            return this.typeParameterFactory.create(baseName, this.variableName);
        }

        return baseName;
    }

}
