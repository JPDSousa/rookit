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
package org.rookit.auto.javax;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.reflect.ClassVisitor;
import org.rookit.utils.reflect.ExtendedClassFactory;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Collection;

final class ElementUtilsImpl implements ElementUtils, ClassVisitor<TypeMirror> {

    private final Elements elements;
    private final Types types;
    private final OptionalFactory optionalFactory;
    private final ExtendedClassFactory extendedClassFactory;

    @Inject
    private ElementUtilsImpl(final Elements elements,
                             final Types types,
                             final OptionalFactory optionalFactory,
                             final ExtendedClassFactory extendedClassFactory) {
        this.elements = elements;
        this.types = types;
        this.optionalFactory = optionalFactory;
        this.extendedClassFactory = extendedClassFactory;
    }

    @Override
    public <T extends TypeMirror> Collection<T> intersection(final Collection<T> typesA, final Collection<T> typesB) {
        final ImmutableSet.Builder<T> builder = ImmutableSet.builder();
        for (final T type : typesA) {
            for (final T anotherType : typesB) {
                if (this.types.isSameType(type, anotherType)) {
                    builder.add(type);
                }
            }
        }
        return builder.build();
    }

    @Override
    public TypeMirror fromClassErasured(final Class<?> clazz) {
        return this.extendedClassFactory.create(clazz).accept(this);
    }

    private TypeMirror create(final TypeKind typeKind) {
        return this.types.getPrimitiveType(typeKind);
    }

    @Override
    public TypeMirror booleanClass() {
        return create(TypeKind.BOOLEAN);
    }

    @Override
    public TypeMirror byteClass() {
        return create(TypeKind.BYTE);
    }

    @Override
    public TypeMirror shortClass() {
        return create(TypeKind.SHORT);
    }

    @Override
    public TypeMirror intClass() {
        return create(TypeKind.INT);
    }

    @Override
    public TypeMirror floatClass() {
        return create(TypeKind.FLOAT);
    }

    @Override
    public TypeMirror doubleClass() {
        return create(TypeKind.DOUBLE);
    }

    @Override
    public TypeMirror longClass() {
        return create(TypeKind.LONG);
    }

    @Override
    public TypeMirror regularClass(final Class<?> clazz) {
        return this.optionalFactory.ofNullable(this.elements.getTypeElement(clazz.getCanonicalName()))
                .map(Element::asType)
                .map(this.types::erasure)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Class '%s' cannot be converted into "
                                                                                      + "a proper TypeMirror",
                        clazz)));
    }

    @Override
    public String toString() {
        return "ElementUtilsImpl{" +
                "elements=" + this.elements +
                ", types=" + this.types +
                ", optionalFactory=" + this.optionalFactory +
                ", extendedClassFactory=" + this.extendedClassFactory +
                "}";
    }
}
