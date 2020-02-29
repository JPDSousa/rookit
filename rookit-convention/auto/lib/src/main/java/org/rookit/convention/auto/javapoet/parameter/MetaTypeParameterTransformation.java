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
package org.rookit.convention.auto.javapoet.parameter;

import one.util.streamex.StreamEx;
import org.rookit.auto.source.parameter.MutableParameterSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.source.PropertyTypeReferenceSourceFactory;

import java.util.function.BiFunction;

final class MetaTypeParameterTransformation
        implements BiFunction<ConventionTypeElement, Property, StreamEx<ParameterSource>> {

    private final PropertyTypeReferenceSourceFactory propertyReferenceFactory;
    private final BiFunction<Property, ParameterSource, ParameterSource> resultAccumulator;
    private final ParameterSourceFactory parameterFactory;

    MetaTypeParameterTransformation(
            final PropertyTypeReferenceSourceFactory propRefFactory,
            final BiFunction<Property, ParameterSource, ParameterSource> resultAccumulator,
            final ParameterSourceFactory parameterFactory) {
        this.propertyReferenceFactory = propRefFactory;
        this.resultAccumulator = resultAccumulator;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public StreamEx<ParameterSource> apply(final ConventionTypeElement element, final Property property) {

        final TypeReferenceSource paramType = this.propertyReferenceFactory.create(element, property);
        final MutableParameterSource parameter = this.parameterFactory.createMutable(property.name(), paramType);

        return StreamEx.of(this.resultAccumulator.apply(property, parameter));
    }

    @Override
    public String toString() {
        return "MetaTypeParameterTransformation{" +
                "propertyReferenceFactory=" + this.propertyReferenceFactory +
                ", resultAccumulator=" + this.resultAccumulator +
                ", parameterFactory=" + this.parameterFactory +
                "}";
    }

}
