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
package org.rookit.auto.javax.visitor;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Name;

final class QualifiedNameVisitor<P> implements ExtendedElementVisitor<Name, P> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(QualifiedNameVisitor.class);

    private static final ExtendedElementVisitor<Name, ?> SINGLETON = new QualifiedNameVisitor<>();

    @SuppressWarnings("unchecked")
    static <PS> ExtendedElementVisitor<Name, PS> singleton() {
        return (ExtendedElementVisitor<Name, PS>) SINGLETON;
    }

    private QualifiedNameVisitor() {}

    @Override
    public Name visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        return packageElement.getQualifiedName();
    }

    @Override
    public Name visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return extendedType.getQualifiedName();
    }

    @Override
    public Name visitExecutable(final ExtendedExecutableElement extendedExecutable, final P parameter) {
        // This is acceptable if we think about it as a fallback mechanism
        // i.e. given that we don't have a qualified name, we return the simple name
        return extendedExecutable.getSimpleName();
    }

    @Override
    public Name visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        // This is acceptable if we think about it as a fallback mechanism
        // i.e. given that we don't have a qualified name, we return the simple name
        return extendedParameter.getSimpleName();
    }

    @Override
    public Name visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        // This is acceptable if we think about it as a fallback mechanism
        // i.e. given that we don't have a qualified name, we return the simple name
        return extendedElement.getSimpleName();
    }

    @Override
    public Name visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        logger.warn("Trying to resolve the qualified name of an unknown element type. Returning simple name instead.");
        return extendedElement.getSimpleName();
    }
}
