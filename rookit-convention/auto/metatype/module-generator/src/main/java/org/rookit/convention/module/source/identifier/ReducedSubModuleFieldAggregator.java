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
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.identifier.IdentifierFieldAggregator;
import org.rookit.auto.source.identifier.IdentifierFieldAggregatorFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;

final class ReducedSubModuleFieldAggregator implements IdentifierFieldAggregator {

    private final FieldSourceFactory fieldFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final IdentifierFieldAggregatorFactory factory;
    private final String separator;
    private final IdentifierFieldAggregator delegate;
    private final IdentifierFieldAggregator left;
    private final IdentifierFieldAggregator right;

    ReducedSubModuleFieldAggregator(
            final FieldSourceFactory fieldFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final IdentifierFieldAggregatorFactory factory,
            final String separator, final IdentifierFieldAggregator left,
            final IdentifierFieldAggregator right) {
        this.fieldFactory = fieldFactory;
        this.referenceFactory = referenceFactory;
        this.factory = factory;
        this.separator = separator;
        this.left = left;
        this.right = right;
        this.delegate = factory.create();
    }

    @Override
    public boolean accept(final Identifier item) {
        return this.delegate.accept(item);
    }

    @Override
    public IdentifierFieldAggregator reduce(final IdentifierFieldAggregator aggregator) {
        return new ReducedSubModuleFieldAggregator(this.fieldFactory,
                                                   this.referenceFactory,
                                                   this.factory,
                                                   this.separator,
                                                   this, aggregator);
    }

    @Override
    public FieldSource result() {
        final FieldSource left = this.left.result();
        final FieldSource right = this.right.result();
        final FieldSource delegate = this.delegate.result();

        final TypeReferenceSource type = this.referenceFactory.fromClass(Module.class);
        return this.fieldFactory.createMutable(type, "MODULE")
                .initializer("$T.combine($L$L, $L$L, $L$L)",
                             ImmutableList.of(Modules.class,
                                              this.separator,
                                              delegate.initializer(),
                                              this.separator,
                                              left.initializer(),
                                              this.separator,
                                              right.initializer()));
    }

}
