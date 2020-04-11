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
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.type.reference.MetaTypeReferenceFactory;
import org.rookit.convention.auto.property.PropertyModifiers;
import org.rookit.convention.property.guice.PropertyModelGet;

import javax.lang.model.element.Name;
import java.util.function.Function;

final class PropertyModelMethods implements PropertyModifiers<VisitorData> {

    private static final String GETTER = "getter";

    private final MetaTypeReferenceFactory metaTypeReferences;
    private final ExtendedExecutableElement propertyGet;
    private final ParameterSourceFactory parameterFactory;
    private final FieldSourceFactory fieldFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final MethodSourceFactory methodFactory;

    @Inject
    private PropertyModelMethods(
            final MetaTypeReferenceFactory metaTypeReferences,
            @PropertyModelGet final ExtendedExecutableElement propertyGet,
            final ParameterSourceFactory parameterFactory,
            final FieldSourceFactory fieldFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final MethodSourceFactory methodFactory) {
        this.metaTypeReferences = metaTypeReferences;
        this.propertyGet = propertyGet;
        this.parameterFactory = parameterFactory;
        this.fieldFactory = fieldFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.referenceFactory = referenceFactory;
        this.methodFactory = methodFactory;
    }

    @Override
    public VisitorData get(final VisitorData original) {

        final ConventionTypeElement typeElement = original.type();
        final ExtendedTypeMirror propertyType = original.property().type();
        final TypeReferenceSource propTypeReference = this.referenceFactory.create(propertyType);

        final MutableTypeSource targetSource = original.source()
                .addInjectedField(createGetterField(typeElement, propTypeReference))
                .addMethod(createGetterMethod(
                        typeElement,
                        propTypeReference
                ));


        return ImmutableVisitorData.builder()
                .from(original)
                .source(targetSource)
                .build();
    }

    private MethodSource createGetterMethod(final ConventionTypeElement typeElement,
                                            final TypeReferenceSource propertyType) {

        final Name paramName = this.propertyGet.getParameters()
                .get(0)
                .getSimpleName();
        final TypeReferenceSource paramType = this.metaTypeReferences.parentParameterTypeFor(typeElement);

        final ParameterSource parameter = this.parameterFactory.createMutable(paramName, paramType);

        return this.methodFactory.createMutableMethod(this.propertyGet.getSimpleName())
                .makePublic()
                .override()
                .withReturnType(propertyType)
                .addParameter(parameter)
                .addStatement("return this.$L.apply($L)", ImmutableList.of(GETTER, paramName));
    }

    private FieldSource createGetterField(final ConventionTypeElement typeElement,
                                          final TypeReferenceSource propertyType) {

        final TypeParameterSource fieldType = this.typeParameterFactory.create(
                Function.class,
                this.metaTypeReferences.parentParameterTypeFor(typeElement),
                propertyType
        );

        return this.fieldFactory.createMutable(fieldType, GETTER)
                .makePrivate()
                .makeFinal();
    }

}
