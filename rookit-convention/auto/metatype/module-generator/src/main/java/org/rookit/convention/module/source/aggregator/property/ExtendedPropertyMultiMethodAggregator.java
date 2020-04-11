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

import com.google.common.collect.Lists;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.source.prototype.PropertyMethodPrototypes;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.convention.auto.property.aggregator.ExtendedPropertyAggregator;
import org.rookit.guice.auto.bind.ProviderMethodBinding;

import javax.annotation.processing.Messager;
import java.util.Collection;

final class ExtendedPropertyMultiMethodAggregator implements ExtendedPropertyAggregator<Collection<MethodSource>> {

    private final ConventionTypeElement element;
    private final Collection<Property> properties;
    private final PropertyFactory propertyFactory;
    private final PropertyMethodPrototypes propertyPrototype;
    private final Messager messager;

    ExtendedPropertyMultiMethodAggregator(
            final ConventionTypeElement element,
            final Iterable<Property> properties,
            final PropertyFactory propertyFactory,
            final PropertyMethodPrototypes propertyPrototype,
            final Messager messager) {

        this.element = element;
        this.properties = Lists.newArrayList(properties);
        this.propertyFactory = propertyFactory;
        this.propertyPrototype = propertyPrototype;
        this.messager = messager;
    }

    @Override
    public boolean accept(final Property item) {
        this.properties.add(item);
        return true;
    }

    @Override
    public ExtendedPropertyAggregator<Collection<MethodSource>> reduce(
            final ExtendedPropertyAggregator<Collection<MethodSource>> aggregator) {
        return new ReducedExtendedPropertyMultiMethodAggregator(this.messager, this, aggregator);
    }

    @Override
    public Collection<MethodSource> result() {
        return StreamEx.of(this.properties)
                .map(property -> this.propertyPrototype.bindingFor(this.element, property))
                .map(ProviderMethodBinding::asMethod)
                .toImmutableList();
    }

}
