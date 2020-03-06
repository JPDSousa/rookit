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

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.ExtendedElementFactory;
import org.rookit.auto.javax.executable.ExtendedExecutableElementFactory;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElementFactory;
import org.rookit.utils.optional.OptionalFactory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

final class ExtendedTypeElementFactoryImpl implements ExtendedTypeElementFactory {

    private final ExtendedElementFactory elementFactory;
    private final ExtendedPackageElementFactory packageFactory;
    private final Elements elements;
    private final IdentifierFactory identifierFactory;
    private final OptionalFactory optionalFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final ExtendedExecutableElementFactory executableFactory;
    private final ExtendedTypeParameterElementFactory typeParameterFactory;

    @Inject
    private ExtendedTypeElementFactoryImpl(
            final ExtendedElementFactory elementFactory,
            final ExtendedPackageElementFactory packageFactory,
            final Elements elements,
            final IdentifierFactory identifierFactory,
            final OptionalFactory optionalFactory,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final ExtendedExecutableElementFactory executableFactory,
            final ExtendedTypeParameterElementFactory typeParameterFactory) {
        this.elementFactory = elementFactory;
        this.packageFactory = packageFactory;
        this.elements = elements;
        this.identifierFactory = identifierFactory;
        this.optionalFactory = optionalFactory;
        this.mirrorFactory = mirrorFactory;
        this.executableFactory = executableFactory;
        this.typeParameterFactory = typeParameterFactory;
    }

    @Override
    public ExtendedTypeElement extend(final TypeElement javaxElement) {
        return this.optionalFactory.of(javaxElement)
                .select(ExtendedTypeElement.class)
                .orElseGet(() -> createExtendedTypeElement(javaxElement));
    }

    private ExtendedTypeElement createExtendedTypeElement(final TypeElement element) {
        final ExtendedElement extendedElement = this.elementFactory.extend(element);
        final ExtendedPackageElement packageElement = this.packageFactory
                .extend(this.elements.getPackageOf(element));
        final Identifier identifier = this.identifierFactory.fromSplitPackageAndName(packageElement, element.getSimpleName());

        return new ExtendedTypeElementImpl(
                extendedElement,
                element,
                this.optionalFactory,
                this.mirrorFactory,
                identifier,
                this.executableFactory,
                this.typeParameterFactory);
    }

    @Override
    public String toString() {
        return "ExtendedTypeElementFactoryImpl{" +
                "elementFactory=" + this.elementFactory +
                ", packageFactory=" + this.packageFactory +
                ", elements=" + this.elements +
                ", identifierFactory=" + this.identifierFactory +
                ", optionalFactory=" + this.optionalFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                ", executableFactory=" + this.executableFactory +
                ", typeParameterFactory=" + this.typeParameterFactory +
                "}";
    }

}
