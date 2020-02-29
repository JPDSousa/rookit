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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.guice.MetaType;
import org.rookit.convention.metatype.AbstractMetaType;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import java.util.Collection;

final class MetaTypePartialTypeSourceFactory extends AbstractMetaTypeTypeSourceFactory {

    private final TypeParameterSourceFactory typeParameterFactory;
    private final OptionalFactory optionalFactory;
    private final TypeVariableSource variableSource;
    private final TypeReferenceSource delegateBaseMetaType;
    private final TypeReferenceSourceFactory referenceFactory;

    @Inject
    private MetaTypePartialTypeSourceFactory(
            final TypeSourceFactory typeFactory,
            final VoidUtils voidUtils,
            @MetaType final ExtendedElementVisitor<StreamEx<FieldSource>, Void> fieldVisitor,
            @MetaType final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor,
            final TypeParameterSourceFactory typeParameterFactory,
            @MetaType final TypeVariableSource variableSource,
            @MetaTypeAPI final TypeReferenceSourceFactory referenceFactory,
            final OptionalFactory optionalFactory) {

        super(typeFactory, voidUtils, methodVisitor, fieldVisitor);
        this.typeParameterFactory = typeParameterFactory;
        this.optionalFactory = optionalFactory;
        this.variableSource = variableSource;
        this.delegateBaseMetaType = typeParameterFactory.create(AbstractMetaType.class, variableSource);
        this.referenceFactory = referenceFactory;
    }

    @Override
    Collection<TypeReferenceSource> superInterfaces(final ExtendedTypeElement element) {
        return ImmutableList.of(this.typeParameterFactory.create(this.referenceFactory.create(element),
                this.variableSource));
    }

    @Override
    Optional<TypeReferenceSource> superclass(final ExtendedTypeElement element) {
        return this.optionalFactory.of(this.delegateBaseMetaType);
    }

    @Override
    Optional<TypeVariableSource> typeVariableName(final ExtendedTypeElement element) {
        return this.optionalFactory.of(this.variableSource.withBound(element));
    }
}
