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
package org.rookit.auto.javapoet.parameter;

import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.javax.type.ExtendedTypeElement;

import javax.lang.model.type.TypeMirror;

import static javax.lang.model.element.Modifier.FINAL;

final class TypeNameParameterVisitor<P> implements StreamExtendedElementVisitor<ParameterSpec, P> {

    private final ParameterSpec parameter;

    TypeNameParameterVisitor(final TypeMirror mirror, final CharSequence name) {
        // TODO we should addAll an additional layer here
        this.parameter = ParameterSpec.builder(TypeName.get(mirror), name.toString(), FINAL).build();
    }

    @Override
    public StreamEx<ParameterSpec> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return StreamEx.of(this.parameter);
    }

    @Override
    public String toString() {
        return "TypeNameParameterVisitor{" +
                "parameter=" + this.parameter +
                "}";
    }
}
