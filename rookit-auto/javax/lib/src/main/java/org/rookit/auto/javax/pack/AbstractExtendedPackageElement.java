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

import com.google.common.base.Objects;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

abstract class AbstractExtendedPackageElement implements ExtendedPackageElement {

    abstract ExtendedPackageElementFactory packageFactory();

    abstract OptionalFactory optionalFactory();

    @Override
    public ExtendedPackageElement resolve(final String name) {
        return packageFactory().create(fullName().resolve(name).asString());
    }

    @Override
    public ExtendedPackageElement resolve(final ExtendedPackageElement packageElement) {
        return packageFactory().create(fullName().resolve(packageElement.fullName()));
    }

    @Override
    public Optional<ExtendedPackageElement> relativize(final ExtendedPackageElement other) {
        if (equals(other)) {
            return this.optionalFactory().empty();
        }

        final ExtendedPackageElement thisRoot = this.root();
        final ExtendedPackageElement otherRoot = other.root();
        if (!thisRoot.equals(otherRoot)) {
            return this.optionalFactory().empty();
        }

        final int thisLength = this.length();
        final int thatLength = other.length();
        final int minLength = Math.min(thisLength, thatLength);
        final ExtendedPackageElement longestPackage = (thisLength > thatLength) ? this : other;

        int cursor = 0;
        while ((cursor < minLength) && this.packageAtIndex(cursor).equals(other.packageAtIndex(cursor))) {
            cursor++;
        }

        return this.optionalFactory().of(subPackage(longestPackage, cursor));
    }

    private ExtendedPackageElement subPackage(final ExtendedPackageElement packageElement,
                                              final int beginIndex) {
        int cursor = beginIndex;
        ExtendedPackageElement relativized = packageElement.packageAtIndex(cursor);
        cursor++;

        while(cursor < packageElement.length()) {
            relativized = relativized.resolve(packageElement.packageAtIndex(cursor));
            cursor++;
        }

        return relativized;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if(!(o instanceof ExtendedPackageElement)) {
            return false;
        }

        return Objects.equal(fullName(), ((ExtendedPackageElement) o).fullName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getQualifiedName());
    }
}
