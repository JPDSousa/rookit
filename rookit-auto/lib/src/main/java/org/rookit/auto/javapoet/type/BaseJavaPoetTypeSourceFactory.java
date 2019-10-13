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
package org.rookit.auto.javapoet.type;

import com.google.inject.Inject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import org.rookit.auto.javax.naming.Identifier;

import java.util.concurrent.Executor;

final class BaseJavaPoetTypeSourceFactory implements JavaPoetTypeSourceFactory {

    private final Executor executor;

    @Inject
    private BaseJavaPoetTypeSourceFactory(final Executor executor) {
        this.executor = executor;
    }

    @Override
    public JavaPoetTypeSource createClass(final Identifier identifier) {
        return fromTypeSpec(identifier, TypeSpec.classBuilder(fromIdentifier(identifier)).build());
    }

    private ClassName fromIdentifier(final Identifier identifier) {
        return ClassName.get(identifier.packageElement().getQualifiedName().toString(), identifier.name());
    }

    @Override
    public JavaPoetTypeSource createInterface(final Identifier identifier) {
        return fromTypeSpec(identifier, TypeSpec.interfaceBuilder(fromIdentifier(identifier)).build());
    }

    @Override
    public JavaPoetTypeSource createAnnotation(final Identifier identifier) {
        return fromTypeSpec(identifier, TypeSpec.annotationBuilder(fromIdentifier(identifier)).build());
    }

    @Override
    public JavaPoetTypeSource fromTypeSpec(final Identifier identifier, final TypeSpec source) {
        return new BaseTypeSource(identifier, source.toBuilder(), this.executor);
    }

    @Override
    public String toString() {
        return "BaseJavaPoetTypeSourceFactory{" +
                "executor=" + this.executor +
                "}";
    }
}
