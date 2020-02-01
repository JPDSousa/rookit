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
package org.rookit.auto.javapoet;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.squareup.javapoet.AnnotationSpec;
import org.rookit.auto.source.type.annotation.AnnotationSourceAdapter;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;

final class JavaPoetMutableAnnotatableFactoryImpl implements JavaPoetMutableAnnotatableFactory {

    private final AnnotationSourceFactory factory;
    private final AnnotationSourceAdapter<AnnotationSpec> adapter;

    @Inject
    private JavaPoetMutableAnnotatableFactoryImpl(
            final AnnotationSourceFactory factory,
            final AnnotationSourceAdapter<AnnotationSpec> adapter) {
        this.factory = factory;
        this.adapter = adapter;
    }

    @Override
    public JavaPoetMutableAnnotatable createEmpty() {

        return new JavaPoetMutableAnnotatableImpl(this.factory, this.adapter, ImmutableList.of()) ;
    }

    @Override
    public String toString() {
        return "JavaPoetMutableAnnotatableFactoryImpl{" +
                "factory=" + this.factory +
                ", adapter=" + this.adapter +
                "}";
    }

}
