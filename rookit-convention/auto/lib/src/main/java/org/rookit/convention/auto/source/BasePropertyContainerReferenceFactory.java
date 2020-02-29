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

import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.property.ContainerProperty;

import java.util.function.Function;

final class BasePropertyContainerReferenceFactory implements PropertyContainerTypeReferenceSourceFactory {

    private final TypeParameterSourceFactory parameterFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final Function<ConventionTypeElement, TypeReferenceSource> paramFunction;

    BasePropertyContainerReferenceFactory(
            final TypeParameterSourceFactory parameterFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction) {
        this.parameterFactory = parameterFactory;
        this.referenceFactory = referenceFactory;
        this.paramFunction = paramFunction;
    }

    @Override
    public TypeReferenceSource create(final ConventionTypeElement owner, final ContainerProperty property) {

        final TypeReferenceSource typeName = this.paramFunction.apply(owner);
        final TypeReferenceSource baseName = this.referenceFactory.create(property.typeAsElement());

        return this.parameterFactory.create(baseName, typeName);
    }

    @Override
    public String toString() {
        return "BasePropertyContainerReferenceFactory{" +
                "parameterFactory=" + this.parameterFactory +
                ", referenceFactory=" + this.referenceFactory +
                ", paramFunction=" + this.paramFunction +
                "}";
    }

}
