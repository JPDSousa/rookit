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
package org.rookit.utils.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;

final class GuavaClassToInstanceMultiMap implements ClassToInstanceMultiMap {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GuavaClassToInstanceMultiMap.class);

    private final Failsafe failsafe;
    private final Multimap<Class<?>, ?> multiMap;

    GuavaClassToInstanceMultiMap(final Failsafe failsafe, final Multimap<Class<?>, ?> multiMap) {
        this.failsafe = failsafe;
        this.multiMap = ImmutableMultimap.copyOf(multiMap);
    }

    @Override
    public <T> Collection<T> get(final Class<T> clazz) {
        this.failsafe.checkArgument().isNotNull(logger, clazz, "key class");
        final Collection<?> rawValues = this.multiMap.get(clazz);

        if (Objects.isNull(rawValues)) {
            logger.trace("No instances of class '{}'. Returning empty list.", clazz);
            return ImmutableList.of();
        }

        final ImmutableList.Builder<T> builder = ImmutableList.builder();
        for (final Object rawValue : rawValues) {
            if (!clazz.isAssignableFrom(rawValue.getClass())) {
                final String errMsg = String.format("Instance expected to be a of type or subtype of '%s', but is of "
                                                            + "type '%s'", clazz, rawValue.getClass());
                throw new IllegalStateException(errMsg);
            }
            //noinspection unchecked
            builder.add((T) rawValue);
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return "GuavaClassToInstanceMultiMap{" +
                "failsafe=" + this.failsafe +
                ", multiMap=" + this.multiMap +
                "}";
    }

}
