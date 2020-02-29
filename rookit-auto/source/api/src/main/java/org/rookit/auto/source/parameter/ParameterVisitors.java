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
package org.rookit.auto.source.parameter;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.ExtendedElementVisitors;

import java.util.function.Function;

public interface ParameterVisitors extends ExtendedElementVisitors {

    <B extends GenericParameterBuilder<B, V, P>, V extends ExtendedElementVisitor<StreamEx<ParameterSource>, P>, P>
    B parameterBuilder(V visitor, Function<ExtendedElementVisitor<StreamEx<ParameterSource>, P>, V> downcastAdapter);

    default <B extends GenericParameterBuilder<B, ExtendedElementVisitor<StreamEx<ParameterSource>, P>, P>, P>
    B parameterBuilder(final ExtendedElementVisitor<StreamEx<ParameterSource>, P> visitor) {
        return parameterBuilder(visitor, element -> element);
    }

}
