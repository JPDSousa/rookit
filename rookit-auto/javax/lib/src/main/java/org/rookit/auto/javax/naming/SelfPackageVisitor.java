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
package org.rookit.auto.javax.naming;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;

final class SelfPackageVisitor implements ExtendedElementVisitor<ExtendedPackageElement, Void> {

    static final ExtendedElementVisitor<ExtendedPackageElement, Void> SINGLETON = new SelfPackageVisitor();

    private SelfPackageVisitor() {}

    @Override
    public ExtendedPackageElement visitPackage(final ExtendedPackageElement packageElement, final Void parameter) {
        return packageElement.packageInfo();
    }

    @Override
    public ExtendedPackageElement visitType(final ExtendedTypeElement extendedType, final Void parameter) {
        return extendedType.packageInfo();
    }

    @Override
    public ExtendedPackageElement visitExecutable(final ExtendedExecutableElement extendedExecutable,
                                                  final Void parameter) {
        return extendedExecutable.packageInfo();
    }

    @Override
    public ExtendedPackageElement visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                     final Void parameter) {
        return extendedParameter.packageInfo();
    }

    @Override
    public ExtendedPackageElement visitVariable(final ExtendedVariableElement extendedElement, final Void parameter) {
        return extendedElement.packageInfo();
    }

    @Override
    public ExtendedPackageElement visitUnknown(final ExtendedElement extendedElement, final Void parameter) {
        return extendedElement.packageInfo();
    }
}
