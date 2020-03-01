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
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.pack.PackageReferenceWalker;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.container.TypeSourceContainer;
import org.rookit.auto.source.type.container.TypeSourceContainerExtendedElementAggregator;
import org.rookit.auto.source.type.container.TypeSourceContainerFactory;
import org.rookit.convention.auto.metatype.module.ModuleTypeSource;
import org.rookit.utils.optional.OptionalFactory;

import javax.annotation.processing.Filer;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

final class EntityExtendedTypeElementAggregator implements TypeSourceContainerExtendedElementAggregator<TypeSource> {

    private final PackageReferenceWalker step;
    private final ExtendedPackageElement packageId;
    private final IdentifierFactory identifierFactory;
    private final OptionalFactory optionalFactory;
    private final Identifier identifier;
    private final TypeSourceContainerFactory containerFactory;

    private final ModuleTypeSource source;

    // TODO too much dependencies :(
    EntityExtendedTypeElementAggregator(
            final PackageReferenceWalker step,
            final ExtendedPackageElement packageId,
            final IdentifierFactory identifierFactory,
            final ModuleTypeSource source,
            final OptionalFactory optionalFactory,
            final Identifier identifier,
            final TypeSourceContainerFactory containerFactory) {
        this.step = step;
        this.packageId = packageId;
        this.identifierFactory = identifierFactory;
        this.source = source;
        this.identifier = identifier;
        this.optionalFactory = optionalFactory;
        this.containerFactory = containerFactory;
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
                .map(this.identifierFactory::create)
                .mapToBoolean(moduleIdentifier -> {
                    this.source.addModule(moduleIdentifier);
                    return true;
                }).orElse(false);
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

    @Override
    public String toString() {
        return "EntityExtendedTypeElementAggregator{" +
                "step=" + this.step +
                ", packageId=" + this.packageId +
                ", identifierFactory=" + this.identifierFactory +
                ", optionalFactory=" + this.optionalFactory +
                ", identifier=" + this.identifier +
                ", containerFactory=" + this.containerFactory +
                ", source=" + this.source +
                "}";
    }

}
