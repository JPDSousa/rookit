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
package org.rookit.convention.module.source.aggregator.property;

import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.prototype.PropertyMethodPrototypes;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.convention.auto.property.aggregator.ExtendedPropertyAggregator;
import org.rookit.convention.auto.property.aggregator.ExtendedPropertyAggregatorFactory;

import javax.annotation.processing.Messager;
import java.util.Collection;

final class ExtendedPropertyMultiAggregatorFactory implements ExtendedPropertyAggregatorFactory<
        Collection<MethodSource>> {

    private final Messager messager;
    private final PropertyFactory propertyFactory;
    private final PropertyMethodPrototypes propertyPrototype;

    @Inject
    private ExtendedPropertyMultiAggregatorFactory(
            final Messager messager,
            final PropertyFactory propertyFactory,
            final PropertyMethodPrototypes propertyPrototype) {
        this.messager = messager;
        this.propertyFactory = propertyFactory;
        this.propertyPrototype = propertyPrototype;
    }

    @Override
    public ExtendedPropertyAggregator<Collection<MethodSource>> create(final ConventionTypeElement element) {
        return new ExtendedPropertyMultiMethodAggregator(
                element,
                element.properties(),
                this.propertyFactory,
                this.propertyPrototype,
                this.messager
        );
    }

}
