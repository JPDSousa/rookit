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
package org.rookit.convention.auto.metatype.source;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.type.reference.PropertyTypeReferenceFactory;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.guice.MetaTypeProperty;
import org.rookit.utils.guice.Separator;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

import javax.lang.model.element.Name;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.repeat;

final class PropertyFetcherFactory implements MetaTypePropertyFetcherFactory {

    private static final String ACCESSOR_NAME = "propertyMap";
    private static final String OPTIONAL_FACTORY = "optionalFactory";

    private final ExtendedExecutableElement apiMethod;

    private final MethodSourceFactory methodFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final FieldSourceFactory fieldFactory;
    private final PropertyTypeReferenceFactory references;

    private final TypeReferenceSource keyReference;
    private final ParameterSource optFactoryParam;

    private final TypeReferenceSource mapReference;
    private final List<FieldSource> secondaryFields;

    private final ArbitraryCodeSourceFactory codeFactory;
    private final String lineSeparator;

    @Inject
    private PropertyFetcherFactory(
            final FieldSourceFactory fieldFactory,
            final MethodSourceFactory methodFactory,
            final PropertyTypeReferenceFactory references,
            final ArbitraryCodeSourceFactory codeFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final ParameterSourceFactory parameterFactory,
            @MetaTypeProperty final ExtendedExecutableElement apiMethod,
            @From(String.class) final TypeReferenceSource stringReference,
            @From(Map.class) final TypeReferenceSource mapReference,
            @From(OptionalFactory.class) final TypeReferenceSource optFactoryReference,
            @Separator final String lineSeparator) {

        this.fieldFactory = fieldFactory;
        this.references = references;
        this.apiMethod = apiMethod;
        this.methodFactory = methodFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.codeFactory = codeFactory;
        this.keyReference = stringReference;
        this.lineSeparator = lineSeparator;

        this.optFactoryParam = parameterFactory.createMutable(OPTIONAL_FACTORY, optFactoryReference);
        this.mapReference = mapReference;

        this.secondaryFields = ImmutableList.of(
                this.fieldFactory.createMutable(optFactoryReference, OPTIONAL_FACTORY)
                        .makePrivate()
                        .makeFinal()
        );
    }

    @Override
    public MethodSource methodFor(final ExtendedTypeMirror type) {

        final Name paramName = this.apiMethod.getParameters()
                .get(0)
                .getSimpleName();

        return this.methodFactory.createMutableOverride(this.apiMethod)
                .withReturnType(methodReferenceFor(type))
                .addStatement("return this.$L.ofNullable(this.$L.get($L))",
                              ImmutableList.of(OPTIONAL_FACTORY, ACCESSOR_NAME, paramName));
    }

    private TypeParameterSource methodReferenceFor(final ExtendedTypeMirror type) {

        return this.typeParameterFactory.create(
                Optional.class,
                this.references.genericFor(type)
        );
    }

    @Override
    public MethodSource delegateMethodFor(
            final ExtendedTypeMirror type, final FieldSource delegate) {

        return this.methodFactory.createMutableDelegateOverride(this.apiMethod, delegate)
                .withReturnType(methodReferenceFor(type));
    }

    @Override
    public PropertyFetcherFields fieldsFor(final ConventionTypeElement typeElement) {

        final TypeReferenceSource fieldReference = this.typeParameterFactory.create(
                this.mapReference,
                this.keyReference,
                this.references.genericFor(typeElement));

        return new PropertyFetcherFieldsImpl(
                this.fieldFactory.createMutable(fieldReference, ACCESSOR_NAME)
                        .makePrivate()
                        .makeFinal(),
                this.secondaryFields
        );
    }

    @Override
    public ArbitraryCodeSource initializerFor(final ConventionTypeElement typeElement) {

        final Collection<Property> properties = typeElement.properties();
        final int nProperties = properties.size();
        final int expectedSize = 4 + (nProperties * 2);

        final ImmutableList.Builder<Object> argsBuilder = ImmutableList.builderWithExpectedSize(expectedSize)
                .add(ACCESSOR_NAME, ImmutableMap.class, this.keyReference, this.references.genericFor(typeElement));

        final String format = "this.$L = $T.<$T, $T>builder()"
                + repeat(this.lineSeparator + ".put(\"$L\", $L)", nProperties)
                + this.lineSeparator + ".build()";


        for (final Property property : properties) {
            // once for the key
            argsBuilder.add(property.name());
            // once for the value
            argsBuilder.add(property.name());
        }

        return this.codeFactory.createFromFormat(
                format,
                argsBuilder.build()
        );
    }

    @Override
    public Collection<ParameterSource> constructorParametersFor(final ConventionTypeElement typeElement) {

        return ImmutableList.of(this.optFactoryParam);
    }

}
