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
package org.rookit.test.generator;

import com.google.common.collect.ImmutableSet;
import one.util.streamex.StreamEx;
import org.rookit.failsafe.Failsafe;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

@SuppressWarnings("javadoc")
public abstract class AbstractGenerator<T> implements Generator<T> {
    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AbstractGenerator.class);

    private static final int MAX_SIZE = 10;

    private final Failsafe failsafe;
    @SuppressWarnings("FieldNotUsedInToString") // no point
    private final Random random;

    protected AbstractGenerator(final Failsafe failsafe, final Random random) {
        this.failsafe = failsafe;
        this.random = random;
    }

    @Override
    public List<T> createRandomList() {
        return StreamEx.generate(this)
                .limit(this.random.nextInt(MAX_SIZE) + 1)
                .toImmutableList();
    }

    @Override
    public Set<T> createRandomSet() {
        return StreamEx.generate(this)
                .limit(this.random.nextInt(MAX_SIZE) + 1)
                .toImmutableSet();
    }

    @Override
    public T createRandomUnique(final T item) {
        this.failsafe.checkArgument().isNotNull(logger, item, "item");
        return Stream.generate(this::createRandom)
                .filter(generated -> !Objects.equals(item, generated))
                .limit(MAX_SIZE)
                .findFirst()
                .orElseGet(() -> this.failsafe.handleException()
                        .runtimeException("Cannot find a unique value for: " + item));
    }

    @Override
    public Set<T> createRandomUniqueSet(final Collection<T> items) {
        this.failsafe.checkArgument().isNotNull(logger, items, "items");
        final ImmutableSet.Builder<T> uniqueSet = ImmutableSet.builder();
        for (int i = 0; i < items.size(); i++) {
            final T item = createRandom();
            if (!items.contains(item)) {
                uniqueSet.add(item);
            }
        }
        return uniqueSet.build();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        return object instanceof AbstractGenerator;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return "AbstractGenerator{" +
                "injector=" + this.failsafe +
                "}";
    }
}
