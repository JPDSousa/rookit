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
package org.rookit.convention.auto.source.type.reference;

import com.google.inject.Inject;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.convention.auto.property.PropertyTypeResolver;

import java.util.function.Function;

final class PropertyTypeReferenceSourceFactoriesImpl implements PropertyTypeReferenceSourceFactories {

    private final TypeParameterSourceFactory parameterFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final PropertyFactory propertyFactory;
    private final PropertyTypeResolver resolver;
    private final ExtendedPackageElement defaultPackage;

    @Inject
    private PropertyTypeReferenceSourceFactoriesImpl(
            final TypeParameterSourceFactory parameterFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final PropertyFactory propertyFactory,
            final PropertyTypeResolver resolver,
            final ExtendedPackageElement defaultPackage) {

        this.parameterFactory = parameterFactory;
        this.referenceFactory = referenceFactory;
        this.propertyFactory = propertyFactory;
        this.resolver = resolver;
        this.defaultPackage = defaultPackage;
    }

    @Override
    public PropertyTypeReferenceSourceFactory createDispatcherFactory(
            final TypeReferenceSourceFactory referenceFactory) {

        return createDispatcherFactory(referenceFactory::create, referenceFactory);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createDispatcherFactory(
            final TypeReferenceSourceFactory referenceFactory,
            final ExtendedPackageElement basePackage) {

        return createDispatcherFactory(referenceFactory::create, this.resolver, referenceFactory);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createDispatcherFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final TypeReferenceSourceFactory referenceFactory) {

        return createDispatcherFactory(paramFunction, this.resolver, referenceFactory);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createDispatcherFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final PropertyTypeResolver resolver,
            final TypeReferenceSourceFactory referenceFactory) {

        return createDispatcherFactory(paramFunction, resolver, referenceFactory, this.defaultPackage);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createDispatcherFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final PropertyTypeResolver resolver,
            final TypeReferenceSourceFactory referenceFactory,
            final ExtendedPackageElement basePackage) {


        return new DispatcherPropertyReferenceFactory(
                this.propertyFactory,
                createContainerFactory(paramFunction, referenceFactory, basePackage),
                createNonContainerFactory(paramFunction, resolver, basePackage)
        );
    }

    @Override
    public PropertyContainerTypeReferenceSourceFactory createContainerFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final TypeReferenceSourceFactory referenceFactory) {

        return createContainerFactory(paramFunction, referenceFactory, this.defaultPackage);
    }

    @Override
    public PropertyContainerTypeReferenceSourceFactory createContainerFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final TypeReferenceSourceFactory referenceFactory,
            final ExtendedPackageElement basePackage) {

        return new BasePropertyContainerReferenceFactory(
                this.parameterFactory,
                referenceFactory,
                paramFunction,
                basePackage);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createNonContainerFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction) {

        return createNonContainerFactory(paramFunction, this.resolver);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createNonContainerFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final PropertyTypeResolver resolver) {

        return createNonContainerFactory(paramFunction, resolver, this.defaultPackage);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createNonContainerFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final PropertyTypeResolver resolver,
            final ExtendedPackageElement basePackage) {

        return createNonContainerFactory(paramFunction, this.referenceFactory, resolver, basePackage);
    }

    @Override
    public PropertyTypeReferenceSourceFactory createNonContainerFactory(
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            final TypeReferenceSourceFactory referenceFactory,
            final PropertyTypeResolver resolver,
            final ExtendedPackageElement basePackage) {

        return new NonContainerPropertyReferenceFactory(referenceFactory,
                                                        this.parameterFactory,
                                                        resolver,
                                                        paramFunction,
                                                        basePackage);
    }

    @Override
    public Function<ConventionTypeElement, TypeReferenceSource> parameterWithVariable(
            final TypeVariableSource variableSource) {

        return new PropertiesParamFunction(true, variableSource, this.referenceFactory);
    }

    @Override
    public Function<ConventionTypeElement, TypeReferenceSource> parameterWithoutVariable(
            final TypeVariableSource variableSource) {
        return new PropertiesParamFunction(false, variableSource, this.referenceFactory);
    }

}
