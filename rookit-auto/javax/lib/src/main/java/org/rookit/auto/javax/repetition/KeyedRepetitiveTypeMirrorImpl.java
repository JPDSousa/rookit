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
package org.rookit.auto.javax.repetition;

import com.google.common.base.Objects;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.repetition.Repetition;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.List;

final class KeyedRepetitiveTypeMirrorImpl implements KeyedRepetitiveTypeMirror {

    private final RepetitiveTypeMirror delegate;
    private final ExtendedTypeMirror key;

    KeyedRepetitiveTypeMirrorImpl(final RepetitiveTypeMirror delegate,
                                  final ExtendedTypeMirror key) {
        this.delegate = delegate;
        this.key = key;
    }

    @Override
    public ExtendedTypeMirror unwrapKey() {
        return this.key;
    }

    @Override
    public ExtendedTypeMirror unwrapValue() {
        return this.delegate.unwrapValue();
    }

    @Override
    public Repetition repetition() {
        return this.delegate.repetition();
    }

    @Override
    public Optional<Element> toElement() {
        return this.delegate.toElement();
    }

    @Override
    public boolean isSameTypeErasure(final ExtendedTypeMirror other) {
        return this.delegate.isSameTypeErasure(other);
    }

    @Override
    public boolean isSameType(final ExtendedTypeMirror other) {
        return this.delegate.isSameType(other);
    }

    @Override
    public ExtendedTypeMirror erasure() {
        return this.delegate.erasure();
    }

    @Override
    public List<? extends ExtendedTypeMirror> typeParameters() {
        return this.delegate.typeParameters();
    }

    @Override
    public TypeMirror original() {
        return this.delegate.original();
    }

    @Override
    public ExtendedTypeMirror boxIfPrimitive() {
        // TODO this cast is dangerous, we have to rethink this.
        return new KeyedRepetitiveTypeMirrorImpl((RepetitiveTypeMirror) this.delegate.boxIfPrimitive(), this.key);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final KeyedRepetitiveTypeMirrorImpl other = (KeyedRepetitiveTypeMirrorImpl) o;
        return Objects.equal(this.delegate, other.delegate) &&
                Objects.equal(this.key, other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.delegate, this.key);
    }

    @Override
    public String toString() {
        return "KeyedRepetitiveTypeMirrorImpl{" +
                "delegate=" + this.delegate +
                ", key=" + this.key +
                "}";
    }
}
