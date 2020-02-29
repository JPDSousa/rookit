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
package org.rookit.convention.auto.javapoet;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.property.Property;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import java.util.function.BiFunction;

final class TypeTransformation implements BiFunction<ConventionTypeElement, Property, StreamEx<MethodSource>> {

    private static final String ERR_MSG = "This transformation function requires all properties to declare a declared" +
            " type";

    private final MethodSourceFactory methodFactory;
    private final ParameterSourceFactory parameterFactory;

    @Inject
    TypeTransformation(
            final MethodSourceFactory methodFactory,
            final ParameterSourceFactory parameterFactory) {
        this.methodFactory = methodFactory;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public StreamEx<MethodSource> apply(final ConventionTypeElement owner, final Property property) {

        final ExtendedTypeMirror propertyType = property.type();
        final Element propertyElement = propertyType
                .toElement()
                .orElseThrow(() -> new IllegalArgumentException(ERR_MSG));

        final Name elementName = propertyElement.getSimpleName();
        final ParameterSource parameter = this.parameterFactory.createMutable(elementName, propertyType);

        return StreamEx.of(
                this.methodFactory.createMutableMethod(elementName)
                .addParameter(parameter)
        );
    }

    @Override
    public String toString() {
        return "TypeTransformation{" +
                "methodFactory=" + this.methodFactory +
                ", parameterFactory=" + this.parameterFactory +
                "}";
    }

}
