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
package org.rookit.auto.javapoet.type.variable;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javax.mirror.variable.ExtendedTypeVariable;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.utils.primitive.VoidUtils;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class JavaPoetTypeVariableFactory implements TypeVariableSourceFactory {

    private static final int AVG_N_BOUNDS = 1;

    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final VoidUtils voidUtils;

    @Inject
    private JavaPoetTypeVariableFactory(
            final TypeReferenceSourceFactory referenceFactory,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final VoidUtils voidUtils) {
        this.referenceFactory = referenceFactory;
        this.referenceAdapter = referenceAdapter;
        this.voidUtils = voidUtils;
    }

    private TypeVariableSource createFromJavaPoet(final TypeVariableName typeVariable) {

        return new JavaPoetTypeVariable(
                this.referenceFactory,
                Lists.newArrayListWithExpectedSize(AVG_N_BOUNDS),
                this.referenceAdapter,
                typeVariable
        );
    }

    @Override
    public TypeVariableSource createFromName(final CharSequence variableName) {

        return createFromJavaPoet(TypeVariableName.get(variableName.toString()));
    }

    @Override
    public Iterable<TypeVariableSource> createTypeVariables(final ExtendedTypeElement element) {

        return element.getTypeParameters().stream()
                .map(TypeVariableName::get)
                .map(this::createFromJavaPoet)
                .collect(toImmutableList());
    }

    @Override
    public TypeVariableSource createFromJavaX(final ExtendedTypeVariable typeVariable) {

        return createFromJavaPoet(TypeVariableName.get(typeVariable));
    }

    @Override
    public String toString() {
        return "JavaPoetTypeVariableFactory{" +
                "referenceFactory=" + this.referenceFactory +
                ", referenceAdapter=" + this.referenceAdapter +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
