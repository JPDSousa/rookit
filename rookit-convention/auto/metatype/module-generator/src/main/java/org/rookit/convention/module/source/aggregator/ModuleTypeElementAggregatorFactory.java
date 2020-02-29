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

import com.google.inject.Inject;
import org.rookit.auto.javax.aggregator.ExtendedPackageElementAggregatorFactory;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.pack.PackageReferenceWalker;
import org.rookit.auto.javax.pack.PackageReferenceWalkerFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.container.TypeSourceContainer;
import org.rookit.auto.source.type.container.TypeSourceContainerExtendedElementAggregator;
import org.rookit.auto.source.type.container.TypeSourceContainerFactory;
import org.rookit.convention.auto.module.ModuleTypeSource;
import org.rookit.convention.auto.module.ModuleTypeSourceFactory;
import org.rookit.utils.optional.OptionalFactory;

final class ModuleTypeElementAggregatorFactory implements ExtendedPackageElementAggregatorFactory<
        TypeSourceContainer<TypeSource>, TypeSourceContainerExtendedElementAggregator<TypeSource>> {

    private final ModuleTypeSourceFactory sourceFactory;
    private final OptionalFactory optionalFactory;
    private final IdentifierFactory identifierFactory;
    private final PackageReferenceWalkerFactory walkerFactory;
    private final TypeSourceContainerFactory containerFactory;

    @Inject
    private ModuleTypeElementAggregatorFactory(
            final ModuleTypeSourceFactory sourceFactory,
            final OptionalFactory optionalFactory,
            final IdentifierFactory identifierFactory,
            final PackageReferenceWalkerFactory walkerFactory,
            final TypeSourceContainerFactory containerFactory) {
        this.sourceFactory = sourceFactory;
        this.optionalFactory = optionalFactory;
        this.identifierFactory = identifierFactory;
        this.walkerFactory = walkerFactory;
        this.containerFactory = containerFactory;
    }

    @Override
    public TypeSourceContainerExtendedElementAggregator<TypeSource> create(
            final ExtendedPackageElement packageElement) {

        final Identifier identifier = this.identifierFactory.create(packageElement);
        final ModuleTypeSource typeSource = this.sourceFactory.createClass(identifier);
        final PackageReferenceWalker step = this.walkerFactory.create(packageElement);

        return new EntityExtendedTypeElementAggregator(
                step,
                packageElement,
                this.identifierFactory,
                typeSource,
                this.optionalFactory,
                identifier,
                this.containerFactory
        );
    }

}
