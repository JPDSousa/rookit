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
package org.rookit.convention.auto.metatype.source.parameter;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;

import java.util.Collection;
import java.util.stream.Stream;

final class MetaTypeParameterSourceFactoryImpl implements MetaTypeParameterSourceFactory {

    private final PropertyParameterSourceFactory propertyParamFactory;

    @Inject
    private MetaTypeParameterSourceFactoryImpl(final PropertyParameterSourceFactory propertyParamFactory) {
        this.propertyParamFactory = propertyParamFactory;
    }

    @Override
    public Collection<ParameterSource> dependenciesFor(final ConventionTypeElement type) {

        return dependenciesFor(type, type);
    }

    private Collection<ParameterSource> dependenciesFor(final ConventionTypeElement declaringType,
                                                        final ConventionTypeElement type) {

        return StreamEx.of(declaringType.conventionInterfaces())
                .map(parent -> dependenciesFor(parent, type))
                .flatMap(Collection::stream)
                .append(declaredDependenciesFor(declaringType, type))
                .toImmutableList();
    }

    private Stream<ParameterSource> declaredDependenciesFor(
            final ConventionTypeElement declaringType,
            final ConventionTypeElement type) {

        return StreamEx.of(declaringType.declaredProperties())
                .map(property -> this.propertyParamFactory.parameterForProperty(declaringType, type, property));
    }

}
