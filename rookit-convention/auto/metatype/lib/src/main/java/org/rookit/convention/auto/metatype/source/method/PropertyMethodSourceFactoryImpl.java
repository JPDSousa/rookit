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

import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.type.reference.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.auto.property.Property;

final class PropertyMethodSourceFactoryImpl implements PropertyMethodSourceFactory {

    private final MethodSourceFactory methodFactory;
    private final PropertyTypeReferenceSourceFactory returnType;

    @Inject
    private PropertyMethodSourceFactoryImpl(
            final MethodSourceFactory methodFactory,
            final PropertyTypeReferenceSourceFactory returnType) {
        this.methodFactory = methodFactory;
        this.returnType = returnType;
    }

    private MutableMethodSource baseMethod(final Property property) {
        return this.methodFactory.createMutableMethod(property.name())
                .makePublic();
    }

    @Override
    public MethodSource apiFor(final ConventionTypeElement enclosing, final Property property) {
        return baseMethod(property)
                .makeAbstract()
                .withReturnType(this.returnType.apiForProperty(enclosing, property));
    }

    @Override
    public MethodSource implFor(final ConventionTypeElement enclosing, final Property property) {

        final TypeReferenceSource returnType = this.returnType.apiForProperty(enclosing, property);

        return baseMethod(property)
                .addAnnotationByClass(Override.class)
                .returnInstanceField(returnType, property.name());
    }

}
