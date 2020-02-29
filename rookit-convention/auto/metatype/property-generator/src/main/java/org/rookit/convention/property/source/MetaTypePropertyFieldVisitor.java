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
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.config.NamingConfig;
import org.rookit.convention.guice.MetaType;

import java.util.function.Function;

final class MetaTypePropertyFieldVisitor implements StreamExtendedElementVisitor<FieldSource, Void> {

    private final NamingConfig namingConfig;
    private final TypeVariableSource variableName;
    private final ExtendedElementVisitor<StreamEx<FieldSource>, Void> delegate;
    private final TypeParameterSourceFactory parameterFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final FieldSourceFactory fieldFactory;

    @Inject
    private MetaTypePropertyFieldVisitor(
            final NamingConfig namingConfig,
            @MetaType final TypeVariableSource variableName,
            @MetaType final ExtendedElementVisitor<StreamEx<FieldSource>, Void> delegate,
            final TypeParameterSourceFactory parameterFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final FieldSourceFactory fieldFactory) {
        this.namingConfig = namingConfig;
        this.variableName = variableName;
        this.delegate = delegate;
        this.parameterFactory = parameterFactory;
        this.referenceFactory = referenceFactory;
        this.fieldFactory = fieldFactory;
    }

    @Override
    public StreamEx<FieldSource> visitType(final ExtendedTypeElement extendedType, final Void parameter) {
        final TypeParameterSource function = this.parameterFactory.create(
                Function.class,
                this.variableName,
                this.referenceFactory.create(extendedType)
        );

        // TODO streams are meant to be lazy, and as such we shouldn't createMutable them eagerly
        return StreamEx.<FieldSource>of(
                this.fieldFactory.createMutable(this.referenceFactory.fromClass(String.class), this.namingConfig.propertyName()),
                this.fieldFactory.createMutable(function, this.namingConfig.getter())
        )
                .append(extendedType.accept(this.delegate, parameter));
    }

}
