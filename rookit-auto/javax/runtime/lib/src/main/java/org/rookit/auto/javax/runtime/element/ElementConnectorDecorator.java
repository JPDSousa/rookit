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

import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.node.MutableNodeElement;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.utils.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;

final class ElementConnectorDecorator<I extends RuntimeEntity, O extends Element & MutableNodeElement>
        implements RuntimeGenericElementFactory<I, O> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ElementConnectorDecorator.class);

    private final Registry<I, O> registry;

    ElementConnectorDecorator(final Registry<I, O> registry) {
        this.registry = registry;
    }

    @Override
    public Single<O> createElement(final I entity) {
        logger.trace("Fetching value from registry");
        return this.registry.fetch(entity);
    }

}
