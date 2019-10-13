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

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.rookit.test.generator.Generator;
import org.rookit.test.generator.guice.Positive;

public final class NumberGeneratorModule extends AbstractModule {

    private static final Module MODULE = new NumberGeneratorModule();

    public static Module getModule() {
        return MODULE;
    }

    private NumberGeneratorModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(new TypeLiteral<Generator<Short>>() {}).to(ShortGeneratorImpl.class).in(Singleton.class);
        bind(ShortGenerator.class).to(ShortGeneratorImpl.class).in(Singleton.class);
        bind(ShortGeneratorImpl.class).in(Singleton.class);

        bind(new TypeLiteral<Generator<Double>>() {}).to(DoubleGeneratorImpl.class).in(Singleton.class);
        bind(DoubleGenerator.class).to(DoubleGeneratorImpl.class).in(Singleton.class);
        bind(DoubleGeneratorImpl.class).in(Singleton.class);

        bind(new TypeLiteral<Generator<Integer>>() {}).to(IntegerGeneratorImpl.class).in(Singleton.class);
        bind(IntegerGenerator.class).to(IntegerGeneratorImpl.class).in(Singleton.class);
        bind(IntegerGeneratorImpl.class).in(Singleton.class);

        bind(new TypeLiteral<Generator<Long>>() {}).annotatedWith(Positive.class).to(PositiveLongGenerator.class)
                .in(Singleton.class);
        bind(LongGenerator.class).annotatedWith(Positive.class).to(PositiveLongGenerator.class).in(Singleton.class);
        bind(PositiveLongGenerator.class).annotatedWith(Positive.class).to(PositiveLongGenerator.class)
                .in(Singleton.class);

        bind(new TypeLiteral<Generator<Long>>() {}).to(LongGeneratorImpl.class).in(Singleton.class);
        bind(LongGenerator.class).to(LongGeneratorImpl.class).in(Singleton.class);
        bind(LongGeneratorImpl.class).in(Singleton.class);
    }
}
