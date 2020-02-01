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
package org.rookit.auto.javax.type;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.type.parameter.TypeParameterExtractor;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.primitive.VoidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;
import java.util.Collection;
import java.util.List;

final class TypeVisitorParameterExtractor implements TypeVisitor<Collection<? extends TypeMirror>, Void>,
        TypeParameterExtractor {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeVisitorParameterExtractor.class);

    private final Failsafe failsafe;
    @SuppressWarnings("FieldNotUsedInToString")
    private final VoidUtils voidUtils;
    private final ExtendedTypeMirrorFactory typeMirrorFactory;

    @Inject
    private TypeVisitorParameterExtractor(
            final Failsafe failsafe,
            final VoidUtils voidUtils,
            final ExtendedTypeMirrorFactory typeMirrorFactory) {
        this.failsafe = failsafe;
        this.voidUtils = voidUtils;
        this.typeMirrorFactory = typeMirrorFactory;
    }

    @Override
    public Collection<TypeMirror> visit(final TypeMirror typeMirror, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, typeMirror, "typeMirror");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visit(final TypeMirror typeMirror) {
        this.failsafe.checkArgument().isNotNull(logger, typeMirror, "typeMirror");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitPrimitive(final PrimitiveType primitiveType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, primitiveType, "primitiveType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitNull(final NullType nullType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, nullType, "nullType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitArray(final ArrayType arrayType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, arrayType, "arrayType");
        return ImmutableList.of();
    }

    @Override
    public Collection<? extends TypeMirror> visitDeclared(final DeclaredType declaredType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, declaredType, "declaredType");
        return declaredType.getTypeArguments();
    }

    @Override
    public Collection<TypeMirror> visitError(final ErrorType errorType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, errorType, "errorType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitTypeVariable(final TypeVariable typeVariable, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, typeVariable, "typeVariable");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitWildcard(final WildcardType wildcardType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, wildcardType, "wildcardType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitExecutable(final ExecutableType executableType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, executableType, "executableType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitNoType(final NoType noType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, noType, "noType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitUnknown(final TypeMirror typeMirror, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, typeMirror, "typeMirror");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitUnion(final UnionType unionType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, unionType, "unionType");
        return ImmutableList.of();
    }

    @Override
    public Collection<TypeMirror> visitIntersection(final IntersectionType intersectionType, final Void aVoid) {
        this.failsafe.checkArgument().isNotNull(logger, intersectionType, "intersectionType");
        return ImmutableList.of();
    }

    @Override
    public List<? extends ExtendedTypeMirror> extract(final TypeMirror type) {
        this.failsafe.checkArgument().isNotNull(logger, type, "type");
        return StreamEx.of(type.accept(this, this.voidUtils.returnVoid()))
                .map(this.typeMirrorFactory::extend)
                .toImmutableList();
    }


    @Override
    public String toString() {
        return "TypeVisitorParameterExtractor{" +
                "injector=" + this.failsafe +
                ", typeMirrorFactory=" + this.typeMirrorFactory +
                "}";
    }
}
