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
package org.rookit.convention.auto.source;

import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.property.PropertyTypeResolver;

import java.util.function.Function;

public interface PropertyTypeReferenceSourceFactories {

    PropertyTypeReferenceSourceFactory createDispatcherFactory(TypeReferenceSourceFactory referenceFactory);

    PropertyTypeReferenceSourceFactory createDispatcherFactory(
            Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            TypeReferenceSourceFactory referenceFactory);

    PropertyTypeReferenceSourceFactory createDispatcherFactory(
            Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            PropertyTypeResolver resolver, TypeReferenceSourceFactory referenceFactory);

    PropertyContainerTypeReferenceSourceFactory createContainerFactory(
            Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            TypeReferenceSourceFactory referenceFactory);

    PropertyTypeReferenceSourceFactory createNonContainerFactory(
            Function<ConventionTypeElement, TypeReferenceSource> paramFunction);

    PropertyTypeReferenceSourceFactory createNonContainerFactory(
            Function<ConventionTypeElement, TypeReferenceSource> paramFunction,
            PropertyTypeResolver resolver);

    // TODO what does this mean?
    Function<ConventionTypeElement, TypeReferenceSource> parameterWithVariableEntity(TypeVariableSource variableSource);

    // TODO what does this mean?
    Function<ConventionTypeElement, TypeReferenceSource> parameterWithoutVariableEntity(TypeVariableSource variableSource);

}
