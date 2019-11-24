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
package org.rookit.auto.javax.runtime.mirror.variable;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.mirror.variable.ExtendedTypeVariable;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;
import org.rookit.auto.javax.runtime.mirror.variable.node.NodeTypeVariableFactory;

final class TypeVariableFactoryImpl implements TypeVariableFactory {

    private final NodeTypeVariableFactory nodeFactory;

    @Inject
    private TypeVariableFactoryImpl(final NodeTypeVariableFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    @Override
    public Single<ExtendedTypeVariable> createFromTypeVariable(final RuntimeTypeVariableEntity typeVariable) {
        return this.nodeFactory.createMutableFromEntity(typeVariable)
                .map(node -> new TypeVariableImpl(typeVariable, node));
    }

    @Override
    public String toString() {
        return "TypeVariableFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                "}";
    }

}
