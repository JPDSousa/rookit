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
package org.rookit.failsafe.collection;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.rookit.failsafe.CollectionFailsafe;
import org.rookit.failsafe.ObjectFailsafe;
import org.rookit.failsafe.object.DelegateObjectFailsafe;
import org.slf4j.Logger;

import java.util.Collection;

final class BaseCollectionFailsafe extends DelegateObjectFailsafe implements CollectionFailsafe {

    BaseCollectionFailsafe(final ObjectFailsafe delegate) {
        super(delegate);
    }

    @Override
    public <T> T isEmpty(final Logger logger, final Collection<?> object, final String name) {
        isNotNull(logger, object, name);
        return is(logger, object.isEmpty(), "The %s must be empty", name);
    }

    @Override
    public <T> T isGreaterThanOrEqualTo(final Logger logger,
                                        final Collection<?> collection,
                                        final int minimumSize,
                                        final String collectionName) {
        isNotNull(logger, collection, collectionName);
        //noinspection AutoBoxing
        return is(logger, collection.size() >= minimumSize, "%s must contain at least %d items.",
                collectionName,
                minimumSize);
    }

    @Override
    public <T, E> T isNotAllThereIs(final Logger logger,
                                    final Collection<E> subset,
                                    final Collection<E> collection,
                                    final String collectionName) {
        isNotNull(logger, subset, "subset");
        isNotNull(logger, collection, collectionName);
        final boolean hasSameContent = CollectionUtils.isEqualCollection(Sets.newHashSet(subset),
                Sets.newHashSet(collection));

        return is(logger, !hasSameContent, "The subset represents all elements of collection %s", collectionName);
    }

    @Override
    public <T, E> T isNotContainedIn(final Logger logger,
                                     final E object,
                                     final Collection<E> collection,
                                     final String objectName) {
        isNotNull(logger, object, objectName);
        isNotNull(logger, collection, "collection");
        return is(logger, !collection.contains(object), "Collection %s does not contain %s: %s",
                collection, objectName, object);
    }

    @Override
    public <T> T isNotEmpty(final Logger logger, final Collection<?> object, final String name) {
        isNotNull(logger, object, name);
        return is(logger, !object.isEmpty(), "The %s cannot be empty", name);
    }

    @Override
    public <T> T isNotIntersecting(final Logger logger,
                                   final Collection<T> source,
                                   final Collection<T> target,
                                   final String sourceName,
                                   final String targetName) {
        isNotNull(logger, source, sourceName);
        isNotNull(logger, target, targetName);
        final Collection<T> intersection = CollectionUtils.intersection(source, target);

        return is(logger, intersection.isEmpty(), "%s is contained both in %s and %s", intersection.toString(),
                sourceName,
                targetName);
    }
}
