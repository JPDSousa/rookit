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
package org.rookit.convention.auto.metatype.javapoet.type;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class BaseAnnotationVisitor<P> implements ConventionTypeElementVisitor<TypeSource, P> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseAnnotationVisitor.class);

    private final IdentifierFactory factory;
    private final TypeSourceFactory typeFactory;

    BaseAnnotationVisitor(
            final IdentifierFactory factory,
            final TypeSourceFactory typeFactory) {
        this.factory = factory;
        this.typeFactory = typeFactory;
    }

    private TypeSource create(final ExtendedElement element) {

        final Identifier identifier = this.factory.create(element);
        return this.typeFactory.createMutableAnnotation(identifier)
                .makePublic();
    }

    @Override
    public TypeSource visitConventionType(final ConventionTypeElement element, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(element);
    }

    @Override
    public TypeSource visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(packageElement);
    }

    @Override
    public TypeSource visitType(final ExtendedTypeElement extendedType, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(extendedType);
    }

    @Override
    public TypeSource visitExecutable(
            final ExtendedExecutableElement extendedExecutable, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(extendedExecutable);
    }

    @Override
    public TypeSource visitTypeParameter(
            final ExtendedTypeParameterElement extendedParameter, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(extendedParameter);
    }

    @Override
    public TypeSource visitVariable(
            final ExtendedVariableElement extendedElement, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(extendedElement);
    }

    @Override
    public TypeSource visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        logger.trace("Ignoring parameter: {}", parameter);
        return create(extendedElement);
    }

    @Override
    public String toString() {
        return "BaseAnnotationVisitor{" +
                "factory=" + this.factory +
                ", typeFactory=" + this.typeFactory +
                "}";
    }

}
