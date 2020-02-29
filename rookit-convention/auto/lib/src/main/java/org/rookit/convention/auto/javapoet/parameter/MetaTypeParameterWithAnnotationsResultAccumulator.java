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

import com.google.inject.Inject;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;
import org.rookit.convention.auto.javax.naming.PropertyIdentifierFactory;
import org.rookit.convention.auto.property.Property;
import org.rookit.guice.auto.annotation.Guice;

import java.util.function.BiFunction;

final class MetaTypeParameterWithAnnotationsResultAccumulator
        implements BiFunction<Property, ParameterSource, ParameterSource> {

    private final PropertyIdentifierFactory guiceIdentifierFactory;
    private final ParameterSourceFactory parameterFactory;
    private final AnnotationSourceFactory annotationFactory;

    @Inject
    MetaTypeParameterWithAnnotationsResultAccumulator(
            @Guice final PropertyIdentifierFactory identifierFactory,
            final ParameterSourceFactory parameterFactory,
            final AnnotationSourceFactory annotationFactory) {
        this.guiceIdentifierFactory = identifierFactory;
        this.parameterFactory = parameterFactory;
        this.annotationFactory = annotationFactory;
    }

    @Override
    public ParameterSource apply(final Property property, final ParameterSource parameterSource) {

        final Identifier identifier = this.guiceIdentifierFactory.create(property);
        final AnnotationSource annotation = this.annotationFactory.create(identifier);

        return this.parameterFactory.makeMutable(parameterSource)
                .addAnnotation(annotation);
    }

    @Override
    public String toString() {
        return "MetaTypeParameterWithAnnotationsResultAccumulator{" +
                "guiceIdentifierFactory=" + this.guiceIdentifierFactory +
                ", parameterFactory=" + this.parameterFactory +
                ", annotationFactory=" + this.annotationFactory +
                "}";
    }

}
