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
package org.rookit.auto.javax.runtime.mirror.executable;

import org.rookit.auto.javax.runtime.mirror.executable.node.MutableNodeExecutableType;

import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import java.util.List;

public interface RuntimeExecutableType extends ExecutableType, MutableNodeExecutableType {

    @Override
    default List<? extends TypeVariable> getTypeVariables() {
        return typeVariables();
    }

    @Override
    default TypeMirror getReturnType() {
        return returnType();
    }

    @Override
    default List<? extends TypeMirror> getParameterTypes() {
        return parameterTypes();
    }

    @Override
    default TypeMirror getReceiverType() {
        return receiverType();
    }

    @Override
    default List<? extends TypeMirror> getThrownTypes() {
        return thrownTypes();
    }

    @Override
    default <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        return v.visitExecutable(this, p);
    }

    @Override
    default TypeKind getKind() {
        return TypeKind.EXECUTABLE;
    }

}
