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

import com.google.common.base.MoreObjects;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.generator.AbstractGenerator;

import javax.annotation.Generated;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("javadoc")
public final class EnumGenerator<T extends Enum<T>> extends AbstractGenerator<T> {

    private final Class<T> clazz;
    private final Random random;

    EnumGenerator(final Failsafe failsafe, final Random random, final Class<T> clazz) {
        super(failsafe, random);
        this.random = random;
        this.clazz = clazz;
    }

    @Override
    public T createRandom() {
        final T[] values = this.clazz.getEnumConstants();
        final int index = this.random.nextInt(values.length);

        return values[index];
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof EnumGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final EnumGenerator<?> that = (EnumGenerator<?>) object;
            return Objects.equals(this.clazz, that.clazz);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.clazz);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clazz", this.clazz)
                .add("random", this.random)
                .toString();
    }
}
