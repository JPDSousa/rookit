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
package org.rookit.convention.api.source.type;

import com.google.common.collect.ImmutableList;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.MetaType;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.convention.property.ImmutablePropertyModel;
import org.rookit.utils.primitive.VoidUtils;

import java.util.Collection;

import static com.google.common.collect.ImmutableList.toImmutableList;

// TODO break me down into pieces
class BaseTypeSourceFactory implements SingleTypeSourceFactory {

    private final TypeSourceFactory typeFactory;
    private final TypeVariableSourceFactory variableFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final TypeReferenceSource typeModel;
    private final TypeVariableSource typeVariableName;
    private final TypeReferenceSource propertyModel;
    private final VoidUtils voidUtils;
    private final ExtendedElementVisitor<StreamEx<MethodSource>, Void> visitor;
    private final ConventionTypeElementFactory elementFactory;

    BaseTypeSourceFactory(
            final TypeSourceFactory typeFactory,
            final TypeVariableSourceFactory variableFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final VoidUtils voidUtils,
            final ExtendedElementVisitor<StreamEx<MethodSource>, Void> visitor,
            final TypeVariableSource typeVariableName,
            final ConventionTypeElementFactory elementFactory) {
        this.typeFactory = typeFactory;
        this.variableFactory = variableFactory;
        this.referenceFactory = referenceFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.voidUtils = voidUtils;
        this.visitor = visitor;
        this.typeVariableName = typeVariableName;
        this.elementFactory = elementFactory;
        this.typeModel = referenceFactory.fromClass(MetaType.class);
        this.propertyModel = referenceFactory.fromClass(ImmutablePropertyModel.class);
    }

    @Override
    public TypeSource create(final Identifier identifier,
                             final ExtendedTypeElement element) {

        final ConventionTypeElement conventionElement = this.elementFactory.extend(element);
        return this.typeFactory.createMutableInterface(identifier)
                .makePublic()
                .addTypeVariables(this.variableFactory.createTypeVariables(element))
                .addInterfaces(superInterfaces(conventionElement))
                .addMethods(element.accept(this.visitor, this.voidUtils.returnVoid()));
    }

    private Iterable<TypeReferenceSource> superInterfaces(final ConventionTypeElement element) {

        final Collection<TypeReferenceSource> superTypes = element.conventionInterfaces()
                .map(this.referenceFactory::resolveParameters)
                .collect(toImmutableList());

        final TypeParameterSource typeModel = getModelTypeName(element);

        return ImmutableList.<TypeReferenceSource>builder()
                .add(typeModel)
                .addAll(superTypes)
                .build();
    }

    private TypeParameterSource getModelTypeName(final ConventionTypeElement element) {
        if (element.isEntity() || element.isPartialEntity()) {
            return this.typeParameterFactory.create(this.typeModel, this.typeVariableName);
        }

        final TypeReferenceSource elementReference = this.referenceFactory.create(element);
        return this.typeParameterFactory.create(this.propertyModel, this.typeVariableName, elementReference);
    }

}
