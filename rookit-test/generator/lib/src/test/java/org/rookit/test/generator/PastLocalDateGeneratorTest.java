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

import com.google.inject.Inject;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.categories.UnitTest;
import org.rookit.test.generator.number.IntegerGenerator;

import java.time.Month;
import java.time.Year;
import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
@UnitTest
@MockitoSettings(strictness = Strictness.LENIENT) // as there are stubs that will not always be required.
public class PastLocalDateGeneratorTest extends AbstractUnitTest<PastLocalDateGenerator>
        implements GeneratorTest<PastLocalDateGenerator> {

    private static final int YEAR_MIN = 1700;
    private static final int YEAR_MAX = Year.now().getValue();
    private static final int YEAR = 1750;
    public static final Month MONTH = Month.APRIL;

    @Mock
    private IntegerGenerator integerGenerator;

    @Mock
    private Generator<Month> monthGenerator;

    @Inject
    private Failsafe failsafe;

    @Inject
    private Random random;

    @Override
    protected PastLocalDateGenerator doCreateTestResource() {
        when(this.integerGenerator.createRandomInteger(YEAR_MIN, YEAR_MAX)).thenReturn(YEAR);
        when(this.integerGenerator.createRandomInteger(1, MONTH.length(Year.isLeap(YEAR)))).thenReturn(1);
        when(this.monthGenerator.createRandom()).thenReturn(MONTH);

        return new PastLocalDateGenerator(this.failsafe, this.random,
                this.integerGenerator, this.monthGenerator, YEAR_MIN);
    }

    @Override
    public void verifyCreateRandomDependencies() {
        // creates year
        verify(this.integerGenerator).createRandomInteger(YEAR_MIN, YEAR_MAX);
        // creates month
        verify(this.monthGenerator).createRandom();
        //creates day
        verify(this.integerGenerator).createRandomInteger(1, MONTH.length(Year.isLeap(YEAR)));
    }
}
