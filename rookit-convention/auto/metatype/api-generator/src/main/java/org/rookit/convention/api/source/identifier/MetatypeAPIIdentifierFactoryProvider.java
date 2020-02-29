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
package org.rookit.convention.api.source.identifier;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rookit.auto.javax.naming.IdentifierFactories;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;

final class MetatypeAPIIdentifierFactoryProvider implements Provider<IdentifierFactory> {

    private final IdentifierFactories factories;
    private final NamingFactory namingFactory;

    @Inject
    private MetatypeAPIIdentifierFactoryProvider(final IdentifierFactories factories,
                                                 @MetaTypeAPI final NamingFactory namingFactory) {
        this.factories = factories;
        this.namingFactory = namingFactory;
    }

    @Override
    public IdentifierFactory get() {
        return this.factories.create(this.namingFactory);
    }

    @Override
    public String toString() {
        return "MetatypeAPIIdentifierFactoryProvider{" +
                "factories=" + this.factories +
                ", namingFactory=" + this.namingFactory +
                "}";
    }
}
