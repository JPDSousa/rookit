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

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.javapoet.doc.JavaPoetJavadocTemplate1;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ClassJavadocVisitor<P> implements ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ClassJavadocVisitor.class);

    private final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> upstream;
    private final JavaPoetJavadocTemplate1 template;

    ClassJavadocVisitor(final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> upstream,
                        final JavaPoetJavadocTemplate1 template) {
        this.upstream = upstream;
        this.template = template;
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitPackage(final ExtendedPackageElement packageElement, final P parameter) {
        logger.debug("Noop for packages");
        return this.upstream.visitPackage(packageElement, parameter);
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        // TODO this is redundant if the upstream is empty
        final ClassName className = ClassName.get(extendedType);
        final String javadoc = this.template.build(className);
        logger.debug("Generated javadocs for {}: {}", className, javadoc);

        return this.upstream.visitType(extendedType, parameter)
                .map(builder -> builder.addJavadoc(javadoc));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitExecutable(final ExtendedExecutableElement extendedExecutable,
                                                      final P parameter) {
        logger.debug("Noop for executables");
        return this.upstream.visitExecutable(extendedExecutable, parameter);
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                         final P parameter) {
        logger.debug("Noop for type parameters");
        return this.upstream.visitTypeParameter(extendedParameter, parameter);
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        logger.debug("Noop for variables");
        return this.upstream.visitVariable(extendedElement, parameter);
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        logger.debug("Noop for unknown elements");
        return this.upstream.visitUnknown(extendedElement, parameter);
    }

    @Override
    public String toString() {
        return "ClassJavadocVisitor{" +
                "upstream=" + this.upstream +
                ", template=" + this.template +
                "}";
    }
}
