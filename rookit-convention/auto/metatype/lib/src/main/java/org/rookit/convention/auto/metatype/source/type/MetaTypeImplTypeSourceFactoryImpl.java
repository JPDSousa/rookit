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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.MetaType;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.MetaTypeModelSerializerFactory;
import org.rookit.convention.auto.metatype.source.MetaTypeModelTypeFactory;
import org.rookit.convention.auto.metatype.source.MetaTypePropertyFetcherFactory;
import org.rookit.convention.auto.metatype.source.field.PropertyFieldFactory;
import org.rookit.convention.auto.metatype.source.method.MetaTypeConstructorSourceFactory;
import org.rookit.convention.auto.metatype.source.method.MetaTypePropertiesMethodFactory;
import org.rookit.convention.auto.metatype.source.method.PropertyMethodFactory;
import org.rookit.convention.auto.metatype.source.type.reference.MetaTypeReferenceFactory;
import org.rookit.convention.auto.property.Property;

import java.util.Collection;

final class MetaTypeImplTypeSourceFactoryImpl implements MetaTypeImplTypeSourceFactory {

    private static final String DELEGATE = "metaType";

    private final TypeReferenceSource metaType;

    private final TypeSourceFactory typeFactory;
    private final FieldSourceFactory fieldFactory;
    private final TypeParameterSourceFactory typeParameterFactory;

    private final MetaTypeReferenceFactory references;
    private final MetaTypeConstructorSourceFactory constructorFactory;
    private final MetaTypeModelTypeFactory modelTypeFactory;
    private final MetaTypePropertyFetcherFactory propertyFetchers;
    private final MetaTypePropertiesMethodFactory properties;
    private final MetaTypeModelSerializerFactory serializers;

    private final PropertyFieldFactory propertyFields;
    private final PropertyMethodFactory propertyMethods;

    @Inject
    private MetaTypeImplTypeSourceFactoryImpl(
            @From(MetaType.class) final TypeReferenceSource metaType,
            final TypeSourceFactory typeFactory,
            final FieldSourceFactory fieldFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final MetaTypeReferenceFactory references,
            final MetaTypeConstructorSourceFactory constructorFactory,
            final MetaTypeModelTypeFactory modelTypeFactory,
            final MetaTypePropertyFetcherFactory propertyFetchers,
            final MetaTypePropertiesMethodFactory properties,
            final MetaTypeModelSerializerFactory serializers,
            final PropertyFieldFactory propertyFields,
            final PropertyMethodFactory propertyMethods) {

        this.metaType = metaType;

        this.typeFactory = typeFactory;
        this.fieldFactory = fieldFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.references = references;
        this.constructorFactory = constructorFactory;
        this.modelTypeFactory = modelTypeFactory;
        this.propertyFetchers = propertyFetchers;
        this.properties = properties;
        this.serializers = serializers;
        this.propertyFields = propertyFields;
        this.propertyMethods = propertyMethods;
    }

    @Override
    public TypeSource implFor(final ConventionTypeElement typeElement) {

        final Collection<Property> properties = typeElement.properties();

        return this.typeFactory.createMutableClass(this.references.implReferenceFor(typeElement))
                .addInterface(this.references.referenceFor(typeElement))
                // base data
                .addFields(baseFieldsFor(typeElement))
                .addMethods(baseExecutablesFor(typeElement))
                // property data
                .addFields(this.propertyFields.fieldsForProperties(typeElement, properties))
                .addMethods(this.propertyMethods.implFor(typeElement, properties));
    }

    @Override
    public MutableTypeSource injectDelegate(
            final MutableTypeSource original,
            final ExtendedTypeMirror type) {

        final FieldSource delegate = createDelegate(type);

        return original
                .addInjectedField(delegate)
                .addMethod(this.properties.delegateMethodFor(type, delegate))
                .addMethod(this.serializers.delegateMethodFor(type, delegate))
                .addMethod(this.modelTypeFactory.delegateMethodFor(type, delegate))
                .addMethod(this.propertyFetchers.delegateMethodFor(type, delegate));
    }

    private FieldSource createDelegate(final ExtendedTypeMirror type) {

        final TypeReferenceSource reference = this.typeParameterFactory.create(this.metaType, type);

        return this.fieldFactory.createMutable(reference, DELEGATE)
                .makePrivate()
                .makeFinal();
    }

    private Iterable<FieldSource> baseFieldsFor(final ConventionTypeElement typeElement) {

        return ImmutableList.<FieldSource>builder()
                .addAll(this.propertyFetchers.fieldsFor(typeElement).asCollection())
                .add(this.serializers.fieldFor(typeElement.asType()))
                .build();
    }

    private Iterable<MethodSource> baseExecutablesFor(final ConventionTypeElement typeElement) {

        final ExtendedTypeMirror mirror = typeElement.asType();

        return ImmutableList.of(
                this.constructorFactory.constructorFor(typeElement),
                this.modelTypeFactory.methodFor(mirror),
                this.propertyFetchers.methodFor(mirror),
                this.properties.implFor(typeElement),
                this.serializers.methodFor(mirror)
        );
    }

}
