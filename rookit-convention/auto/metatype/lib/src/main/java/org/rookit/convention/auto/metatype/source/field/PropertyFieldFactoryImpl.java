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
package org.rookit.convention.auto.metatype.source.field;

import com.google.inject.Inject;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.type.reference.PropertyTypeReferenceFactory;
import org.rookit.convention.auto.property.Property;

final class PropertyFieldFactoryImpl implements PropertyFieldFactory {

    private final FieldSourceFactory fieldFactory;
    private final PropertyTypeReferenceFactory references;

    @Inject
    private PropertyFieldFactoryImpl(
            final FieldSourceFactory fieldFactory,
            final PropertyTypeReferenceFactory references) {

        this.fieldFactory = fieldFactory;
        this.references = references;
    }

    @Override
    public FieldSource fieldForProperty(
            final ConventionTypeElement enclosing, final Property property) {

        return this.fieldFactory.createMutable(this.references.apiFor(enclosing, property), property.name())
                .makePrivate()
                .makeFinal();
    }

}
