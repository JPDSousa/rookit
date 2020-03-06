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
package org.rookit.convention.meta.source.javapoet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.convention.guice.MetaType;

final class FactoryJavaPoetMethodFactoryProvider
        implements Provider<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> {

    private final ConventionTypeElementMethodSourceVisitors visitors;
    private final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterVisitor;
    private final TypeVariableSourceFactory variableFactory;
    private final TypeVariableSource variableSource;

    @Inject
    private FactoryJavaPoetMethodFactoryProvider(
            final ConventionTypeElementMethodSourceVisitors visitors,
            @MetaType final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterVisitor,
            @MetaType final TypeVariableSourceFactory variableFactory,
            @MetaType final TypeVariableSource variableSource) {
        this.visitors = visitors;
        this.parameterVisitor = parameterVisitor;
        this.variableFactory = variableFactory;
        this.variableSource = variableSource;
    }

    @Override
    public ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> get() {
        return this.visitors.factoryVisitorBuilder(
                this.parameterVisitor,
                this.variableFactory,
                this.variableSource
        ).build();
    }

    @Override
    public String toString() {
        return "FactoryJavaPoetMethodFactoryProvider{" +
                "visitors=" + this.visitors +
                ", parameterVisitor=" + this.parameterVisitor +
                ", variableFactory=" + this.variableFactory +
                ", variableSource=" + this.variableSource +
                "}";
    }

}
