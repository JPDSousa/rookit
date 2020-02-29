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
package org.rookit.convention.module.source.aggregator.multi;

import com.google.common.collect.ImmutableList;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.aggregator.ExtendedPackageElementAggregatorFactory;
import org.rookit.auto.javax.pack.PackageReferenceWalker;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.container.TypeSourceContainer;
import org.rookit.auto.source.type.container.TypeSourceContainerExtendedElementAggregator;
import org.rookit.auto.source.type.container.TypeSourceContainerFactory;

import javax.annotation.processing.Filer;
import java.util.concurrent.CompletableFuture;

final class MultiEntityModuleExtendedTypeElementAggregator
        implements TypeSourceContainerExtendedElementAggregator<TypeSource> {

    // TODO this doesn't make much sense, given that the module is a single type source, but we're using a container.
    private final TypeSourceContainerExtendedElementAggregator<TypeSource> moduleAggregator;
    private final TypeSourceContainerExtendedElementAggregator<TypeSource> delegate;
    private final TypeSourceContainerFactory containerFactory;

    MultiEntityModuleExtendedTypeElementAggregator(
            final PackageReferenceWalker step,
            final TypeSourceContainerFactory containerFactory,
            final ExtendedPackageElementAggregatorFactory<TypeSourceContainer<TypeSource>,
                    TypeSourceContainerExtendedElementAggregator<TypeSource>> aggregatorFactory) {
        this.moduleAggregator = aggregatorFactory.create(step.materialize());
        this.containerFactory = containerFactory;
        // TODO avoid explicit initializations
        //only subpackages are to be processed by this aggregator
        this.delegate = new DispatchEntityExtendedTypeElementAggregator(step, containerFactory, aggregatorFactory);
    }

    @Override
    public CompletableFuture<Void> writeTo(final Filer filer) {
        return CompletableFuture.allOf(this.delegate.writeTo(filer), this.moduleAggregator.writeTo(filer));
    }

    @Override
    public boolean accept(final ExtendedElement item) {
        final boolean acceptedByModule = this.moduleAggregator.accept(item);
        final boolean acceptedByDelegate = this.delegate.accept(item);

        return acceptedByDelegate || acceptedByModule;
    }

    @Override
    public TypeSourceContainerExtendedElementAggregator<TypeSource> reduce(
            final TypeSourceContainerExtendedElementAggregator<TypeSource> aggregator) {

        // TODO not really confident about this
        return this.delegate.reduce(aggregator);
    }

    @Override
    public TypeSourceContainer<TypeSource> result() {

        return this.containerFactory.createFromContainers(
                ImmutableList.of(this.delegate, this.moduleAggregator)
        );
    }

}
