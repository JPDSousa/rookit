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
package org.rookit.test.generator.number;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.generator.AbstractGenerator;

import java.util.Random;

@SuppressWarnings("javadoc")
final class IntegerGeneratorImpl extends AbstractGenerator<Integer> implements IntegerGenerator {

    private final Random random;

    @Inject
    IntegerGeneratorImpl(final Failsafe failsafe, final Random random) {
        super(failsafe, random);
        this.random = random;
    }

    @SuppressWarnings("AutoBoxing")
    @Override
    public Integer createRandom() {
        return createRandomInteger();
    }

    @Override
    public int createRandomInteger() {
        return this.random.nextInt();
    }

    @Override
    public int createRandomInteger(final int min, final int max) {
        return min + this.random.nextInt(max - min);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("random", this.random)
                .toString();
    }

    @Override
    public int getAsInt() {
        return createRandomInteger();
    }
}
