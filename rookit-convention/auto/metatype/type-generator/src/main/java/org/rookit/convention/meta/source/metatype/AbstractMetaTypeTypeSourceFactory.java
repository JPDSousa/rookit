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

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.primitive.VoidUtils;

import java.util.Collection;

@Deprecated
abstract class AbstractMetaTypeTypeSourceFactory implements SingleTypeSourceFactory {

    private final TypeSourceFactory typeSourceFactory;

    private final VoidUtils voidUtils;
    private final ExtendedElementVisitor<StreamEx<FieldSource>, Void> fieldVisitor;
    private final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor;

    AbstractMetaTypeTypeSourceFactory(
            final TypeSourceFactory typeSourceFactory,
            final VoidUtils voidUtils,
            final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor,
            final ExtendedElementVisitor<StreamEx<FieldSource>, Void> fieldVisitor) {
        this.typeSourceFactory = typeSourceFactory;
        this.voidUtils = voidUtils;
        this.methodVisitor = methodVisitor;
        this.fieldVisitor = fieldVisitor;
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {
        final MutableTypeSource typeSource = this.typeSourceFactory.createMutableClass(identifier)
                .makePublic()
                .makeFinal()
                .addInterfaces(superInterfaces(element))
                .addFields(element.accept(this.fieldVisitor, this.voidUtils.returnVoid()))
                .addMethods(element.accept(this.methodVisitor, this.voidUtils.returnVoid()));
        typeVariableName(element).ifPresent(typeSource::addTypeVariable);
        superclass(element).ifPresent(typeSource::withSuperclass);
        
        return typeSource;
    }

    abstract Collection<TypeReferenceSource> superInterfaces(ExtendedTypeElement element);

    abstract Optional<TypeReferenceSource> superclass(ExtendedTypeElement element);

    abstract Optional<TypeVariableSource> typeVariableName(ExtendedTypeElement element);

}
