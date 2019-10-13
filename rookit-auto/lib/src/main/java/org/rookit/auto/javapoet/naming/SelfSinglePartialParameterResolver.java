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
import com.google.inject.Inject;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javax.type.ExtendedTypeElement;

public final class SelfSinglePartialParameterResolver implements JavaPoetParameterResolver {

    public static JavaPoetParameterResolver create(final JavaPoetNamingFactory namingFactory,
                                                    final TypeVariableName filterType) {
        return new SelfSinglePartialParameterResolver(namingFactory, filterType);
    }

    private final JavaPoetNamingFactory namingFactory;
    private final TypeVariableName type;

    @Inject
    private SelfSinglePartialParameterResolver(final JavaPoetNamingFactory namingFactory,
                                               final TypeVariableName type) {
        this.namingFactory = namingFactory;
        this.type = type;
    }

    @Override
    public TypeName resolveParameters(final ExtendedTypeElement element, final TypeVariableName... typeVariables) {
        final TypeVariableName typeVariable = (typeVariables.length > 0) ? typeVariables[0] : this.type;
        return ParameterizedTypeName.get(this.namingFactory.classNameFor(element), typeVariable);
    }

    @Override
    public Iterable<TypeVariableName> createParameters(final ExtendedTypeElement element) {
        final TypeName type = ParameterizedTypeName.get(this.namingFactory.classNameFor(element), this.type);
        return ImmutableSet.of(TypeVariableName.get(this.type.name, type));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("namingFactory", this.namingFactory)
                .add("type", this.type)
                .toString();
    }
}
