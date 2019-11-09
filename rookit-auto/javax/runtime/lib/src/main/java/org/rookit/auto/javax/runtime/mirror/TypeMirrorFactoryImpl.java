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
package org.rookit.auto.javax.runtime.mirror;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityVisitor;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeEntity;
import org.rookit.utils.primitive.VoidUtils;

import javax.lang.model.type.TypeMirror;

final class TypeMirrorFactoryImpl implements TypeMirrorFactory {

    private final RuntimeEntityVisitor<Single<TypeMirror>, Void> visitor;
    private final VoidUtils voidUtils;

    @Inject
    private TypeMirrorFactoryImpl(
            final RuntimeEntityVisitor<Single<TypeMirror>, Void> visitor,
            final VoidUtils voidUtils) {
        this.visitor = visitor;
        this.voidUtils = voidUtils;
    }

    @Override
    public Single<TypeMirror> createFromType(final RuntimeTypeEntity type) {
        return type.accept(this.visitor, this.voidUtils.returnVoid());
    }

    @Override
    public String toString() {
        return "TypeMirrorFactoryImpl{" +
                "visitor=" + this.visitor +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
