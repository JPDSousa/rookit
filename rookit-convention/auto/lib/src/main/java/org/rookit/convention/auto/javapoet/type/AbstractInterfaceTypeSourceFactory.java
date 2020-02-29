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
package org.rookit.convention.auto.javapoet.type;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.utils.primitive.VoidUtils;

public abstract class AbstractInterfaceTypeSourceFactory implements SingleTypeSourceFactory {

    private final TypeVariableSourceFactory variableFactory;
    private final ConventionTypeElementFactory elementFactory;
    private final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor;
    private final TypeSourceFactory typeFactory;
    private final VoidUtils voidUtils;

    AbstractInterfaceTypeSourceFactory(
            final TypeVariableSourceFactory variableFactory,
            final ConventionTypeElementFactory elementFactory,
            final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor,
            final TypeSourceFactory typeFactory,
            final VoidUtils voidUtils) {
        this.variableFactory = variableFactory;
        this.elementFactory = elementFactory;
        this.methodVisitor = methodVisitor;
        this.typeFactory = typeFactory;
        this.voidUtils = voidUtils;
    }

    @Override
    public TypeSource create(final Identifier identifier,
                             final ExtendedTypeElement element) {
        final ConventionTypeElement conventionElement = this.elementFactory.extend(element);

        return this.typeFactory.createMutableInterface(identifier)
                .addTypeVariables(this.variableFactory.createTypeVariables(element))
                .addMethods(element.accept(this.methodVisitor, this.voidUtils.returnVoid()))
                .addInterfaces(superInterfacesFor(conventionElement));
    }

    protected abstract Iterable<MethodSource> methodsFor(ExtendedTypeElement element);

    private StreamEx<TypeReferenceSource> superTypesFor(final ExtendedTypeElement parent) {
        return StreamEx.empty();
//        return StreamEx.of(this.variableFactory.resolveParameters(parent));
    }

    private Iterable<TypeReferenceSource> superInterfacesFor(final ConventionTypeElement baseElement) {
        return baseElement.conventionInterfaces()
                .flatMap(this::superTypesFor)
                .toImmutableList();
    }

}
