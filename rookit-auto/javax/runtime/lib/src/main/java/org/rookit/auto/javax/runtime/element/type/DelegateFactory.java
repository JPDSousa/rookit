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
package org.rookit.auto.javax.runtime.element.type;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DelegateFactory implements RuntimeTypeElementFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DelegateFactory.class);

    private final RuntimeGenericElementFactory<RuntimeClassEntity, RuntimeTypeElement> delegate;

    @Inject
    private DelegateFactory(final RuntimeGenericElementFactory<RuntimeClassEntity, RuntimeTypeElement> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<RuntimeTypeElement> createElement(final RuntimeClassEntity entity) {
        logger.trace("Delegating to '{}'", this.delegate);
        return this.delegate.createElement(entity);
    }

    @Override
    public String toString() {
        return "DelegateFactory{" +
                "delegate=" + this.delegate +
                "}";
    }

}
