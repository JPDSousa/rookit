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
package org.rookit.auto.javax.pack;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.string.join.JointString;

import javax.lang.model.element.PackageElement;

public interface ExtendedPackageElement extends PackageElement, ExtendedElement {

    String name();

    JointString fullName();

    ExtendedPackageElement resolve(String name);

    ExtendedPackageElement resolve(ExtendedPackageElement packageElement);

    boolean isSubPackageOf(ExtendedPackageElement packageElement);

    Optional<ExtendedPackageElement> relativize(ExtendedPackageElement other);

    ExtendedPackageElement root();

    int length();

    ExtendedPackageElement packageAtIndex(int index);

    @Override
    default <R, P> R accept(final ExtendedElementVisitor<R, P> visitor, final P parameter) {
        return visitor.visitPackage(this, parameter);
    }

    @Override
    default ExtendedPackageElement packageInfo() {
        return this;
    }

}
