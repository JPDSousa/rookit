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
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.primitive.VoidUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import static java.lang.String.format;

final class ExtendedElementFactoryImpl implements ExtendedElementFactory {

    private final Messager messager;
    // due to circular dependency in javax's Element
    private final Provider<ExtendedPackageElementFactory> packageFactory;
    private final OptionalFactory optionalFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final Elements elements;
    @SuppressWarnings("FieldNotUsedInToString")
    private final VoidUtils voidUtils;

    @Inject
    private ExtendedElementFactoryImpl(
            final Messager messager,
            final Provider<ExtendedPackageElementFactory> packageFactory,
            final OptionalFactory optionalFactory,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final Elements elements,
            final VoidUtils voidUtils) {
        this.messager = messager;
        this.packageFactory = packageFactory;
        this.optionalFactory = optionalFactory;
        this.mirrorFactory = mirrorFactory;
        this.elements = elements;
        this.voidUtils = voidUtils;
    }

    @Override
    public ExtendedElement extend(final Element element) {
        return checkIfAlreadyInstanceOf(ExtendedElement.class, element)
                .orElseGet(() -> createExtendedElement(element));
    }

    @Override
    public ExtendedElement extendAsSubType(final Element element) {
        return element.accept(new ElementVisitor<ExtendedElement, Void>() {

            @Override
            public ExtendedElement visit(final Element e, final Void aVoid) {
                return extend(e);
            }

            @Override
            public ExtendedElement visit(final Element e) {
                return extend(e);
            }

            @Override
            public ExtendedElement visitPackage(final PackageElement e, final Void aVoid) {
                return extendPackage(e);
            }

            @Override
            public ExtendedElement visitType(final TypeElement e, final Void aVoid) {
                return extendType(e);
            }

            @Override
            public ExtendedElement visitVariable(final VariableElement e, final Void aVoid) {
                return extendVariable(e);
            }

            @Override
            public ExtendedElement visitExecutable(final ExecutableElement e, final Void aVoid) {
                return extendExecutable(e);
            }

            @Override
            public ExtendedElement visitTypeParameter(final TypeParameterElement e, final Void aVoid) {
                return extendParameter(e);
            }

            @Override
            public ExtendedElement visitUnknown(final Element e, final Void aVoid) {
                return extend(e);
            }
        }, this.voidUtils.returnVoid());
    }

    private ExtendedElement createExtendedElement(final Element element) {
        return new ExtendedElementImpl(this.elements, element, this.mirrorFactory, this);
    }

    @Override
    public ExtendedTypeElement extendType(final TypeElement element) {
        return checkIfAlreadyInstanceOf(ExtendedTypeElement.class, element)
                .orElseGet(() -> createExtendedTypeElement(element));
    }

    private ExtendedTypeElement createExtendedTypeElement(final TypeElement element) {
        final ExtendedElement extendedElement = createExtendedElement(element);
        final ExtendedPackageElement packageElement = this.packageFactory
                .get()
                .create(this.elements.getPackageOf(element));

        return new BaseTypeElementDecorator(
                extendedElement,
                packageElement,
                element,
                this.optionalFactory,
                this.mirrorFactory,
                this
        );
    }

    private <E> Optional<E> checkIfAlreadyInstanceOf(final Class<E> targetClass, final Element element) {
        if (targetClass.isInstance(element)) {
            final String errMsg = format("%s is already a %s. Bypassing creation.", element,
                    ExtendedTypeElement.class.getName());
            this.messager.printMessage(Diagnostic.Kind.NOTE, errMsg);
            return this.optionalFactory.of((E) element);
        }
        return this.optionalFactory.empty();
    }

    @Override
    public ExtendedExecutableElement extendExecutable(final ExecutableElement element) {
        return checkIfAlreadyInstanceOf(ExtendedExecutableElement.class, element)
                .orElseGet(() -> new ExtendedExecutableElementImpl(extend(element), element, this.mirrorFactory));
    }

    @Override
    public ExtendedTypeParameterElement extendParameter(final TypeParameterElement element) {
        return checkIfAlreadyInstanceOf(ExtendedTypeParameterElement.class, element)
                .orElseGet(() -> new ExtendedTypeParameterElementImpl(
                        extend(element),
                        element,
                        this,
                        this.mirrorFactory)
                );
    }

    @Override
    public ExtendedVariableElement extendVariable(final VariableElement element) {
        return checkIfAlreadyInstanceOf(ExtendedVariableElement.class, element)
                .orElseGet(() -> new ExtendedVariableElementImpl(extend(element), element));
    }

    @Override
    public ExtendedPackageElement extendPackage(final PackageElement packageElement) {
        return this.packageFactory.get().create(packageElement);
    }

    @Override
    public String toString() {
        return "ExtendedElementFactoryImpl{" +
                "messager=" + this.messager +
                ", packageFactory=" + this.packageFactory +
                ", optionalFactory=" + this.optionalFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                ", elements=" + this.elements +
                "}";
    }
}
