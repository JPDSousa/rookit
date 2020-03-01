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
package org.rookit.storage.filter.source.method.type;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.property.Property;
import org.rookit.convention.auto.metatype.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.filter.source.guice.Between;
import org.rookit.utils.string.template.Template1;

import java.util.Collection;

final class BetweenMethodFactoryProvider implements Provider<
        ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> {

    private final MethodSourceFactory methodFactory;
    private final ParameterSourceFactory parameterFactory;
    private final Template1 template;
    private final ConventionTypeElementMethodSourceVisitors visitors;

    @Inject
    private BetweenMethodFactoryProvider(
            final MethodSourceFactory methodFactory,
            final ParameterSourceFactory parameterFactory,
            @Between final Template1 template,
            final ConventionTypeElementMethodSourceVisitors visitors) {
        this.methodFactory = methodFactory;
        this.parameterFactory = parameterFactory;
        this.template = template;
        this.visitors = visitors;
    }

    @Override
    public ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> get() {
        return this.visitors.<Void>templateMethodSourceVisitorBuilder(
                this.methodFactory,
                this.template,
                this::createParameters
        ).build();
    }

    private Collection<ParameterSource> createParameters(final Property property) {

        final ExtendedTypeMirror propertyType = property.type();

        // TODO inject me
        final ParameterSource before = this.parameterFactory.createMutable( "before", propertyType);
        final ParameterSource after = this.parameterFactory.createMutable("after", propertyType);

        return ImmutableList.of(before, after);
    }

    @Override
    public String toString() {
        return "BetweenMethodFactoryProvider{" +
                "methodFactory=" + this.methodFactory +
                ", parameterFactory=" + this.parameterFactory +
                ", template=" + this.template +
                ", visitors=" + this.visitors +
                "}";
    }

}
