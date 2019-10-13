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
package org.rookit.auto.guice;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.binder.LinkedBindingBuilder;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javapoet.identifier.JavaPoetIdentifierFactory;
import org.rookit.auto.javapoet.naming.JavaPoetNamingFactory;
import org.rookit.auto.javax.naming.NamingFactory;

import java.lang.annotation.Annotation;

public final class RookitAutoModuleTools {

    public static LinkedBindingBuilder<JavaPoetNamingFactory> bindNaming(
            final Binder binder,
            final Class<? extends Annotation> annotationClass) {
        binder.bind(NamingFactory.class).annotatedWith(annotationClass)
                .to(Key.get(JavaPoetNamingFactory.class, annotationClass)).in(Singleton.class);
        return binder.bind(JavaPoetNamingFactory.class).annotatedWith(annotationClass);
    }

    public static LinkedBindingBuilder<JavaPoetIdentifierFactory> bindIdentifierFactory(
            final Binder binder,
            final Class<? extends Annotation> annotationClass) {
        binder.bind(IdentifierFactory.class).annotatedWith(annotationClass)
                .to(Key.get(JavaPoetIdentifierFactory.class, annotationClass)).in(Singleton.class);
        return binder.bind(JavaPoetIdentifierFactory.class).annotatedWith(annotationClass);
    }


    private RookitAutoModuleTools() {
        // blocking instantiation
    }
}
