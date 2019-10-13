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

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

final class ClassToInstanceMultiMapFactoryImpl implements ClassToInstanceMultiMapFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ClassToInstanceMultiMapFactoryImpl.class);

    private final Failsafe failsafe;

    @Inject
    private ClassToInstanceMultiMapFactoryImpl(final Failsafe failsafe) {
        this.failsafe = failsafe;
    }

    @Override
    public ClassToInstanceMultiMap fromGuavaMultiMap(final Multimap<Class<?>, ?> multiMap) {
        this.failsafe.checkArgument().isNotNull(logger, multiMap, "multiMap");
        for (final Class<?> clazz : multiMap.keySet()) {
            final List<Class<?>> invalidTypes = multiMap.get(clazz)
                    .stream()
                    .map(Object::getClass)
                    .filter(Predicates.not(clazz::isAssignableFrom))
                    .collect(ImmutableList.toImmutableList());
            if (!invalidTypes.isEmpty()) {
                final String errMsg = String.format("Found entries that are neither types or subtypes of '%s': %s",
                                                    clazz, invalidTypes);
                throw new IllegalArgumentException(errMsg);
            }
        }

        return new GuavaClassToInstanceMultiMap(this.failsafe, multiMap);
    }

    @Override
    public String toString() {
        return "ClassToInstanceMultiMapFactoryImpl{" +
                "failsafe=" + this.failsafe +
                "}";
    }

}
