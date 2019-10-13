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
package org.rookit.test.generator.enumeration;

import com.google.inject.Inject;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.generator.Generator;

import java.util.Random;

final class EnumGeneratorFactoryImpl implements EnumGeneratorFactory {

    @SuppressWarnings("FieldNotUsedInToString") // useless
    private final Random random;
    private final Failsafe failsafe;

    @Inject
    EnumGeneratorFactoryImpl(final Random random, final Failsafe failsafe) {
        this.random = random;
        this.failsafe = failsafe;
    }

    @Override
    public <T extends Enum<T>> Generator<T> create(final Class<T> clazz) {
        return new EnumGenerator<>(this.failsafe, this.random, clazz);
    }

    @Override
    public String toString() {
        return "EnumGeneratorFactoryImpl{" +
                ", injector=" + this.failsafe +
                "}";
    }
}
