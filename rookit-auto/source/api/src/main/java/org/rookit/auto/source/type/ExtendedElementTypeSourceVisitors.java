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
package org.rookit.auto.source.type;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;
import org.rookit.auto.source.type.annotation.AnnotationBuilder;
import org.rookit.auto.source.type.inter.face.InterfaceBuilder;

import java.util.function.Function;

public interface ExtendedElementTypeSourceVisitors extends ExtendedElementVisitors {

    <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> TypeSourceBuilder<V, P> typeSourceBuilder(
            V visitor,
            Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter);

    default <P> TypeSourceBuilder<ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> typeSourceBuilder(
            final ExtendedElementVisitor<StreamEx<TypeSource>, P> visitor) {
        return typeSourceBuilder(visitor, element -> element);
    }

    <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> AnnotationBuilder<V, P> annotationBuilder(
            IdentifierFactory identifierFactory,
            Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter);

    default <P> AnnotationBuilder<ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> annotationBuilder(
            final IdentifierFactory identifierFactory) {
        return annotationBuilder(identifierFactory, element -> element);
    }

    <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> AnnotationBuilder<V, P> annotationBuilder(
            IdentifierFactory identifierFactory,
            Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter,
            Class<P> parameterClass);

    default <P> AnnotationBuilder<ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> annotationBuilder(
            final IdentifierFactory identifierFactory,
            final Class<P> parameterClass) {
        return annotationBuilder(identifierFactory, element -> element, parameterClass);
    }

    default <P> InterfaceBuilder<ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> interfaceBuilder(
            final IdentifierFactory identifierFactory,
            final Class<P> parameterClass) {
        return interfaceBuilder(identifierFactory, element -> element, parameterClass);
    }

    <V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P> InterfaceBuilder<V, P> interfaceBuilder(
            IdentifierFactory identifierFactory,
            Function<ExtendedElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter,
            Class<P> parameterClass);

}
