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
import com.google.inject.Provider;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactories;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.metatype.config.MetaTypeApiConfig;

final class MetaTypeAPITypeReferenceFactoryProvider implements Provider<TypeReferenceSourceFactory> {

    private final TypeReferenceSourceFactory originalFactory;
    private final TypeReferenceSourceFactories factories;
    private final MetaTypeApiConfig config;

    @Inject
    private MetaTypeAPITypeReferenceFactoryProvider(
            final TypeReferenceSourceFactory originalFactory,
            final TypeReferenceSourceFactories factories,
            final MetaTypeApiConfig config) {
        this.originalFactory = originalFactory;
        this.factories = factories;
        this.config = config;
    }

    @Override
    public TypeReferenceSourceFactory get() {
        return this.factories.proxy(
                this.originalFactory,
                this.config.basePackage(),
                this.config.entityTemplate());
    }
}
