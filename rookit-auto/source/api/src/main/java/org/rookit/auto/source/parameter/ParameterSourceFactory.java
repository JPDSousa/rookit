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
package org.rookit.auto.source.parameter;

import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import static com.google.common.collect.ImmutableList.toImmutableList;

public interface ParameterSourceFactory {

    MutableParameterSource makeMutable(ParameterSource source);

    MutableParameterSource createMutable(CharSequence name, TypeReferenceSource type);

    MutableParameterSource createMutable(CharSequence name, ExtendedTypeMirror type);

    MutableParameterSource createMutable(CharSequence name, Class<?> type);

    MutableParameterSource createFromElement(ExtendedVariableElement variable);

    MutableParameterSource createFromField(FieldSource field);

    default Iterable<MutableParameterSource> parametersFor(final ExtendedExecutableElement method) {
        return method.getParameters().stream()
                .map(this::createFromElement)
                .collect(toImmutableList());
    }

}
