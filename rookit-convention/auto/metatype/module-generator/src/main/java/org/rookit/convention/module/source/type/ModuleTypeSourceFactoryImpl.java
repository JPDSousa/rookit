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
package org.rookit.convention.module.source.type;

import com.google.inject.Inject;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregatorFactory;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregator;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.source.identifier.IdentifierFieldAggregator;
import org.rookit.auto.source.identifier.IdentifierFieldAggregatorFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.convention.auto.module.ModuleTypeSource;
import org.rookit.convention.auto.module.ModuleTypeSourceFactory;

import java.util.Collection;

final class ModuleTypeSourceFactoryImpl implements ModuleTypeSourceFactory {

    private final TypeSourceFactory delegate;
    private final ExtendedElementAggregatorFactory<MethodSource
            > methodAggregatorFactory;
    private final IdentifierFieldAggregatorFactory moduleAggregatorFactory;
    private final ExtendedElementAggregatorFactory<Collection<MethodSource>
            > propertyAggregatorFactory;

    @Inject
    private ModuleTypeSourceFactoryImpl(
            final TypeSourceFactory delegate,
            final ExtendedElementAggregatorFactory<MethodSource
                    > methodFactory,
            final IdentifierFieldAggregatorFactory moduleFactory,
            final ExtendedElementAggregatorFactory<Collection<MethodSource>
                    > propAggFactory) {
        this.delegate = delegate;
        this.methodAggregatorFactory = methodFactory;
        this.moduleAggregatorFactory = moduleFactory;
        this.propertyAggregatorFactory = propAggFactory;
    }

    @Override
    public ModuleTypeSource createClass(final Identifier identifier) {
        final MutableTypeSource delegate = this.delegate.createMutableClass(identifier);
        final ExtendedElementAggregator<MethodSource> methodAggregator = this.methodAggregatorFactory.create();
        final IdentifierFieldAggregator moduleAggregator = this.moduleAggregatorFactory.create(identifier);
        final ExtendedElementAggregator<Collection<MethodSource>> propertyAggregator
                = this.propertyAggregatorFactory.create();
        return new ModuleTypeSourceImpl(delegate, methodAggregator, moduleAggregator, propertyAggregator);
    }

}
