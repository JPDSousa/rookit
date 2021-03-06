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
package org.rookit.convention.auto.metatype.source.method;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.MetaTypeModelSerializerFactory;
import org.rookit.convention.auto.metatype.source.MetaTypePropertyFetcherFactory;
import org.rookit.convention.auto.metatype.source.parameter.MetaTypeParameterSourceFactory;

final class MetaTypeConstructorSourceFactoryImpl implements MetaTypeConstructorSourceFactory {

    private final MethodSourceFactory methodFactory;
    private final MetaTypeParameterSourceFactory parameters;
    private final MetaTypePropertyFetcherFactory propertyFetchers;
    private final MetaTypeModelSerializerFactory serializers;

    @Inject
    private MetaTypeConstructorSourceFactoryImpl(
            final MethodSourceFactory methodFactory,
            final MetaTypeParameterSourceFactory parameters,
            final MetaTypePropertyFetcherFactory propertyFetchers,
            final MetaTypeModelSerializerFactory serializers) {
        this.methodFactory = methodFactory;
        this.parameters = parameters;
        this.propertyFetchers = propertyFetchers;
        this.serializers = serializers;
    }

    @Override
    public MethodSource constructorFor(final ConventionTypeElement typeElement) {

        return this.methodFactory.createMutableConstructor()
                .makePrivate()
                .addAnnotationByClass(Inject.class)
                .assignParametersToFields(this.parameters.dependenciesFor(typeElement))
                .assignParametersToFields(this.propertyFetchers.constructorParametersFor(typeElement))
                .assignParametersToFields(ImmutableList.of(this.serializers.parameterFor(typeElement.asType())))
                .addStatement(this.propertyFetchers.initializerFor(typeElement));
    }

}
