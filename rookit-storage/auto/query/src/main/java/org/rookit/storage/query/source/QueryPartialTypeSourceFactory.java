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
package org.rookit.storage.query.source;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.storage.guice.Query;

import java.util.Collection;

//TODO this class is too similar to UpdateFilterPartialTypeSourceFactory
final class QueryPartialTypeSourceFactory implements SingleTypeSourceFactory {

    private final TypeSourceFactory typeSourceFactory;
    private final MethodSourceFactory methodSourceFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeReferenceSource queryTypeName;

    private final NamingFactory namingFactory;
    private final ConventionTypeElementFactory elementFactory;

    @Inject
    private QueryPartialTypeSourceFactory(
            final TypeSourceFactory typeSourceFactory,
            final MethodSourceFactory methodSourceFactory,
            @Query final TypeReferenceSourceFactory referenceFactory,
            @Query final TypeReferenceSource queryTypeName,
            @Query final NamingFactory namingFactory,
            final ConventionTypeElementFactory elementFactory) {
        this.typeSourceFactory = typeSourceFactory;
        this.methodSourceFactory = methodSourceFactory;
        this.referenceFactory = referenceFactory;
        this.queryTypeName = queryTypeName;
        this.namingFactory = namingFactory;
        this.elementFactory = elementFactory;
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {

        final ConventionTypeElement conventionElement = this.elementFactory.extend(element);

        return this.typeSourceFactory.createMutableInterface(identifier)
                .addInterfaces(parentNamesOf(conventionElement))
                .addMethods(methodsFor(conventionElement));
    }

    private Collection<MethodSource> methodsFor(final ConventionTypeElement element) {
        return element.upstreamEntity()
                .map(this::adapterMethod)
                .toImmutableSet();
    }

    private MethodSource adapterMethod(final ExtendedTypeElement element) {

        final TypeReferenceSource returnType = this.referenceFactory.create(element);
        return this.methodSourceFactory.createMutableMethod("to" + this.namingFactory.type(element))
                .makePublic()
                .makeAbstract()
                .withReturnType(returnType);
    }

    private Collection<TypeReferenceSource> parentNamesOf(final ConventionTypeElement baseElement) {
        final ImmutableSet.Builder<TypeReferenceSource> builder = ImmutableSet.<TypeReferenceSource>builder()
                .add(this.referenceFactory.resolveParameters(baseElement));

        if (baseElement.isTopLevel()) {
            builder.add(this.queryTypeName);
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return "QueryPartialTypeSourceFactory{" +
                "typeSourceFactory=" + this.typeSourceFactory +
                ", methodSourceFactory=" + this.methodSourceFactory +
                ", referenceFactory=" + this.referenceFactory +
                ", queryTypeName=" + this.queryTypeName +
                ", namingFactory=" + this.namingFactory +
                ", elementFactory=" + this.elementFactory +
                "}";
    }

}
