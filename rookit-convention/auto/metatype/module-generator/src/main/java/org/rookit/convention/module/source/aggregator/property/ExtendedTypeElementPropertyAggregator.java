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

import com.google.common.collect.Maps;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregator;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.property.aggregator.ExtendedPropertyAggregator;
import org.rookit.convention.auto.metatype.property.aggregator.ExtendedPropertyAggregatorFactory;
import org.rookit.utils.primitive.VoidUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Name;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

final class ExtendedTypeElementPropertyAggregator implements ExtendedElementAggregator<Collection<MethodSource>> {

    private final Map<String, ExtendedPropertyAggregator<Collection<MethodSource>>> subAggregators;
    private final ExtendedPropertyAggregatorFactory<Collection<MethodSource>> aggregatorFactory;
    private final VoidUtils voidUtils;
    private final Messager messager;
    private final ExtendedElementVisitor<Name, Void> qualifiedNameVisitor;

    ExtendedTypeElementPropertyAggregator(
            final ExtendedPropertyAggregatorFactory<Collection<MethodSource>> aggregatorFactory,
            final VoidUtils voidUtils,
            final Messager messager,
            final ExtendedElementVisitor<Name, Void> qualifiedNameVisitor) {
        this.aggregatorFactory = aggregatorFactory;
        this.voidUtils = voidUtils;
        this.messager = messager;
        this.qualifiedNameVisitor = qualifiedNameVisitor;
        this.subAggregators = Maps.newHashMap();
    }

    @Override
    public boolean accept(final ExtendedElement item) {
        if (item instanceof ConventionTypeElement) {
            final Name qualifiedName = item.accept(this.qualifiedNameVisitor, this.voidUtils.returnVoid());
            this.subAggregators.computeIfAbsent(qualifiedName.toString(),
                    key -> this.aggregatorFactory.create((ConventionTypeElement) item));
            return true;
        }
        return false;
    }

    @Override
    public ExtendedElementAggregator<Collection<MethodSource>> reduce(
            final ExtendedElementAggregator<Collection<MethodSource>> aggregator) {

        return new ReducedExtendedTypeElementPropertyAggregator(this, aggregator, this.messager);
    }

    @Override
    public Collection<MethodSource> result() {
        return this.subAggregators.values().stream()
                .map(ExtendedPropertyAggregator<Collection<MethodSource>>::result)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
