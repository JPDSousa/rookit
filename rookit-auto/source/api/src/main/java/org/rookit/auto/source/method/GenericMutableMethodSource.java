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
package org.rookit.auto.source.method;

import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.type.annotation.MutableAnnotatable;
import org.rookit.auto.source.modifier.MutableModifiable;
import org.rookit.auto.source.type.variable.SupportsTypeVariable;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.util.Collection;
import java.util.List;

public interface GenericMutableMethodSource<S extends GenericMutableMethodSource<S>>
        extends MethodSource, MutableModifiable<S>, MutableAnnotatable<S>, SupportsTypeVariable<S> {

    default S addParameters(final Iterable<? extends ParameterSource> parameters) {
        parameters.forEach(this::addParameter);
        return self();
    }

    S addParameter(ParameterSource parameter);

    default S assignParametersToFields(final Iterable<ParameterSource> parameters) {

        parameters.forEach(this::assignParameterToField);
        return self();
    }

    S assignParameterToField(ParameterSource parameter);

    S returnNewObject(TypeReferenceSource objectType, Iterable<CharSequence> parameters);

    S returnNewObject(ExtendedTypeElement objectType, Iterable<CharSequence> parameters);

    S returnInstanceField(TypeReferenceSource fieldType, CharSequence fieldName);

    S returnInstanceField(ExtendedTypeMirror fieldType, CharSequence fieldName);

    default S override() {
        return addAnnotationByClass(Override.class);
    }

    S withReturnType(TypeReferenceSource reference);

    S defaultValue(String format, List<Object> args);

    S addVariable(TypeReferenceSource type, CharSequence name, String initialization, List<Object> args);

    S addStatement(String statement, List<Object> args);

    S addStatement(ArbitraryCodeSource statement);

    S varArgs(boolean varArgs);

    S addThrownType(TypeReferenceSource exceptionType);

    S addThrownType(ExtendedTypeMirror exceptionType);

    default S addThrownTypes(final Collection<? extends ExtendedTypeMirror> exceptionTypes) {
        exceptionTypes.forEach(this::addThrownType);
        return self();
    }

    S returnStaticField(TypeReferenceSource returnType, CharSequence fieldName);

}
