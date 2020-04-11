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
package org.rookit.convention.auto.metatype.source.type.reference;

import com.google.inject.Inject;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.util.Collection;

final class PropertiesReferenceFactoryImpl implements PropertiesReferenceFactory {

    private final TypeParameterSourceFactory typeParameterFactory;
    private final PropertyTypeReferenceFactory references;
    private final TypeReferenceSource collection;

    @Inject
    private PropertiesReferenceFactoryImpl(
            final TypeParameterSourceFactory typeParameterFactory,
            final PropertyTypeReferenceFactory references,
            @From(Collection.class) final TypeReferenceSource collection) {

        this.typeParameterFactory = typeParameterFactory;
        this.references = references;
        this.collection = collection;
    }

    @Override
    public TypeReferenceSource methodReferenceFor(final ExtendedTypeMirror mirror) {

        final TypeReferenceSource propertyModel = this.references.genericFor(mirror);

        return this.typeParameterFactory.create(this.collection, propertyModel);
    }

}
