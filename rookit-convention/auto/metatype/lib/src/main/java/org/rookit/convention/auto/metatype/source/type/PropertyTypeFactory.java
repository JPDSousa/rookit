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
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.type.reference.PropertyTypeReferenceFactory;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.property.guice.PropertyModelPropertyName;

final class PropertyTypeFactory implements PropertyImplTypeFactory {

    private final MethodSourceFactory methodFactory;
    private final TypeSourceFactory typeFactory;
    private final PropertyTypeReferenceFactory references;
    private final ExtendedExecutableElement propertyName;
    private final MetaTypeImplTypeSourceFactory metaType;
    private final PropertySpecifics propertySpecifics;

    @Inject
    private PropertyTypeFactory(
            final MethodSourceFactory methodFactory,
            final TypeSourceFactory typeFactory,
            final PropertyTypeReferenceFactory references,
            @PropertyModelPropertyName final ExtendedExecutableElement propertyName,
            final MetaTypeImplTypeSourceFactory metaType,
            final PropertySpecifics propertySpecifics) {

        this.methodFactory = methodFactory;

        this.typeFactory = typeFactory;
        this.references = references;
        this.propertyName = propertyName;
        this.metaType = metaType;
        this.propertySpecifics = propertySpecifics;
    }

    @Override
    public TypeSource implFor(final ConventionTypeElement typeElement, final Property property) {

        final ExtendedTypeMirror propertyType = property.type();

        final TypeReferenceSource typeReference = this.references.implFor(
                typeElement,
                property);

        final MutableTypeSource baseType = this.typeFactory.createMutableClass(typeReference)
                .addInterface(this.references.apiFor(typeElement, property));

        final MutableTypeSource source = this.metaType.injectDelegate(baseType, propertyType)
                .addMethod(createPropertyName(property));

        return property.accept(
                this.propertySpecifics,
                ImmutableVisitorData.builder()
                        .property(property)
                        .type(typeElement)
                        .source(source)
                        .build()
        );
    }

    private MethodSource createPropertyName(final Property property) {

        return this.methodFactory.createMutableOverride(this.propertyName)
                .addStatement("return $S", ImmutableList.of(property.name()));
    }

}
