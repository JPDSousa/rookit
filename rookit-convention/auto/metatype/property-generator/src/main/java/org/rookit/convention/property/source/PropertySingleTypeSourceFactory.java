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
package org.rookit.convention.property.source;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.metatype.AbstractMetaType;
import org.rookit.convention.property.guice.PropertyModel;
import org.rookit.utils.primitive.VoidUtils;

final class PropertySingleTypeSourceFactory implements SingleTypeSourceFactory {

    private final VoidUtils voidUtils;
    private final ExtendedElementVisitor<StreamEx<FieldSource>, Void> fieldFactory;
    private final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor;
    private final TypeVariableSource typeVariable;
    private final TypeSourceFactory typeSourceFactory;

    @Inject
    private PropertySingleTypeSourceFactory(
            final VoidUtils voidUtils,
            @PropertyModel final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor,
            final ExtendedElementVisitor<StreamEx<FieldSource>, Void> fieldFactory,
            final TypeVariableSource typeVariable,
            final TypeSourceFactory typeSourceFactory) {
        this.voidUtils = voidUtils;
        this.methodVisitor = methodVisitor;
        this.fieldFactory = fieldFactory;
        this.typeVariable = typeVariable;
        this.typeSourceFactory = typeSourceFactory;
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {
        return this.typeSourceFactory.createMutableClass(identifier)
                .addTypeVariable(this.typeVariable)
                .withParameterizedSuperclass(AbstractMetaType.class, element)
                .addParameterizedInterface(element, this.typeVariable)
                .addMethods(element.accept(this.methodVisitor, this.voidUtils.returnVoid()))
                .addFields(element.accept(this.fieldFactory, this.voidUtils.returnVoid()));
    }

    @Override
    public String toString() {
        return "PropertySingleTypeSourceFactory{" +
                "voidUtils=" + this.voidUtils +
                ", fieldFactory=" + this.fieldFactory +
                ", methodVisitor=" + this.methodVisitor +
                ", typeVariable=" + this.typeVariable +
                ", typeSourceFactory=" + this.typeSourceFactory +
                "}";
    }

}
