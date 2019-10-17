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
package org.rookit.auto.javax.runtime.type.parameter;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.element.type.parameter.RuntimeTypeParameterElement;
import org.rookit.auto.javax.runtime.element.type.parameter.RuntimeTypeParameterElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;

final class RuntimeTypeParameterElementFactoryImpl implements RuntimeTypeParameterElementFactory {

    private final RuntimeGenericElementFactory<RuntimeTypeVariableEntity, RuntimeTypeParameterElement> delegate;

    @Inject
    private RuntimeTypeParameterElementFactoryImpl(
            final RuntimeGenericElementFactory<RuntimeTypeVariableEntity, RuntimeTypeParameterElement> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<RuntimeTypeParameterElement> createElement(final RuntimeTypeVariableEntity entity) {
        return this.delegate.createElement(entity);
    }

    @Override
    public String toString() {
        return "RuntimeTypeParameterElementFactoryImpl{" +
                "delegate=" + this.delegate +
                "}";
    }

}
