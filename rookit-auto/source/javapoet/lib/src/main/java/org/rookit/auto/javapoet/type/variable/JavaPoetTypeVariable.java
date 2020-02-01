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

import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

final class JavaPoetTypeVariable implements TypeVariableSource {

    private final TypeReferenceSourceFactory referenceFactory;
    private final Collection<TypeReferenceSource> bounds;
    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final TypeVariableName typeVariable;

    JavaPoetTypeVariable(
            final TypeReferenceSourceFactory referenceFactory,
            final Collection<TypeReferenceSource> bounds,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final TypeVariableName typeVariable) {
        this.referenceFactory = referenceFactory;
        this.bounds = new ArrayList<>(bounds);
        this.referenceAdapter = referenceAdapter;
        this.typeVariable = typeVariable;
    }

    private TypeVariableSource newInstance(
            final TypeVariableName typeVariable,
            final Collection<TypeReferenceSource> bounds) {
        return new JavaPoetTypeVariable(
                this.referenceFactory,
                bounds,
                this.referenceAdapter,
                typeVariable
        );
    }

    TypeVariableName getTypeVariable() {
        return this.typeVariable;
    }

    @Override
    public TypeVariableSource withBound(final TypeReferenceSource reference) {

        final TypeName bound = this.referenceAdapter.adaptTypeReference(reference);
        final TypeVariableName boundedTypeVariable = this.typeVariable.withBounds(bound);
        final Collection<TypeReferenceSource> newBounds = new ArrayList<>(this.bounds);
        newBounds.add(reference);

        return newInstance(boundedTypeVariable, newBounds);
    }

    @Override
    public TypeVariableSource withBound(final ExtendedTypeElement reference) {

        return withBound(this.referenceFactory.create(reference));
    }

    @Override
    public CharSequence name() {
        return this.typeVariable.name;
    }

    @Override
    public Collection<TypeReferenceSource> bounds() {
        return Collections.unmodifiableCollection(this.bounds);
    }

    @Override
    public String toString() {
        return "JavaPoetTypeVariable{" +
                "referenceFactory=" + this.referenceFactory +
                ", bounds=" + this.bounds +
                ", referenceAdapter=" + this.referenceAdapter +
                ", typeVariable=" + this.typeVariable +
                "}";
    }

}
