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
package org.rookit.auto.javax.runtime.element;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.javax.runtime.element.Registries;
import org.rookit.utils.registry.Registry;

import java.util.function.Function;

final class RegistriesImpl implements Registries {

    @Inject
    private RegistriesImpl() {}

    @Override
    public <I extends RuntimeEntity, O extends RuntimeElement> Registry<I, O> fromFactory(
            final RuntimeGenericElementFactory<I, O> factory) {
        return new FactoryRegistry<>(factory::createElement);
    }

    @Override
    public <I extends RuntimeEntity, O extends RuntimeElement> Registry<I, O> fromFunction(
            final Function<I, Single<O>> function) {
        return new FactoryRegistry<>(function);
    }

}
