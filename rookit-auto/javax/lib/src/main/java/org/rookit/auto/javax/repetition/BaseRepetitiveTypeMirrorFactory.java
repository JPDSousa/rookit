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

import com.google.inject.Inject;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;

public final class BaseRepetitiveTypeMirrorFactory implements RepetitiveTypeMirrorFactory {

    public static RepetitiveTypeMirrorFactory create() {
        return new BaseRepetitiveTypeMirrorFactory();
    }

    @Inject
    private BaseRepetitiveTypeMirrorFactory() {}

    @Override
    public RepetitiveTypeMirror create(final ExtendedTypeMirror delegate, final int parameterIndex) {
        final ExtendedTypeMirror value = delegate.typeParameters().get(parameterIndex);
        return new RepetitiveTypeMirrorImpl(delegate, value);
    }

    @Override
    public KeyedRepetitiveTypeMirror createKeyed(final ExtendedTypeMirror delegate,
                                                 final int keyIndex,
                                                 final int valueIndex) {
        final RepetitiveTypeMirror delegateWithValue = create(delegate, valueIndex);
        final ExtendedTypeMirror key = delegateWithValue.typeParameters().get(keyIndex);
        return new KeyedRepetitiveTypeMirrorImpl(delegateWithValue, key);
    }

    @Override
    public KeyedRepetitiveTypeMirror createKeyed(final ExtendedTypeMirror delegate,
                                                 final ExtendedTypeMirror constantKey,
                                                 final int valueIndex) {
        return new KeyedRepetitiveTypeMirrorImpl(create(delegate, valueIndex), constantKey);
    }

}
