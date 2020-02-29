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
package org.rookit.storage.update.source;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.storage.guice.PartialUpdateFilter;
import org.rookit.storage.guice.TopUpdate;
import org.rookit.storage.update.UpdateQuery;
import org.rookit.utils.primitive.VoidUtils;

// TODO semi copy-pasted from PropertyBasedTypeSourceFactory
final class PartialUpdateTypeSourceFactory implements SingleTypeSourceFactory {

    private final TypeSourceFactory typeSourceFactory;
    private final VoidUtils voidUtils;
    private final ExtendedElementVisitor<StreamEx<MethodSource>, Void> visitor;
    private final TypeReferenceSource updateQueryTypeName;
    private final ConventionTypeElementFactory elementFactory;

    @Inject
    private PartialUpdateTypeSourceFactory(
            final TypeSourceFactory typeSourceFactory,
            final VoidUtils voidUtils,
            @TopUpdate final ExtendedElementVisitor<StreamEx<MethodSource>, Void> visitor,
            @PartialUpdateFilter final TypeVariableSource updateFilter,
            final ConventionTypeElementFactory elementFactory,
            final TypeParameterSourceFactory typeParameterFactory) {
        this.typeSourceFactory = typeSourceFactory;
        this.voidUtils = voidUtils;
        this.visitor = visitor;
        this.updateQueryTypeName = typeParameterFactory.create(
                UpdateQuery.class,
                updateFilter
        );
        this.elementFactory = elementFactory;
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {
        final ConventionTypeElement conventionElement = this.elementFactory.extend(element);
        return this.typeSourceFactory.createMutableInterface(identifier)
                .addInterfaces(superInterfacesFor(conventionElement))
                .addMethods(element.accept(this.visitor, this.voidUtils.returnVoid()));
    }

    private Iterable<TypeReferenceSource> superInterfacesFor(final ConventionTypeElement baseElement) {
        final ImmutableSet.Builder<TypeReferenceSource> builder = ImmutableSet.builder();

        if (baseElement.isTopLevel()) {
            builder.add(this.updateQueryTypeName);
        }

        return builder.build();
    }

}
