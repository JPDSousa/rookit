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
package org.rookit.auto.javax.type.parameter;

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.ExtendedElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.TypeParameterElement;

final class ExtendedTypeParameterElementFactoryImpl implements ExtendedTypeParameterElementFactory {

    private final OptionalFactory optionalFactory;
    private final ExtendedElementFactory elementFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final ElementVisitor<ExtendedElement, Void> effectiveTypeVisitor;
    private final VoidUtils voidUtils;

    @Inject
    private ExtendedTypeParameterElementFactoryImpl(
            final OptionalFactory optionalFactory,
            final ExtendedElementFactory elementFactory,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final ElementVisitor<ExtendedElement, Void> effectiveTypeVisitor,
            final VoidUtils voidUtils) {
        this.optionalFactory = optionalFactory;
        this.elementFactory = elementFactory;
        this.mirrorFactory = mirrorFactory;
        this.effectiveTypeVisitor = effectiveTypeVisitor;
        this.voidUtils = voidUtils;
    }

    @Override
    public ExtendedTypeParameterElement extend(final TypeParameterElement javaxElement) {
        return this.optionalFactory.of(javaxElement)
                .select(ExtendedTypeParameterElement.class)
                .orElseGet(() -> new ExtendedTypeParameterElementImpl(
                        this.elementFactory.extend(javaxElement),
                        javaxElement,
                        this.effectiveTypeVisitor,
                        this.voidUtils,
                        this.mirrorFactory)
                );
    }

    @Override
    public String toString() {
        return "ExtendedTypeParameterElementFactoryImpl{" +
                "optionalFactory=" + this.optionalFactory +
                ", elementFactory=" + this.elementFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                ", effectiveTypeVisitor=" + this.effectiveTypeVisitor +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
