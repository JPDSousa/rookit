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
package org.rookit.auto.source.spec.parameter;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;

final class ExtendedParameterAdapterVisitor<P> implements StreamExtendedElementVisitor<ParameterSource, P> {

    private final ParameterSourceFactory parameterFactory;
    private final ExtendedElementVisitor<StreamEx<ParameterSource>, P> visitor;
    private final boolean isSuper;

    ExtendedParameterAdapterVisitor(
            final ParameterSourceFactory parameterFactory,
            final ExtendedElementVisitor<StreamEx<ParameterSource>, P> visitor,
            final boolean isSuper) {
        this.parameterFactory = parameterFactory;
        this.visitor = visitor;
        this.isSuper = isSuper;
    }

    @Override
    public StreamEx<ParameterSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return extendedType.accept(this.visitor, parameter)
                .map(this.parameterFactory::makeMutable)
                .map(param -> param.toBeUsedInSuperclass(this.isSuper));
    }

    @Override
    public String toString() {
        return "ExtendedParameterAdapterVisitor{" +
                "parameterFactory=" + this.parameterFactory +
                ", visitor=" + this.visitor +
                ", isSuper=" + this.isSuper +
                "}";
    }

}
