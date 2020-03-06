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

import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.utils.adapt.Adapter;

final class BaseIdentifierTransformer implements IdentifierTransformer {

    private final Adapter<ExtendedPackageElement> packageTransformer;
    private final Adapter<CharSequence> nameTransformer;
    private final IdentifierFactory identifierFactory;

    BaseIdentifierTransformer(
            final Adapter<ExtendedPackageElement> packageTransformer,
            final Adapter<CharSequence> nameTransformer,
            final IdentifierFactory identifierFactory) {
        this.packageTransformer = packageTransformer;
        this.nameTransformer = nameTransformer;
        this.identifierFactory = identifierFactory;
    }

    @Override
    public ExtendedPackageElement transformPackage(final ExtendedPackageElement source) {

        return this.packageTransformer.adapt(source);
    }

    @Override
    public CharSequence transformName(final CharSequence source) {

        return this.nameTransformer.adapt(source);
    }

    @Override
    public Identifier transformIdentifier(final Identifier source) {

        return this.identifierFactory.fromSplitPackageAndName(
                transformPackage(source.packageElement()),
                transformName(source.name())
        );
    }

}
