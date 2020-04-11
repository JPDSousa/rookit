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
package org.rookit.convention.auto.metatype.source.prototype;

import com.google.inject.Inject;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.annotation.PropertyAnnotationSourceFactory;
import org.rookit.convention.auto.metatype.source.parameter.PropertyParameterSourceFactory;
import org.rookit.convention.auto.metatype.source.type.reference.PropertyTypeReferenceFactory;
import org.rookit.convention.auto.property.Property;
import org.rookit.guice.auto.bind.BindingFactory;
import org.rookit.guice.auto.bind.ProviderMethodBinding;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.uncapitalize;

final class PropertyMethodPrototypesImpl implements PropertyMethodPrototypes {

    private final PropertyTypeReferenceFactory returnType;
    private final PropertyAnnotationSourceFactory annotationPrototype;
    private final BindingFactory bindingFactory;
    private final PropertyParameterSourceFactory parameterPrototypes;


    @Inject
    private PropertyMethodPrototypesImpl(
            final PropertyTypeReferenceFactory returnType,
            final PropertyAnnotationSourceFactory annotationPrototype,
            final BindingFactory bindingFactory,
            final PropertyParameterSourceFactory parameterPrototypes) {
        this.returnType = returnType;
        this.annotationPrototype = annotationPrototype;
        this.bindingFactory = bindingFactory;
        this.parameterPrototypes = parameterPrototypes;
    }

    @Override
    public ProviderMethodBinding bindingFor(final ConventionTypeElement enclosing, final Property property) {

        final TypeReferenceSource bindingSource = this.returnType.apiFor(enclosing, property);
        final String methodName = uncapitalize(enclosing.getSimpleName().toString()) + property.name();

        final Collection<ParameterSource> dependencies = this.parameterPrototypes.implDependenciesForProperty(enclosing,
                                                                                                              property);

        return this.bindingFactory.bindSingletonThroughProviderMethod(bindingSource, methodName)
                .throughConstructor(this.returnType.implFor(enclosing, property))
                .addBindingAnnotation(this.annotationPrototype.bindingAnnotationForProperty(enclosing, property))
                .addDependencies(dependencies)
                .build();
    }

}
