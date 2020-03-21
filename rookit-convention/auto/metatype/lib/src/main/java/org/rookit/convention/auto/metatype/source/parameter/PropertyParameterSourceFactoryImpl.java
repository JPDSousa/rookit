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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.annotation.PropertyAnnotationSourceFactory;
import org.rookit.convention.auto.metatype.source.type.reference.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.auto.property.Property;

import java.util.Collection;

final class PropertyParameterSourceFactoryImpl implements PropertyParameterSourceFactory {

    private final PropertyTypeReferenceSourceFactory typePrototypes;
    private final PropertyAnnotationSourceFactory annotationPrototype;
    private final ParameterSourceFactory parameterFactory;

    @Inject
    private PropertyParameterSourceFactoryImpl(
            final PropertyTypeReferenceSourceFactory typePrototypes,
            final PropertyAnnotationSourceFactory annotationPrototype,
            final ParameterSourceFactory parameterFactory) {
        this.typePrototypes = typePrototypes;
        this.annotationPrototype = annotationPrototype;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public ParameterSource parameterForProperty(final ConventionTypeElement element, final Property property) {

        final TypeReferenceSource paramType = this.typePrototypes.apiForProperty(element, property);
        final AnnotationSource annotation = this.annotationPrototype.bindingAnnotationForProperty(element,
                                                                                                  property);

        return this.parameterFactory.createMutable(property.name(), paramType)
                .addAnnotation(annotation);
    }

    @Override
    public Collection<ParameterSource> implDependenciesForProperty(final ConventionTypeElement element,
                                                                   final Property property) {
        // TODO add dependencies here
        return ImmutableList.of();
    }

}
