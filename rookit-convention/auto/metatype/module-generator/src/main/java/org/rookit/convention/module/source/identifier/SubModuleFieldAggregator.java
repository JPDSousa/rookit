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
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.identifier.ReferenceFieldAggregator;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.guice.auto.module.ModuleFieldFactory;

import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;

final class SubModuleFieldAggregator implements ReferenceFieldAggregator {

    private final Collection<TypeReferenceSource> subModules;
    private final ModuleFieldFactory fieldFactory;

    SubModuleFieldAggregator(
            final Iterable<TypeReferenceSource> subModules,
            final ModuleFieldFactory fieldFactory) {
        this.subModules = Lists.newArrayList(subModules);
        this.fieldFactory = fieldFactory;
    }

    @Override
    public boolean accept(final TypeReferenceSource item) {
        // even if the module already exists, return true because it's ok
        if (!this.subModules.contains(item)) {
            this.subModules.add(item);
        }
        return true;
    }

    @Override
    public ReferenceFieldAggregator reduce(final ReferenceFieldAggregator aggregator) {

        return new SubModuleFieldAggregator(
                ImmutableList.<TypeReferenceSource>builder()
                        .addAll(acceptedReferences())
                        .addAll(aggregator.acceptedReferences())
                        .build(),
                this.fieldFactory
        );
    }

    @Override
    public FieldSource result() {

        return this.fieldFactory.combine(this.subModules);
    }


    @Override
    public Collection<TypeReferenceSource> acceptedReferences() {

        return unmodifiableCollection(this.subModules);
    }

}
