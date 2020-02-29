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
package org.rookit.convention.property.source.javapoet;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.convention.property.guice.PropertyModel;

final class PropertiesJavaPoetMethodVisitor<P> implements StreamExtendedElementVisitor<MethodSource, P> {

    private final MethodSourceFactory methodSourceFactory;
    private final ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterFactory;

    @Inject
    private PropertiesJavaPoetMethodVisitor(
            final MethodSourceFactory methodSourceFactory,
            @PropertyModel final ExtendedElementVisitor<StreamEx<ParameterSource>, P> parameterFactory) {
        this.methodSourceFactory = methodSourceFactory;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public StreamEx<MethodSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return extendedType.accept(this.parameterFactory, parameter)
                // TODO this filter is hammerish
                .filter(ParameterSource::isToBeUsedInSuperclass)
                .map(this::createGetter);
    }

    private MethodSource createGetter(final ParameterSource parameter) {
        return this.methodSourceFactory.createMutableMethod(parameter.name())
                .makePublic()
                .override()
                .returnInstanceField(parameter.type(), parameter.name());
    }

    @Override
    public String toString() {
        return "PropertiesJavaPoetMethodVisitor{" +
                "methodSourceFactory=" + this.methodSourceFactory +
                ", parameterFactory=" + this.parameterFactory +
                "}";
    }

}
