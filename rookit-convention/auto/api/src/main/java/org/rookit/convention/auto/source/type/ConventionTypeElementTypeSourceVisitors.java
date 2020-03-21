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
package org.rookit.convention.auto.source.type;

import one.util.streamex.StreamEx;
import org.rookit.auto.source.type.ExtendedElementTypeSourceVisitors;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitors;

import java.util.function.Function;

public interface ConventionTypeElementTypeSourceVisitors extends ExtendedElementTypeSourceVisitors,
        ConventionTypeElementVisitors {

    default <P> ConventionTypeSourceBuilder<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P>
    conventionTypeSourceBuilder(final ConventionTypeSourceCreator... creators) {

        return conventionTypeSourceBuilder(element -> element, creators);
    }

    <V extends ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P> ConventionTypeSourceBuilder<V, P>
    conventionTypeSourceBuilder(
            Function<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter,
            ConventionTypeSourceCreator... creators
    );

    <V extends ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P> ConventionTypeSourceBuilder<V, P>
    conventionTypeSourceBuilder(
            V visitor,
            Function<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter);

    <V extends ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P> ConventionAnnotationBuilder<V, P>
    conventionAnnotationBuilder(
            Function<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter);

    default <P> ConventionAnnotationBuilder<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P>
    conventionAnnotationBuilder() {
        return conventionAnnotationBuilder(element -> element);
    }

    <V extends ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P> ConventionAnnotationBuilder<V, P>
    conventionAnnotationBuilder(
            Class<P> parameterClass,
            Function<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, V> downcastAdapter);

    default <P> ConventionAnnotationBuilder<ConventionTypeElementVisitor<StreamEx<TypeSource>, P>, P>
    conventionAnnotationBuilder(
            final Class<P> parameterClass) {
        return conventionAnnotationBuilder(parameterClass, element -> element);
    }

}
