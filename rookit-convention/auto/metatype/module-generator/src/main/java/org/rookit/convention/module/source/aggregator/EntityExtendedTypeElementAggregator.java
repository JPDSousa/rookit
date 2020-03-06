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
package org.rookit.convention.module.source.aggregator;

import com.google.common.collect.ImmutableList;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.pack.PackageReferenceWalker;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.container.TypeSourceContainer;
import org.rookit.auto.source.type.container.TypeSourceContainerExtendedElementAggregator;
import org.rookit.auto.source.type.container.TypeSourceContainerFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.guice.auto.module.ModuleTypeSource;

import javax.annotation.processing.Filer;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

final class EntityExtendedTypeElementAggregator implements TypeSourceContainerExtendedElementAggregator<TypeSource> {

    private final PackageReferenceWalker step;
    private final ExtendedPackageElement packageId;
    private final TypeSourceContainerFactory containerFactory;
    private final TypeReferenceSourceFactory referenceFactory;

    private final ModuleTypeSource source;

    // TODO too much dependencies :(
    EntityExtendedTypeElementAggregator(
            final PackageReferenceWalker step,
            final ExtendedPackageElement packageId,
            final ModuleTypeSource source,
            final TypeSourceContainerFactory containerFactory,
            final TypeReferenceSourceFactory referenceFactory) {
        this.step = step;
        this.packageId = packageId;
        this.source = source;
        this.containerFactory = containerFactory;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public CompletableFuture<Void> writeTo(final Filer filer) {
        return this.source.writeTo(filer);
    }

    @Override
    public boolean accept(final ExtendedElement item) {
        final ExtendedPackageElement packageElement = item.packageInfo();

        if (Objects.equals(this.packageId, packageElement)) {
            this.source.addBinding(item);
            return true;
        }
        return this.step.nextStepFrom(packageElement)
                .map(PackageReferenceWalker::materialize)
                .map(this::createModuleReference)
                .mapToBoolean(this.source::addModule)
                .orElse(false);
    }

    private TypeReferenceSource createModuleReference(final ExtendedPackageElement packageReference) {

        return this.referenceFactory.fromSplitPackageAndName(
                packageReference,
                packageReference.getSimpleName()
        );
    }

    @Override
    public TypeSourceContainerExtendedElementAggregator<TypeSource> reduce(
            final TypeSourceContainerExtendedElementAggregator<TypeSource> aggregator) {

        // TODO avoid direct initializations
        return new ReducedEntityExtendedElementAggregator(this, aggregator, this.containerFactory);
    }

    @Override
    public TypeSourceContainer<TypeSource> result() {
        return this.containerFactory.createFromTypeSource(ImmutableList.of(this.source));
    }

}
