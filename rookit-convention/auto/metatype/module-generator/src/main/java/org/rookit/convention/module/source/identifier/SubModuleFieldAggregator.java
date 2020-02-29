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
package org.rookit.convention.module.source.identifier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.identifier.IdentifierFieldAggregator;
import org.rookit.auto.source.identifier.IdentifierFieldAggregatorFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.lang.String.format;

final class SubModuleFieldAggregator implements IdentifierFieldAggregator {

    private final IdentifierFieldAggregatorFactory factory;
    private final Collection<Identifier> subModules;
    private final String separator;
    private final FieldSourceFactory fieldFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final ArbitraryCodeSourceFactory codeSourceFactory;

    SubModuleFieldAggregator(
            final IdentifierFieldAggregatorFactory factory,
            final Iterable<Identifier> subModules,
            final String separator,
            final FieldSourceFactory fieldFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final ArbitraryCodeSourceFactory codeSourceFactory) {
        this.factory = factory;
        // this must be a list, because we are treating the FIRST item differently
        // TODO should we change the design here to consider the first field separately?
        this.subModules = Lists.newArrayList(subModules);
        this.separator = separator;
        this.fieldFactory = fieldFactory;
        this.referenceFactory = referenceFactory;
        this.codeSourceFactory = codeSourceFactory;
    }

    @Override
    public boolean accept(final Identifier item) {
        // even if the module already exists, return true because it's ok
        if (!this.subModules.contains(item)) {
            this.subModules.add(item);
        }
        return true;
    }

    @Override
    public IdentifierFieldAggregator reduce(final IdentifierFieldAggregator aggregator) {

        return new ReducedSubModuleFieldAggregator(this.fieldFactory,
                                                   this.referenceFactory,
                                                   this.factory,
                                                   this.separator,
                                                   this,
                                                   aggregator
        );
    }

    @Override
    public FieldSource result() {
        final TypeReferenceSource type = this.referenceFactory.fromClass(Module.class);
        return this.fieldFactory.createMutable(type, "MODULE")
                .makePrivate()
                .makeStatic()
                .makeFinal()
                .initializer(initializer());
    }

    private ArbitraryCodeSource initializer() {
        if (this.subModules.isEmpty()) {
            return this.codeSourceFactory.createFromFormat("$T.combine()", ImmutableList.of(Modules.class));
        }
        if (this.subModules.size() == 1) {
            final Identifier onlyElement = getOnlyElement(this.subModules);
            final TypeReferenceSource referenceName = this.referenceFactory.fromIdentifier(onlyElement);
            return this.codeSourceFactory.createFromFormat("$T.combine(new $T())",
                                                           ImmutableList.of(Modules.class, referenceName));
        }

        final String initializer = format("$T.combine(new $T(),%s%s)", this.separator, combineBody());
        final List<TypeReferenceSource> args = StreamEx.of(this.subModules)
                .map(this.referenceFactory::fromIdentifier)
                .toImmutableList();

        return this.codeSourceFactory.createFromFormat(
                initializer,
                ImmutableList.builder()
                        .addAll(args)
                        .add(this.referenceFactory.fromClass(Modules.class))
                        .build()
        );
    }

    private String combineBody() {
        final String[] combines = new String[this.subModules.size() - 1];
        Arrays.fill(combines, "$T.getModule()");
        return String.join("," + this.separator, combines);
    }

}
