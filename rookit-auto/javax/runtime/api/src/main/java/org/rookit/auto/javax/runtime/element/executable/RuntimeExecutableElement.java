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
package org.rookit.auto.javax.runtime.element.executable;

import org.rookit.auto.javax.runtime.element.RuntimeElement;
import org.rookit.auto.javax.runtime.element.executable.node.MutableExecutableNodeElement;

import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public interface RuntimeExecutableElement extends RuntimeElement, ExecutableElement, MutableExecutableNodeElement {

    @Override
    default List<? extends TypeParameterElement> getTypeParameters() {
        return typeParameters();
    }

    @Override
    default TypeMirror asType() {
        return typeMirror();
    }

    @Override
    default  <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return v.visitExecutable(this, p);
    }

    @Override
    default TypeMirror getReturnType() {
        return returnType();
    }

    @Override
    default TypeMirror getReceiverType() {
        return receiverType();
    }

    @Override
    default List<? extends VariableElement> getParameters() {
        return parameters();
    }

    @Override
    default List<? extends TypeMirror> getThrownTypes() {
        return thrownTypes();
    }

}
