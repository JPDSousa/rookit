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
package org.rookit.auto.javax.runtime.mirror;

import com.google.inject.Inject;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;

final class AsElementVisitor implements TypeVisitor<Optional<Element>, Void> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AsElementVisitor.class);

    private final OptionalFactory optionalFactory;

    @Inject
    private AsElementVisitor(final OptionalFactory optionalFactory) {
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Optional<Element> visit(final TypeMirror t, final Void aVoid) {
        logger.debug("Visiting generic method. Assuming no element");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visit(final TypeMirror t) {
        logger.debug("Visiting generic method. Assuming no element");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitPrimitive(final PrimitiveType t, final Void aVoid) {
        logger.trace("Visiting primitive type");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitNull(final NullType t, final Void aVoid) {
        logger.trace("Visiting null type");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitArray(final ArrayType t, final Void aVoid) {
        logger.trace("Visiting array type");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitDeclared(final DeclaredType t, final Void aVoid) {
        logger.trace("Visiting declared type");
        return this.optionalFactory.of(t.asElement());
    }

    @Override
    public Optional<Element> visitError(final ErrorType t, final Void aVoid) {
        logger.trace("Visiting error type");
        return this.optionalFactory.of(t.asElement());
    }

    @Override
    public Optional<Element> visitTypeVariable(final TypeVariable t, final Void aVoid) {
        logger.trace("Visiting type variable");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitWildcard(final WildcardType t, final Void aVoid) {
        logger.trace("Visiting wildcard type");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitExecutable(final ExecutableType t, final Void aVoid) {
        logger.trace("Visiting executable type");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitNoType(final NoType t, final Void aVoid) {
        logger.trace("Visiting no type");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitUnknown(final TypeMirror t, final Void aVoid) {
        logger.warn("Visiting unknown type.");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitUnion(final UnionType t, final Void aVoid) {
        logger.trace("Visiting union type.");
        return this.optionalFactory.empty();
    }

    @Override
    public Optional<Element> visitIntersection(final IntersectionType t, final Void aVoid) {
        logger.trace("Visiting intersection type.");
        return this.optionalFactory.empty();
    }

    @Override
    public String toString() {
        return "AsElementVisitor{" +
                "optionalFactory=" + this.optionalFactory +
                "}";
    }

}
