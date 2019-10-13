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

import com.squareup.javapoet.TypeSpec;
import one.util.streamex.StreamEx;
import org.rookit.auto.javapoet.identifier.JavaPoetIdentifierFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;

import java.util.function.Function;

public interface ExtendedElementTypeSpecVisitors extends ExtendedElementVisitors {

    <V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> TypeSpecBuilder<V, P> typeSpecBuilder(
            V visitor,
            Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter);

    default <P> TypeSpecBuilder<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> typeSpecBuilder(
            final ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P> visitor) {
        return typeSpecBuilder(visitor, element -> element);
    }

    <V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> AnnotationBuilder<V, P> annotationBuilder(
            JavaPoetIdentifierFactory identifierFactory,
            Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter);

    default <P> AnnotationBuilder<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> annotationBuilder(
            final JavaPoetIdentifierFactory identifierFactory) {
        return annotationBuilder(identifierFactory, element -> element);
    }

    <V extends ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> AnnotationBuilder<V, P> annotationBuilder(
            JavaPoetIdentifierFactory identifierFactory,
            Function<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, V> downcastAdapter,
            Class<P> parameterClass);

    default <P> AnnotationBuilder<ExtendedElementVisitor<StreamEx<TypeSpec.Builder>, P>, P> annotationBuilder(
            final JavaPoetIdentifierFactory identifierFactory,
            final Class<P> parameterClass) {
        return annotationBuilder(identifierFactory, element -> element, parameterClass);
    }

}
