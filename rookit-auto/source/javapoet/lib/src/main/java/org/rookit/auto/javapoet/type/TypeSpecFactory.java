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
package org.rookit.auto.javapoet.type;

import com.google.inject.Inject;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.source.spec.SpecFactory;
import org.rookit.auto.source.type.TypeSource;

final class TypeSpecFactory implements SpecFactory<TypeSource> {

    private final SpecFactory<TypeSpec> specFactory;
    private final JavaPoetTypeSourceFactory sourceFactory;
    private final IdentifierFactory identifierFactory;

    @Inject
    private TypeSpecFactory(final SpecFactory<TypeSpec> specFactory,
                            final JavaPoetTypeSourceFactory sourceFactory,
                            final IdentifierFactory identifierFactory) {
        this.specFactory = specFactory;
        this.sourceFactory = sourceFactory;
        this.identifierFactory = identifierFactory;
    }

    @Override
    public StreamEx<TypeSource> create(final ExtendedElement element) {
        final Identifier identifier = this.identifierFactory.create(element);
        return this.specFactory.create(element)
                .map(spec -> this.sourceFactory.fromTypeSpec(identifier, spec));
    }

    @Override
    public String toString() {
        return "TypeSpecFactory{" +
                "specFactory=" + this.specFactory +
                ", sourceFactory=" + this.sourceFactory +
                ", identifierFactory=" + this.identifierFactory +
                "}";
    }
}
