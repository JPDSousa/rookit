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

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.util.Elements;

final class ExtendedElementFactoryImpl implements ExtendedElementFactory {

    // due to circular dependency in javax's Element
    private final Provider<ExtendedPackageElementFactory> packageFactory;
    private final Provider<ElementVisitor<ExtendedElement, Void>> effectiveTypeVisitor;

    private final OptionalFactory optionalFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final Elements elements;
    private final VoidUtils voidUtils;

    @Inject
    private ExtendedElementFactoryImpl(
            final Provider<ExtendedPackageElementFactory> packageFactory,
            final Provider<ElementVisitor<ExtendedElement, Void>> effectiveTypeVisitor,
            final OptionalFactory optionalFactory,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final Elements elements,
            final VoidUtils voidUtils) {
        this.packageFactory = packageFactory;
        this.effectiveTypeVisitor = effectiveTypeVisitor;
        this.optionalFactory = optionalFactory;
        this.mirrorFactory = mirrorFactory;
        this.elements = elements;
        this.voidUtils = voidUtils;
    }

    @Override
    public ExtendedElement extend(final Element element) {
        return this.optionalFactory.of(element)
                .select(ExtendedElement.class)
                .orElseGet(() -> createExtendedElement(element));
    }

    private ExtendedElement createExtendedElement(final Element element) {
        return new ExtendedElementImpl(
                this.elements,
                element,
                this.mirrorFactory,
                this.effectiveTypeVisitor,
                this.voidUtils,
                this.packageFactory
        );
    }

    @Override
    public String toString() {
        return "ExtendedElementFactoryImpl{" +
                "packageFactory=" + this.packageFactory +
                ", effectiveTypeVisitor=" + this.effectiveTypeVisitor +
                ", optionalFactory=" + this.optionalFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                ", elements=" + this.elements +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
