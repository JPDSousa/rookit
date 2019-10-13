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
package org.rookit.failsafe;

import org.slf4j.Logger;

import java.util.Collection;

public interface CollectionFailsafe extends ObjectFailsafe {

    <T> T isEmpty(Logger logger, Collection<?> object, String name);

    <T> T isGreaterThanOrEqualTo(Logger logger, Collection<?> collection,
                                 int minimumSize,
                                 String collectionName);

    <T, E> T isNotAllThereIs(Logger logger, Collection<E> subset,
                             Collection<E> collection,
                             String collectionName);

    <T, E> T isNotContainedIn(Logger logger, E object,
                              Collection<E> collection,
                              String objectName);

    <T> T isNotEmpty(Logger logger, Collection<?> object, String name);

    <T> T isNotIntersecting(Logger logger, Collection<T> source,
                            Collection<T> target,
                            String sourceName,
                            String targetName);
}
