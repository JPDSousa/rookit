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
package org.rookit.convention.meta.source.metatype;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.source.type.reference.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.guice.MetaType;

final class MetaTypeFieldVisitor implements ConventionTypeElementVisitor<StreamEx<FieldSource>, Void>,
        StreamExtendedElementVisitor<FieldSource, Void> {

    private final FieldSourceFactory fieldFactory;
    private final TypeParameterSourceFactory parameterFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final PropertyTypeReferenceSourceFactory propertyReferenceFactory;
    private final TypeReferenceSource metaTypeName;
    private final TypeVariableSource variableSource;

    @Inject
    private MetaTypeFieldVisitor(
            final FieldSourceFactory fieldFactory,
            final TypeParameterSourceFactory parameterFactory,
            final TypeReferenceSourceFactory referenceFactory,
            @MetaType final PropertyTypeReferenceSourceFactory propRefFactory,
            @MetaType final TypeVariableSource variableSource) {
        this.fieldFactory = fieldFactory;
        this.parameterFactory = parameterFactory;
        this.referenceFactory = referenceFactory;
        this.propertyReferenceFactory = propRefFactory;
        this.variableSource = variableSource;
        this.metaTypeName = referenceFactory.fromClass(org.rookit.convention.MetaType.class);
    }

    @Override
    public StreamEx<FieldSource> visitConventionType(final ConventionTypeElement element, final Void parameter) {

        final TypeReferenceSource param = element.isPartialEntity()
                ? this.variableSource
                : this.referenceFactory.create(element);

        final TypeParameterSource metatypeType = this.parameterFactory.create(this.metaTypeName, param);

        // TODO ERROR here, but where ????
        return StreamEx.<FieldSource>of(this.fieldFactory.createMutable(metatypeType, "metaType"))
                .append(createPropertyFields(element));
    }

    private StreamEx<FieldSource> createPropertyFields(final ConventionTypeElement owner) {
        return StreamEx.of(owner.properties())
                .map(property -> createProperty(owner, property));
    }

    private FieldSource createProperty(final ConventionTypeElement owner, final Property property) {
        final TypeReferenceSource type = this.propertyReferenceFactory.create(owner, property);
        return this.fieldFactory.createMutable(type, property.name());
    }

    @Override
    public String toString() {
        return "MetaTypeFieldVisitor{" +
                "fieldFactory=" + this.fieldFactory +
                ", parameterFactory=" + this.parameterFactory +
                ", referenceFactory=" + this.referenceFactory +
                ", propertyReferenceFactory=" + this.propertyReferenceFactory +
                ", metaTypeName=" + this.metaTypeName +
                ", variableSource=" + this.variableSource +
                "}";
    }

}
