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

import com.google.inject.Inject;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

@SuppressWarnings("javadoc")
final class BagUtilsImpl implements BagUtils {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BagUtilsImpl.class);

    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    @Inject
    private BagUtilsImpl(final Failsafe failsafe, final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public <T> Optional<T> getHighestCount(final Bag<T> bag) {
        int max = 0;
        T maxElement = null;

        for (final T element : bag) {
            final int count = bag.getCount(element);
            if (Objects.isNull(maxElement) || (count > max)) {
                maxElement = element;
                max = count;
            }
        }

        return this.optionalFactory.fromJavaOptional(bag.stream()
                .max(Comparator.comparingInt(bag::getCount)));
    }

    @Override
    public <T> Bag<T> newHashBag(final T initial) {
        this.failsafe.checkArgument().isNotNull(logger, this.failsafe, "initial");
        return new HashBag<>(Collections.singleton(initial));
    }

    @Override
    public <T> Bag<T> unmodifiableBag(final Bag<T> bag) {
        this.failsafe.checkArgument().isNotNull(logger, bag, "bag");
        //noinspection InstanceofConcreteClass
        if (bag instanceof UnmodifiableBag) {
            return bag;
        }

        return new UnmodifiableBag<>(this.failsafe, bag);
    }

    @Override
    public String toString() {
        return "BagUtilsImpl{" +
                "injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
