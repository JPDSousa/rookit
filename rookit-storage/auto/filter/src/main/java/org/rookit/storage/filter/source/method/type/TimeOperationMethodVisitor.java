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

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.property.Property;
import org.rookit.storage.filter.source.guice.Time;
import org.rookit.utils.string.template.Template1;

import java.util.Collection;
import java.util.Set;

final class TimeOperationMethodVisitor<P> implements ConventionTypeElementVisitor<StreamEx<MethodSource>, P>,
        StreamExtendedElementVisitor<MethodSource, P> {

    private final MethodSourceFactory methodFactory;
    private final ParameterSourceFactory parameterFactory;
    private final Collection<Template1> templates;

    @Inject
    private TimeOperationMethodVisitor(
            final MethodSourceFactory methodFactory,
            final ParameterSourceFactory parameterFactory,
            @Time final Set<Template1> templates) {
        this.methodFactory = methodFactory;
        this.parameterFactory = parameterFactory;
        this.templates = templates;
    }

    private StreamEx<MethodSource> create(final Property property) {
        final String propertyName = property.name();
        final ParameterSource param = this.parameterFactory.createMutable(propertyName, property.type());

        return StreamEx.of(this.templates)
                .map(template -> template.build(propertyName))
                .map(name -> this.methodFactory.createMutableMethod(propertyName)
                        .addParameter(param));
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(final ConventionTypeElement element, final P parameter) {
        return StreamEx.of(element.properties())
                .flatMap(this::create);
    }

    @Override
    public String toString() {
        return "TimeOperationMethodVisitor{" +
                "methodFactory=" + this.methodFactory +
                ", parameterFactory=" + this.parameterFactory +
                ", templates=" + this.templates +
                "}";
    }

}
