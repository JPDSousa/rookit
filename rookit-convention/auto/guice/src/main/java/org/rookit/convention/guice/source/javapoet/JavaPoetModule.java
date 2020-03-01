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
package org.rookit.convention.guice.source.javapoet;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.convention.annotation.LaConvention;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.source.type.ConventionTypeElementTypeSourceVisitors;

import java.lang.annotation.Annotation;
import java.util.Set;

@SuppressWarnings("MethodMayBeStatic")
public final class JavaPoetModule extends AbstractModule {

    private static final Module MODULE = new JavaPoetModule();

    public static Module getModule() {
        return MODULE;
    }

    private JavaPoetModule() {}

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    ConventionTypeElementVisitor<StreamEx<TypeSource>, Void> typeElementVisitor(
            final ConventionTypeElementTypeSourceVisitors visitors,
            final IdentifierFactory identifierFactory,
            @LaConvention final Set<Class<? extends Annotation>> annotations) {
        return visitors.conventionAnnotationBuilder(identifierFactory, Void.class)
                .withRecursiveVisiting(StreamEx::append)
                .filterIfAnyAnnotationPresent(annotations)
                .build();
    }
}
