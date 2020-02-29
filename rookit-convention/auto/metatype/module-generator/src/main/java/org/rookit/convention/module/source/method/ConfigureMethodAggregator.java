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
package org.rookit.convention.module.source.method;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregatorFactory;
import org.rookit.auto.javax.aggregator.ExtendedTypeElementAggregator;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.utils.primitive.VoidUtils;

import java.util.List;
import java.util.Set;

final class ConfigureMethodAggregator implements ExtendedTypeElementAggregator<MethodSource> {

    private final Set<ExtendedElement> configureBindings;
    private final NamingFactory apiNamingFactory;
    private final NamingFactory implNamingFactory;
    private final MethodSourceFactory methodFactory;
    private final ExtendedElementAggregatorFactory<MethodSource, ExtendedTypeElementAggregator<MethodSource>> factory;
    private final VoidUtils voidUtils;
    private final ArbitraryCodeSourceFactory codeFactory;

    ConfigureMethodAggregator(
            final NamingFactory apiNamingFactory,
            final NamingFactory implNamingFactory,
            final MethodSourceFactory methodFactory,
            final ExtendedElementAggregatorFactory<MethodSource,
                    ExtendedTypeElementAggregator<MethodSource>> factory,
            final VoidUtils voidUtils,
            final ArbitraryCodeSourceFactory codeFactory) {
        this.apiNamingFactory = apiNamingFactory;
        this.implNamingFactory = implNamingFactory;
        this.methodFactory = methodFactory;
        this.factory = factory;
        this.voidUtils = voidUtils;
        this.codeFactory = codeFactory;
        this.configureBindings = Sets.newHashSet();
    }

    @Override
    public boolean accept(final ExtendedElement element) {
        this.configureBindings.add(element);
        return true;
    }

    @Override
    public ExtendedTypeElementAggregator<MethodSource> reduce(
            final ExtendedTypeElementAggregator<MethodSource> aggregator) {

        return new ReducedConfigureMethodAggregator(this.factory, result(), aggregator.result(), this.voidUtils);
    }

    @Override
    public MethodSource result() {
        final MutableMethodSource configureBuilder = this.methodFactory.createMutableMethod("configure");

        StreamEx.of(this.configureBindings)
                .select(ConventionTypeElement.class)
                .filter(ConventionTypeElement::isConvention)
                .map(this::createBinding)
                .forEach(configureBuilder::addStatement);

        return configureBuilder;
    }

    private ArbitraryCodeSource createBinding(final ExtendedElement element) {

        final List<Object> params = ImmutableList.of(
                this.apiNamingFactory.type(element),
                this.implNamingFactory.type(element),
                Singleton.class
        );

        final String bindPreStatement = "bind($L.class).to($L.class).in($T.class)";

        return this.codeFactory.createFromFormat(bindPreStatement, params);
    }

}
