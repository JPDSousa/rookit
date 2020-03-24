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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.WildcardVariableSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.MetaTypePropertyFetcherFactory;
import org.rookit.convention.property.PropertyModel;

import java.util.Collection;

final class PropertiesMethodFactory implements MetaTypePropertiesMethodFactory {

    private final MethodSource propertiesMethod;

    @Inject
    private PropertiesMethodFactory(
            final WildcardVariableSourceFactory wildcardFactory,
            final MethodSourceFactory methodFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            @From(Collection.class) final TypeReferenceSource collection,
            final MetaTypePropertyFetcherFactory propertyFetcher) {

        final TypeReferenceSource propertyModel = typeParameterFactory.create(
                PropertyModel.class, wildcardFactory.newWildcard());
        final TypeReferenceSource returnType = typeParameterFactory.create(collection, propertyModel);

        final CharSequence propertyMapName = propertyFetcher.fields()
                .propertyMap()
                .name();

        this.propertiesMethod = methodFactory.createMutableMethod("properties")
                .makePublic()
                .override()
                .withReturnType(returnType)
                .addStatement("return $L.values()", ImmutableList.of(propertyMapName));
    }

    @Override
    public MethodSource implFor(final ConventionTypeElement type) {

        return this.propertiesMethod;
    }

}
