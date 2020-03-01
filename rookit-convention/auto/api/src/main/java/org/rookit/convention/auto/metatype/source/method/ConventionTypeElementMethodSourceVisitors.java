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
package org.rookit.convention.auto.metatype.source.method;

import com.google.common.collect.ImmutableList;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitors;
import org.rookit.convention.auto.metatype.property.Property;
import org.rookit.utils.string.template.Template1;

import java.util.Collection;
import java.util.function.Function;

// TODO improve variable naming to better express what they mean
public interface ConventionTypeElementMethodSourceVisitors extends ConventionTypeElementVisitors {

    <V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P> StreamExMethodSourceBuilder<V, P>
    constructorVisitorBuilder(
            ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterVisitor,
            Function<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, V> downcastAdapter);

    default <P> StreamExMethodSourceBuilder<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    constructorVisitorBuilder(
            final ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterVisitor) {
        return constructorVisitorBuilder(parameterVisitor, element -> element);
    }

    <V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P> StreamExMethodSourceBuilder<V, P>
    factoryVisitorBuilder(
            ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterVisitor,
            TypeVariableSourceFactory parameterResolver,
            TypeVariableSource variableName,
            Function<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, V> downcastAdapter);

    default <P> StreamExMethodSourceBuilder<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    factoryVisitorBuilder(
            final ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterFactory,
            final TypeVariableSourceFactory parameterResolver,
            final TypeVariableSource variableSource) {
        return factoryVisitorBuilder(parameterFactory, parameterResolver, variableSource,
                                     element -> element);
    }

    // TODO should we have an overload which doesn't require the method factory?
    <V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P> StreamExMethodSourceBuilder<V, P>
    templateMethodSourceVisitorBuilder(
            MethodSourceFactory methodFactory,
            Template1 template,
            Function<Property, Collection<ParameterSource>> parameterResolver,
            Function<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, V> downcastAdapter);

    default <P> StreamExMethodSourceBuilder<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    templateMethodSourceVisitorBuilder(
            final MethodSourceFactory methodFactory,
            final Template1 template,
            final Function<Property, Collection<ParameterSource>> parameterResolver) {
        return templateMethodSourceVisitorBuilder(methodFactory, template, parameterResolver, element -> element);
    }

    default <V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P> StreamExMethodSourceBuilder<V, P>
    templateMethodSourceVisitorBuilder(
            final Function<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, V> downcastAdapter,
            final MethodSourceFactory methodFactory,
            final Template1 template) {
        return templateMethodSourceVisitorBuilder(methodFactory, template, property -> ImmutableList.of(),
                                                  downcastAdapter);
    }

    default <P> StreamExMethodSourceBuilder<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    templateMethodSourceVisitorBuilder(
            final MethodSourceFactory methodSourceFactory,
            final Template1 template) {
        return templateMethodSourceVisitorBuilder(methodSourceFactory, template, property -> ImmutableList.of());
    }

    <V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    StreamExMethodSourceBuilder<V, P> streamExMethodBuilder(
            V visitor,
            Function<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, V> downcastAdapter);

    default <P>
    StreamExMethodSourceBuilder<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P> streamExMethodBuilder(
            final ConventionTypeElementVisitor<StreamEx<MethodSource>, P> visitor) {
        return streamExMethodBuilder(visitor, element -> element);
    }

    <V extends ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    StreamExMethodSourceBuilder<V, P> getterMethodBuilder(
            ExtendedExecutableElement executable,
            Function<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, V> downcastAdapter);

    default <P> StreamExMethodSourceBuilder<ConventionTypeElementVisitor<StreamEx<MethodSource>, P>, P>
    getterMethodBuilder(final ExtendedExecutableElement executable) {
        return getterMethodBuilder(executable, element -> element);
    }

}
