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
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.MetaTypePropertyFetcherFactory;
import org.rookit.convention.auto.metatype.source.type.reference.PropertiesReferenceFactory;
import org.rookit.convention.guice.MetaTypeProperties;

final class PropertiesMethodFactory implements MetaTypePropertiesMethodFactory {

    private final MethodSourceFactory methodFactory;

    private final MetaTypePropertyFetcherFactory propertyFetcher;
    private final PropertiesReferenceFactory references;
    private final ExtendedExecutableElement properties;

    @Inject
    private PropertiesMethodFactory(
            @MetaTypeProperties final ExtendedExecutableElement properties,
            final MethodSourceFactory methodFactory,
            final MetaTypePropertyFetcherFactory propertyFetcher,
            final PropertiesReferenceFactory references) {

        this.properties = properties;
        this.methodFactory = methodFactory;
        this.propertyFetcher = propertyFetcher;
        this.references = references;
    }

    @Override
    public MethodSource implFor(final ConventionTypeElement type) {

        final CharSequence propertyMapName = this.propertyFetcher.fieldsFor(type)
                .propertyMap()
                .name();

        return this.methodFactory.createMutableOverride(this.properties)
                .withReturnType(this.references.methodReferenceFor(type))
                .addStatement("return $L.values()", ImmutableList.of(propertyMapName));
    }

    @Override
    public MethodSource delegateMethodFor(
            final ExtendedTypeMirror type,
            final FieldSource delegate) {

        return this.methodFactory.createMutableOverride(this.properties)
                .withReturnType(this.references.methodReferenceFor(type))
                .addStatement("return $L.$L()",
                              ImmutableList.of(delegate.name(), this.properties.getSimpleName()));
    }

}
