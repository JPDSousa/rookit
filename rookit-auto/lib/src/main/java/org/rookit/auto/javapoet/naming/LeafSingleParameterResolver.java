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
package org.rookit.auto.javapoet.naming;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javax.type.ExtendedTypeElement;

public final class LeafSingleParameterResolver implements JavaPoetParameterResolver {

    public static JavaPoetParameterResolver create(final JavaPoetNamingFactory parameterFactory,
                                                   final JavaPoetNamingFactory parameterizedFactory) {
        return new LeafSingleParameterResolver(parameterFactory, parameterizedFactory);
    }

    private final JavaPoetNamingFactory namingFactory;
    private final JavaPoetNamingFactory genericNamingFactory;

    private LeafSingleParameterResolver(final JavaPoetNamingFactory namingFactory,
                                        final JavaPoetNamingFactory genericNamingFactory) {
        this.namingFactory = namingFactory;
        this.genericNamingFactory = genericNamingFactory;
    }

    @Override
    public TypeName resolveParameters(final ExtendedTypeElement element, final TypeVariableName... typeVariables) {
        final ClassName className = this.namingFactory.classNameFor(element);
        final ClassName genericClassName = this.genericNamingFactory.classNameFor(element);
        return ParameterizedTypeName.get(genericClassName, className);
    }

    @Override
    public Iterable<TypeVariableName> createParameters(final ExtendedTypeElement element) {
        return ImmutableSet.of();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("namingFactory", this.namingFactory)
                .add("genericNamingFactory", this.genericNamingFactory)
                .toString();
    }
}
