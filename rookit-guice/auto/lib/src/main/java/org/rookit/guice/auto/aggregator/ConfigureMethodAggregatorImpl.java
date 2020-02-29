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
package org.rookit.guice.auto.aggregator;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.guice.auto.bind.BindingStatement;
import org.rookit.guice.auto.bind.BindingStatementFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

final class ConfigureMethodAggregatorImpl implements ConfigureMethodAggregator {

    private final Set<BindingStatement> configureBindings;
    private final TypeReferenceSourceFactory apiNamingFactory;
    private final TypeReferenceSourceFactory implNamingFactory;
    private final MethodSourceFactory methodFactory;
    private final BindingStatementFactory bindingFactory;

    ConfigureMethodAggregatorImpl(
            final TypeReferenceSourceFactory apiNamingFactory,
            final TypeReferenceSourceFactory implNamingFactory,
            final MethodSourceFactory methodFactory,
            final BindingStatementFactory bindingFactory,
            final Iterable<BindingStatement> configureBindings) {
        this.apiNamingFactory = apiNamingFactory;
        this.implNamingFactory = implNamingFactory;
        this.methodFactory = methodFactory;
        this.bindingFactory = bindingFactory;
        this.configureBindings = Sets.newHashSet(configureBindings);
    }

    @Override
    public boolean accept(final ExtendedElement element) {
        if (element instanceof ExtendedTypeElement) {
            this.configureBindings.add(createBinding((ExtendedTypeElement) element));
            return true;
        }
        return false;
    }

    @Override
    public ConfigureMethodAggregator reduce(final ConfigureMethodAggregator aggregator) {

        return new ConfigureMethodAggregatorImpl(
                this.apiNamingFactory,
                this.implNamingFactory,
                this.methodFactory,
                this.bindingFactory,
                ImmutableSet.<BindingStatement>builder()
                        .addAll(bindStatements())
                        .addAll(aggregator.bindStatements())
                        .build()
        );
    }


    @Override
    public MethodSource result() {
        final MutableMethodSource configureBuilder = this.methodFactory.createMutableMethod("configure");

        this.configureBindings.forEach(configureBuilder::addStatement);

        return configureBuilder;
    }

    private BindingStatement createBinding(final ExtendedTypeElement element) {

        return this.bindingFactory.bindSingleton(this.apiNamingFactory.create(element))
                .to(this.implNamingFactory.create(element));
    }

    @Override
    public Collection<BindingStatement> bindStatements() {
        return Collections.unmodifiableCollection(this.configureBindings);
    }

}
