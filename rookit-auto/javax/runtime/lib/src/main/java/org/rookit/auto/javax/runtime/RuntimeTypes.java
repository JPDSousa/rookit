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
package org.rookit.auto.javax.runtime;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.element.RuntimeElement;
import org.rookit.auto.javax.mirror.NullTypeFactory;
import org.rookit.auto.javax.mirror.array.ArrayTypeFactory;
import org.rookit.auto.javax.runtime.mirror.declared.DeclaredTypeFactory;
import org.rookit.auto.javax.runtime.mirror.declared.RuntimeDeclaredType;
import org.rookit.auto.javax.mirror.no.NoTypeFactory;
import org.rookit.auto.javax.runtime.mirror.primitive.PrimitiveTypeFactory;
import org.rookit.auto.javax.runtime.mirror.primitive.RuntimePrimitiveType;
import org.rookit.auto.javax.mirror.wildcard.WildcardTypeFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.primitive.VoidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import java.util.List;

import static java.util.Objects.nonNull;
import static javax.lang.model.type.TypeKind.EXECUTABLE;
import static javax.lang.model.type.TypeKind.PACKAGE;

final class RuntimeTypes implements Types {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeTypes.class);

    private final Failsafe failsafe;
    private final NoTypeFactory noTypeFactory;
    private final PrimitiveTypeFactory primitiveTypeFactory;
    private final TypeVisitor<Optional<Element>, Void> asElementVisitor;
    private final NullTypeFactory nullTypeFactory;
    private final ArrayTypeFactory arrayTypeFactory;
    private final WildcardTypeFactory wildcardTypeFactory;
    private final VoidUtils voidUtils;
    private final DeclaredTypeFactory declaredTypeFactory;

    @Inject
    private RuntimeTypes(
            final Failsafe failsafe,
            final NoTypeFactory noTypeFactory,
            final PrimitiveTypeFactory primitiveTypeFactory,
            final TypeVisitor<Optional<Element>, Void> asElementVisitor,
            final NullTypeFactory nullTypeFactory,
            final ArrayTypeFactory arrayTypeFactory,
            final WildcardTypeFactory wildcardTypeFactory,
            final VoidUtils voidUtils,
            final DeclaredTypeFactory declaredTypeFactory) {
        this.failsafe = failsafe;
        this.noTypeFactory = noTypeFactory;
        this.primitiveTypeFactory = primitiveTypeFactory;
        this.asElementVisitor = asElementVisitor;
        this.nullTypeFactory = nullTypeFactory;
        this.arrayTypeFactory = arrayTypeFactory;
        this.wildcardTypeFactory = wildcardTypeFactory;
        this.voidUtils = voidUtils;
        this.declaredTypeFactory = declaredTypeFactory;
    }

    @Override
    public Element asElement(final TypeMirror t) {
        return t.accept(this.asElementVisitor, this.voidUtils.returnVoid())
                .orElse(null);
    }

    @Override
    public boolean isSameType(final TypeMirror t1, final TypeMirror t2) {
        if ((t1 instanceof WildcardType) || (t2 instanceof WildcardType)) {
            return false;
        }

        return t1.equals(t2);
    }

    @Override
    public boolean isSubtype(final TypeMirror t1, final TypeMirror t2) {

        final RuntimeDeclaredType rt1 = checkNoPackageOrExecutableType(t1);
        final RuntimeDeclaredType rt2 = checkNoPackageOrExecutableType(t2);

        return rt1.isSubTypeOf(rt2);
    }

    private RuntimeDeclaredType checkNoPackageOrExecutableType(final TypeMirror t) {

        final TypeKind kind = t.getKind();
        final boolean condition = (kind != EXECUTABLE) && (kind != PACKAGE);
        final String errMessage = "Sub types cannot be evaluated for packages or executables, "
                + "but the provided argument is %s";

        this.failsafe.checkArgument()
                .is(logger, condition, errMessage, kind);

        if (t instanceof RuntimeDeclaredType) {
            return (RuntimeDeclaredType) t;
        }

        // TODO improve for other types
        throw new IllegalArgumentException("This method only supports types of " + RuntimeDeclaredType.class);
    }

    private RuntimePrimitiveType checkPrimitiveType(final TypeMirror t) {
        return this.failsafe.checkArgument().isInstanceOf(logger, t, RuntimePrimitiveType.class);
    }

    @Override
    public boolean isAssignable(final TypeMirror t1, final TypeMirror t2) {

        final RuntimeDeclaredType rt1 = checkNoPackageOrExecutableType(t1);
        final RuntimeDeclaredType rt2 = checkNoPackageOrExecutableType(t2);

        return rt1.isAssignableFrom(rt2);
    }

    @Override
    public boolean contains(final TypeMirror t1, final TypeMirror t2) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isSubsignature(final ExecutableType m1, final ExecutableType m2) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<? extends TypeMirror> directSupertypes(final TypeMirror t) {

        return checkNoPackageOrExecutableType(t).directSubtypes();
    }

    @Override
    public TypeMirror erasure(final TypeMirror t) {

        return checkNoPackageOrExecutableType(t).erasure();
    }

    @Override
    public TypeElement boxedClass(final PrimitiveType p) {

        return checkPrimitiveType(p).boxedClass();
    }

    @Override
    public PrimitiveType unboxedType(final TypeMirror t) {

        return checkPrimitiveType(t);
    }

    @Override
    public TypeMirror capture(final TypeMirror t) {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PrimitiveType getPrimitiveType(final TypeKind kind) {

        return this.primitiveTypeFactory.createFromKind(kind);
    }

    @Override
    public NullType getNullType() {
        return this.nullTypeFactory.nullType();
    }

    @Override
    public NoType getNoType(final TypeKind kind) {
        return this.noTypeFactory.noType();
    }

    @Override
    public ArrayType getArrayType(final TypeMirror componentType) {

        return this.arrayTypeFactory.createFromComponent(componentType)
                .blockingGet();
    }

    @Override
    public WildcardType getWildcardType(final TypeMirror extendsBound, final TypeMirror superBound) {

        final boolean extendsPresent = nonNull(extendsBound);
        final boolean superPresent = nonNull(superBound);

        if (!extendsPresent && !superPresent) {
            return this.wildcardTypeFactory.createWildcard()
                    .blockingGet();
        }

        if (extendsPresent && !superPresent) {
            return this.wildcardTypeFactory.createFromExtends(extendsBound)
                    .blockingGet();
        }

        if (!extendsPresent) {
            return this.wildcardTypeFactory.createFromSuper(superBound)
                    .blockingGet();
        }

        throw new IllegalArgumentException("Both extends and super bounds were specified, can only specify one at max");
    }

    @Override
    public DeclaredType getDeclaredType(final TypeElement typeElem, final TypeMirror... typeArgs) {
        return this.declaredTypeFactory.createFromElement(typeElem, ImmutableList.copyOf(typeArgs))
                .blockingGet();
    }

    @Override
    public DeclaredType getDeclaredType(
            final DeclaredType containing, final TypeElement typeElem, final TypeMirror... typeArgs) {
        return this.declaredTypeFactory.createFromElement(containing, typeElem, ImmutableList.copyOf(typeArgs))
                .blockingGet();
    }

    @Override
    public TypeMirror asMemberOf(final DeclaredType containing, final Element element) {

        final RuntimeDeclaredType runtimeContaining = this.failsafe.checkArgument()
                .isInstanceOf(logger, containing, RuntimeDeclaredType.class);
        return this.failsafe.checkArgument().isInstanceOf(logger, element, RuntimeElement.class)
                .asMemberOf(runtimeContaining);
    }

    @Override
    public String toString() {
        return "RuntimeTypes{" +
                "noTypeFactory=" + this.noTypeFactory +
                ", asElementVisitor=" + this.asElementVisitor +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
