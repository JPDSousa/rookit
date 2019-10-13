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

import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;

final class AddAnnotationsVisitor<P> implements ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> {

    private final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> upstream;
    private final Iterable<AnnotationSpec> annotations;

    AddAnnotationsVisitor(final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> upstream,
                          final Iterable<AnnotationSpec> annotations) {
        this.upstream = upstream;
        this.annotations = ImmutableList.copyOf(annotations);
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitPackage(final ExtendedPackageElement packageElement,
                                                   final P parameter) {
        return this.upstream.visitPackage(packageElement, parameter)
                .map(builder -> builder.addAnnotations(this.annotations));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return this.upstream.visitType(extendedType, parameter)
                .map(builder -> builder.addAnnotations(this.annotations));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitExecutable(final ExtendedExecutableElement extendedExecutable,
                                                      final P parameter) {
        return this.upstream.visitExecutable(extendedExecutable, parameter)
                .map(builder -> builder.addAnnotations(this.annotations));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitTypeParameter(final ExtendedTypeParameterElement extendedParameter,
                                                         final P parameter) {
        return this.upstream.visitTypeParameter(extendedParameter, parameter)
                .map(builder -> builder.addAnnotations(this.annotations));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitVariable(final ExtendedVariableElement extendedElement, final P parameter) {
        return this.upstream.visitVariable(extendedElement, parameter)
                .map(builder -> builder.addAnnotations(this.annotations));
    }

    @Override
    public StreamEx<TypeSpec.Builder> visitUnknown(final ExtendedElement extendedElement, final P parameter) {
        return this.upstream.visitUnknown(extendedElement, parameter)
                .map(builder -> builder.addAnnotations(this.annotations));
    }

    @Override
    public String toString() {
        return "AddAnnotationsVisitor{" +
                "upstream=" + this.upstream +
                ", annotations=" + this.annotations +
                "}";
    }
}
