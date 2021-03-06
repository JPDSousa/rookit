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
package org.rookit.guice.auto.annotation;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

final class TargetProvider implements Provider<AnnotationSource> {

    private final AnnotationSourceFactory factory;
    private final ArbitraryCodeSourceFactory codeSourceFactory;

    @Inject
    private TargetProvider(
            final AnnotationSourceFactory factory,
            final ArbitraryCodeSourceFactory codeSourceFactory) {
        this.factory = factory;
        this.codeSourceFactory = codeSourceFactory;
    }

    @Override
    public AnnotationSource get() {
        final ArbitraryCodeSource target = this.codeSourceFactory.createFromFormat("{$T.$L, $T.$L, $T.$L}",
                                                                                   ImmutableList.of(
                                                                                           ElementType.class, FIELD,
                                                                                           ElementType.class, METHOD,
                                                                                           ElementType.class, PARAMETER
                                                                                   ));
        return this.factory.createMutable(Target.class)
                .addMember("value", target);
    }

    @Override
    public String toString() {
        return "TargetProvider{" +
                "factory=" + this.factory +
                ", codeSourceFactory=" + this.codeSourceFactory +
                "}";
    }

}
