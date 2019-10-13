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

import com.devskiller.jfairy.Fairy;
import com.google.inject.*;
import com.google.inject.util.Modules;
import org.rookit.test.generator.enumeration.EnumGeneratorFactory;
import org.rookit.test.generator.enumeration.EnumGeneratorModule;
import org.rookit.test.generator.guice.Past;
import org.rookit.test.generator.number.NumberGeneratorModule;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

@SuppressWarnings("javadoc")
public final class BaseGeneratorModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(new BaseGeneratorModule(),
            NumberGeneratorModule.getModule(),
            EnumGeneratorModule.getModule());

    public static Module getModule() {
        return MODULE;
    }

    private BaseGeneratorModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(Random.class).toInstance(new SecureRandom());

        bind(Fairy.class).toInstance(Fairy.create());
        
        bind(new TypeLiteral<Generator<String>>() {
            }).to(FairyGenerator.class).in(Singleton.class);
        bind(FairyGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Boolean>>() {
            }).to(BooleanGeneratorImpl.class).in(Singleton.class);
        bind(BooleanGeneratorImpl.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<byte[]>>() {
            }).to(ByteArrayGeneratorImpl.class).in(Singleton.class);
        bind(ByteArrayGeneratorImpl.class).in(Singleton.class);
        
        bindTime();
    }

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    private void bindTime() {
        bind(new TypeLiteral<Generator<Duration>>() {
            }).to(DurationGenerator.class).in(Singleton.class);
        bind(DurationGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<LocalDate>>() {}).annotatedWith(Past.class).to(PastLocalDateGenerator.class)
                .in(Singleton.class);
        bind(PastLocalDateGenerator.class).annotatedWith(Past.class)
                .to(PastLocalDateGenerator.class).in(Singleton.class);
    }
    
    @SuppressWarnings("MethodMayBeStatic")
    @Provides
    private Generator<Month> getMonthGenerator(final EnumGeneratorFactory enumFactory) {
        return enumFactory.create(Month.class);
    }

}
