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

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.generator.guice.MinYear;
import org.rookit.test.generator.number.IntegerGenerator;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("javadoc")
final class PastLocalDateGenerator extends AbstractGenerator<LocalDate> {


    private final IntegerGenerator integerGenerator;
    private final Generator<Month> monthGenerator;
    private final int minYear;

    @Inject
    PastLocalDateGenerator(final Failsafe failsafe,
                           final Random random,
                           final IntegerGenerator integerGenerator,
                           final Generator<Month> monthGenerator,
                           @MinYear final int minYear) {
        super(failsafe, random);
        this.integerGenerator = integerGenerator;
        this.monthGenerator = monthGenerator;
        this.minYear = minYear;
    }

    @Override
    public LocalDate createRandom() {
        final int maxYear = Year.now().getValue();
        final int year = this.integerGenerator.createRandomInteger(this.minYear, maxYear);
        final boolean isLeap = Year.isLeap(year);

        final Month month = this.monthGenerator.createRandom();
        final int dayOfMonth = this.integerGenerator.createRandomInteger(1, month.length(isLeap));
        return LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof PastLocalDateGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final PastLocalDateGenerator that = (PastLocalDateGenerator) object;
            return Objects.equals(this.monthGenerator, that.monthGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.monthGenerator);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("integerGenerator", this.integerGenerator)
                .add("monthGenerator", this.monthGenerator)
                .add("minYear", this.minYear)
                .toString();
    }
}
