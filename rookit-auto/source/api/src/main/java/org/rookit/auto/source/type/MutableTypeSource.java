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
package org.rookit.auto.source.type;

import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.MutableAnnotatable;
import org.rookit.auto.source.MutableModifiable;
import org.rookit.auto.source.SupportsTypeVariable;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;

public interface MutableTypeSource extends TypeSource, MutableModifiable<MutableTypeSource>,
        MutableAnnotatable<MutableTypeSource>,
        SupportsTypeVariable<MutableTypeSource> {

    MutableTypeSource addMethod(MethodSource method);

    default MutableTypeSource addMethods(final Iterable<MethodSource> methods) {
        methods.forEach(this::addMethod);
        return this;
    }

    MutableTypeSource addField(FieldSource field);

    default MutableTypeSource addFields(final Iterable<FieldSource> fields) {
        fields.forEach(this::addField);
        return this;
    }

    MutableTypeSource addJavadoc(String javadoc);

    MutableTypeSource addTypeVariable(TypeVariableSource typeVariable);

    default MutableTypeSource addTypeVariables(final Iterable<TypeVariableSource> typeVariables) {
        typeVariables.forEach(this::addTypeVariable);
        return this;
    }

    MutableTypeSource withSuperclass(TypeReferenceSource superclass);

    MutableTypeSource withParameterizedSuperclass(Class<?> parameterized, ExtendedTypeElement parameter);

    MutableTypeSource addParameterizedInterface(ExtendedTypeElement parameterized, TypeReferenceSource parameter);

    MutableTypeSource addInterface(TypeReferenceSource interfaceSource);

    default MutableTypeSource addInterfaces(final Iterable<TypeReferenceSource> interfaces) {
        interfaces.forEach(this::addInterface);
        return this;
    }



}
