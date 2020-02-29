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
package org.rookit.storage.update.filter.source;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.storage.guice.PartialUpdateFilter;
import org.rookit.storage.update.filter.UpdateFilterQuery;

import java.util.Collection;

//TODO this class is too similar to QueryPartialTypeSourceFactory
final class UpdateFilterPartialTypeSourceFactory implements SingleTypeSourceFactory {

    private final TypeSourceFactory typeSourceFactory;
    private final ConventionTypeElementFactory elementFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeVariableSource updateFilter;
    private final TypeReferenceSource updateFilterTypeName;

    @Inject
    private UpdateFilterPartialTypeSourceFactory(
            final TypeSourceFactory typeSourceFactory,
            final ConventionTypeElementFactory elementFactory,
            final TypeReferenceSourceFactory referenceFactory,
            @PartialUpdateFilter final TypeVariableSource updateFilter) {
        this.typeSourceFactory = typeSourceFactory;
        this.elementFactory = elementFactory;
        this.referenceFactory = referenceFactory;
        this.updateFilter = updateFilter;
        this.updateFilterTypeName = referenceFactory.fromClass(UpdateFilterQuery.class);
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {
        final ConventionTypeElement conventionElement = this.elementFactory.extend(element);

        return this.typeSourceFactory.createMutableInterface(identifier)
                .addInterfaces(parentNamesOf(conventionElement));
    }

    private Collection<TypeReferenceSource> parentNamesOf(final ConventionTypeElement baseElement) {
        final ImmutableSet.Builder<TypeReferenceSource> builder = ImmutableSet.<TypeReferenceSource>builder()
                .add(this.referenceFactory.resolveParameters(baseElement, this.updateFilter));
        if (baseElement.isTopLevel()) {
            builder.add(this.updateFilterTypeName);
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return "UpdateFilterPartialTypeSourceFactory{" +
                "typeSourceFactory=" + this.typeSourceFactory +
                ", elementFactory=" + this.elementFactory +
                ", referenceFactory=" + this.referenceFactory +
                ", updateFilter=" + this.updateFilter +
                ", updateFilterTypeName=" + this.updateFilterTypeName +
                "}";
    }

}
