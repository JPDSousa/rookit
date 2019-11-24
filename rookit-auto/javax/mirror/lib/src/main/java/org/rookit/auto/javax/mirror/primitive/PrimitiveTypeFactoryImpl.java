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
package org.rookit.auto.javax.mirror.primitive;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.utils.array.ArrayUtils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;

final class PrimitiveTypeFactoryImpl implements PrimitiveTypeFactory {

    private final ArrayUtils arrayUtils;
    private final Elements elements;

    @Inject
    private PrimitiveTypeFactoryImpl(final ArrayUtils arrayUtils, final Elements elements) {
        this.arrayUtils = arrayUtils;
        this.elements = elements;
    }

    @Override
    public Single<ExtendedPrimitiveType> createFromKind(final TypeKind kind) {
        return Single.just(new PrimitiveTypeImpl(kind, toElement(kind), this.arrayUtils));
    }

    private TypeElement toElement(final TypeKind kind) {
        switch (kind) {
            case BOOLEAN:
                return this.elements.getTypeElement(Boolean.class.getName());
            case BYTE:
                return this.elements.getTypeElement(Byte.class.getName());
            case SHORT:
                return this.elements.getTypeElement(Short.class.getName());
            case INT:
                return this.elements.getTypeElement(Integer.class.getName());
            case LONG:
                return this.elements.getTypeElement(Long.class.getName());
            case CHAR:
                return this.elements.getTypeElement(Character.class.getName());
            case FLOAT:
                return this.elements.getTypeElement(Float.class.getName());
            case DOUBLE:
                return this.elements.getTypeElement(Double.class.getName());
            case VOID:
                return this.elements.getTypeElement(Void.class.getName());
            default:
                throw new IllegalArgumentException(kind + " is not a primitive kind");
        }
    }

    @Override
    public String toString() {
        return "PrimitiveTypeFactoryImpl{" +
                "arrayUtils=" + this.arrayUtils +
                ", elements=" + this.elements +
                "}";
    }

}
