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
package org.rookit.storage.method.type.collection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.guice.RemoveAll;
import org.rookit.utils.string.template.Template1;

import java.util.Collection;
import java.util.function.Function;

final class RemoveAllMethodFactoryProvider
        implements Provider<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> {

    private final Template1 template;
    private final MethodSourceFactory methodFactory;
    private final Function<Property, Collection<ParameterSource>> parameterResolver;
    private final ConventionTypeElementMethodSourceVisitors visitors;

    @Inject
    private RemoveAllMethodFactoryProvider(
            @RemoveAll final Template1 template,
            final MethodSourceFactory methodFactory,
            @org.rookit.storage.guice.Collection final Function<Property, Collection<ParameterSource>> parameterResolver,
            final ConventionTypeElementMethodSourceVisitors visitors) {
        this.template = template;
        this.methodFactory = methodFactory;
        this.parameterResolver = parameterResolver;
        this.visitors = visitors;
    }

    @Override
    public ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> get() {
        return this.visitors.<Void>
                templateMethodSourceVisitorBuilder(
                this.methodFactory,
                this.template,
                this.parameterResolver
        ).build();
    }

    @Override
    public String toString() {
        return "RemoveAllMethodFactoryProvider{" +
                "template=" + this.template +
                ", methodFactory=" + this.methodFactory +
                ", parameterResolver=" + this.parameterResolver +
                ", visitors=" + this.visitors +
                "}";
    }
}
