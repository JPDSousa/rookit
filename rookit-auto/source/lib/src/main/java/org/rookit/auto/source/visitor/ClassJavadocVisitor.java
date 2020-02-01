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
package org.rookit.auto.source.visitor;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.doc.JavadocTemplate1;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ClassJavadocVisitor<P> implements ExtendedElementVisitor<StreamEx<TypeSource>, P> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ClassJavadocVisitor.class);

    private final TypeSourceFactory typeFactory;
    private final ExtendedElementVisitor<StreamEx<TypeSource>, P> upstream;
    private final JavadocTemplate1 template;

    ClassJavadocVisitor(
            final TypeSourceFactory typeFactory,
            final ExtendedElementVisitor<StreamEx<TypeSource>, P> upstream,
            final JavadocTemplate1 template) {
        this.typeFactory = typeFactory;
        this.upstream = upstream;
        this.template = template;
    }

    @Override
    public StreamEx<TypeSource> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        logger.debug("Noop for packages");
        return packageElement.accept(this.upstream, parameter)
                .select(TypeSource.class);
    }

    @Override
    public StreamEx<TypeSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        // TODO this is redundant if the upstream is empty
        final String javadoc = this.template.build(extendedType);
        logger.debug("Generated javadocs for {}: {}", extendedType, javadoc);

        return extendedType.accept(this.upstream, parameter)
                .map(this.typeFactory::makeMutable)
                .map(builder -> builder.addJavadoc(javadoc));
    }

    @Override
    public StreamEx<TypeSource> visitExecutable(final ExtendedExecutableElement extendedExecutable,
                                                final P parameter) {
        logger.debug("Noop for executables");
        return extendedExecutable.accept(this.upstream, parameter)
                .select(TypeSource.class);
    }

    @Override
    public StreamEx<TypeSource> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                   final P parameter) {
        logger.debug("Noop for type parameters");
        return extendedParameter.accept(this.upstream, parameter)
                .select(TypeSource.class);
    }

    @Override
    public StreamEx<TypeSource> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        logger.debug("Noop for variables");
        return extendedElement.accept(this.upstream, parameter)
                .select(TypeSource.class);
    }

    @Override
    public StreamEx<TypeSource> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        logger.debug("Noop for unknown elements");
        return extendedElement.accept(this.upstream, parameter)
                .select(TypeSource.class);
    }

    @Override
    public String toString() {
        return "ClassJavadocVisitor{" +
                "typeFactory=" + this.typeFactory +
                ", upstream=" + this.upstream +
                ", template=" + this.template +
                "}";
    }

}
