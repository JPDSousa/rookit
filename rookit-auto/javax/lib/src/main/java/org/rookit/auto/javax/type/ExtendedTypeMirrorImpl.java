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
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.repetition.Repetition;

import javax.lang.model.element.Element;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;

final class ExtendedTypeMirrorImpl implements ExtendedTypeMirror {

    private final TypeMirror delegate;
    private final Types types;
    private final ExtendedTypeMirrorFactory factory;
    private final List<? extends ExtendedTypeMirror> typeParameters;
    private final OptionalFactory optionalFactory;
    private final Repetition repetition;

    ExtendedTypeMirrorImpl(final TypeMirror delegate,
                           final Types types,
                           final ExtendedTypeMirrorFactory factory,
                           final TypeParameterExtractor extractor,
                           final OptionalFactory optionalFactory,
                           final Repetition repetition) {
        this.delegate = delegate;
        this.types = types;
        this.factory = factory;
        this.typeParameters = ImmutableList.copyOf(extractor.extract(this.delegate));
        this.optionalFactory = optionalFactory;
        this.repetition = repetition;
    }

    @Override
    public List<? extends ExtendedTypeMirror> typeParameters() {
        //noinspection AssignmentOrReturnOfFieldWithMutableType already immutable
        return this.typeParameters;
    }

    @Override
    public TypeMirror original() {
        return this.delegate;
    }

    @Override
    public Repetition repetition() {
        return this.repetition;
    }

    @Override
    public Optional<Element> toElement() {
        return this.optionalFactory.ofNullable(this.types.asElement(this.delegate));
    }

    @Override
    public boolean isSameTypeErasure(final ExtendedTypeMirror other) {
        return isSameType(other.erasure());
    }

    @Override
    public boolean isSameType(final ExtendedTypeMirror other) {
        return this.types.isSameType(this.delegate, other.original());
    }

    @Override
    public ExtendedTypeMirror erasure() {
        return this.factory.create(this.types.erasure(this.delegate));
    }

    @Override
    public ExtendedTypeMirror boxIfPrimitive() {
        if (getKind().isPrimitive()) {
            return this.factory.create(this.types.boxedClass((PrimitiveType) this.delegate).asType());
        }
        return this;
    }

    @Override
    public String toString() {
        return "ExtendedTypeMirrorImpl{" +
                "delegate=" + this.delegate +
                ", types=" + this.types +
                ", factory=" + this.factory +
                ", typeParameters=" + this.typeParameters +
                ", optionalFactory=" + this.optionalFactory +
                ", repetition=" + this.repetition +
                "}";
    }
}
