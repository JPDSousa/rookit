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

import org.rookit.auto.javax.repetition.KeyedRepetitiveTypeMirror;
import org.rookit.auto.javax.repetition.RepetitiveTypeMirror;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyTypeResolver;

import java.util.function.Function;

final class NonContainerPropertyReferenceFactory implements PropertyTypeReferenceSourceFactory {

    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeParameterSourceFactory parameterFactory;
    private final PropertyTypeResolver propertyTypeResolver;
    private final Function<ConventionTypeElement, TypeReferenceSource> paramFunction;

    NonContainerPropertyReferenceFactory(
            final TypeReferenceSourceFactory referenceFactory,
            final TypeParameterSourceFactory parameterFactory,
            final PropertyTypeResolver propertyTypeResolver,
            final Function<ConventionTypeElement, TypeReferenceSource> paramFunction) {
        this.referenceFactory = referenceFactory;
        this.parameterFactory = parameterFactory;
        this.propertyTypeResolver = propertyTypeResolver;
        this.paramFunction = paramFunction;
    }

    @Override
    public TypeReferenceSource create(final ConventionTypeElement owner, final Property property) {

        final TypeReferenceSource reference = this.paramFunction.apply(owner);
        final ExtendedTypeMirror type = property.type().boxIfPrimitive();
        final TypeReferenceSource propType = unwrapIfRepetitive(type);
        final Class<?> clazz = this.propertyTypeResolver.resolve(property);

        if (type instanceof KeyedRepetitiveTypeMirror) {
            final ExtendedTypeMirror keyType = ((KeyedRepetitiveTypeMirror) type).unwrapKey();
            // TODO this has to be changed, as it is boxing stuff
            final TypeReferenceSource keyClass = this.referenceFactory.create(keyType.boxIfPrimitive());
            return this.parameterFactory.create(clazz, reference, keyClass, propType);
        }

        return this.parameterFactory.create(clazz, reference, propType);
    }

    private TypeReferenceSource unwrapIfRepetitive(final ExtendedTypeMirror typeMirror) {
        if (typeMirror instanceof RepetitiveTypeMirror) {
            return this.referenceFactory.create(((RepetitiveTypeMirror) typeMirror).unwrapValue());
        }

        return this.referenceFactory.create(typeMirror);
    }

    @Override
    public String toString() {
        return "NonContainerPropertyReferenceFactory{" +
                "referenceFactory=" + this.referenceFactory +
                ", parameterFactory=" + this.parameterFactory +
                ", propertyTypeResolver=" + this.propertyTypeResolver +
                ", paramFunction=" + this.paramFunction +
                "}";
    }

}
