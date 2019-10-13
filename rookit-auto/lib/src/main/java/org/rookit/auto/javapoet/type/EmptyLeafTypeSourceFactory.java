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

import com.google.common.base.MoreObjects;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import org.rookit.auto.javapoet.naming.JavaPoetParameterResolver;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;

import javax.lang.model.element.Modifier;

public final class EmptyLeafTypeSourceFactory implements SingleTypeSourceFactory {

    public static SingleTypeSourceFactory create(final JavaPoetTypeSourceFactory adapter,
                                                 final JavaPoetParameterResolver parameterResolver) {
        return new EmptyLeafTypeSourceFactory(adapter, parameterResolver);
    }

    private final JavaPoetTypeSourceFactory adapter;
    private final JavaPoetParameterResolver parameterResolver;

    private EmptyLeafTypeSourceFactory(final JavaPoetTypeSourceFactory adapter,
                                       final JavaPoetParameterResolver parameterResolver) {
        this.adapter = adapter;
        this.parameterResolver = parameterResolver;
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {
        final ClassName className = ClassName.get(
                identifier.packageElement().getQualifiedName().toString(),
                identifier.name()
        );

        final TypeSpec.Builder spec = TypeSpec.interfaceBuilder(className)
                .addSuperinterface(this.parameterResolver.resolveParameters(element))
                .addModifiers(Modifier.PUBLIC);
        return this.adapter.fromTypeSpec(identifier, spec.build());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("adapter", this.adapter)
                .add("parameterResolver", this.parameterResolver)
                .toString();
    }
}
