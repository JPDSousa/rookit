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
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.MutableParameterSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.metatype.source.type.reference.MetaTypeReferenceFactory;
import org.rookit.convention.auto.property.MutablePropertyModifiers;
import org.rookit.convention.auto.property.PropertyModifiers;
import org.rookit.convention.property.guice.MutablePropertyModelSet;

import javax.lang.model.element.Name;
import java.util.List;
import java.util.function.BiConsumer;

final class MutablePropertyModelMethods implements MutablePropertyModifiers<VisitorData> {

    private static final CharSequence SETTER_FIELD = "setter";

    private final PropertyModifiers<VisitorData> delegate;
    private final MethodSourceFactory methodFactory;
    private final FieldSourceFactory fieldFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final MetaTypeReferenceFactory metaTypeReferences;
    private final TypeReferenceSourceFactory referenceFactory;
    private final ParameterSourceFactory parameterFactory;

    private final ExtendedExecutableElement singleSetter;

    MutablePropertyModelMethods(
            final PropertyModifiers<VisitorData> delegate,
            final MethodSourceFactory methodFactory,
            final FieldSourceFactory fieldFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final MetaTypeReferenceFactory metaTypeReferences,
            final TypeReferenceSourceFactory referenceFactory,
            final ParameterSourceFactory parameterFactory,
            @MutablePropertyModelSet final ExtendedExecutableElement singleSetter) {

        this.delegate = delegate;
        this.methodFactory = methodFactory;
        this.fieldFactory = fieldFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.metaTypeReferences = metaTypeReferences;
        this.referenceFactory = referenceFactory;
        this.parameterFactory = parameterFactory;
        this.singleSetter = singleSetter;
    }

    @Override
    public VisitorData set(final VisitorData original) {

        final TypeReferenceSource typeType = this.metaTypeReferences.parentParameterTypeFor(original.type());

        final TypeReferenceSource propertyType = this.referenceFactory.create(
                original.property().type()
        );

        final MutableTypeSource targetSource = original.source()
                .addMethod(setterMethod(typeType, propertyType))
                .addField(setterField(typeType, propertyType));

        return ImmutableVisitorData.builder()
                .from(original)
                .source(targetSource)
                .build();
    }

    @Override
    public VisitorData get(final VisitorData original) {

        return this.delegate.get(original);
    }

    private FieldSource setterField(final TypeReferenceSource typeType,
                                    final TypeReferenceSource propertyType) {

        final TypeParameterSource fieldType = this.typeParameterFactory.create(
                BiConsumer.class,
                typeType,
                propertyType
        );
        return this.fieldFactory.createMutable(fieldType, SETTER_FIELD)
                .makePrivate()
                .makeFinal();
    }

    private MethodSource setterMethod(final TypeReferenceSource typeType,
                                      final TypeReferenceSource propertyType) {

        final List<? extends ExtendedVariableElement> params = this.singleSetter.getParameters();

        final Name typeParamName = params.get(0)
                .getSimpleName();

        final Name propParamName = params.get(1)
                .getSimpleName();

        final ParameterSource typeParam = this.parameterFactory.createMutable(typeParamName, typeType);
        final MutableParameterSource propParam = this.parameterFactory.createMutable(propParamName, propertyType);

        return this.methodFactory.createMutableMethod(this.singleSetter.getSimpleName())
                .makePublic()
                .override()
                .addParameter(typeParam)
                .addParameter(propParam)
                .addStatement("this.$L.accept($L, $L)",
                              ImmutableList.of(SETTER_FIELD, typeParamName, propParamName));
    }

}
