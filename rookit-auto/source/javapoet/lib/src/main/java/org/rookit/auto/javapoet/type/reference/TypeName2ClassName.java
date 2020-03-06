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
package org.rookit.auto.javapoet.type.reference;

import com.google.inject.Inject;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

final class TypeName2ClassName {

    private final OptionalFactory optionalFactory;

    @Inject
    private TypeName2ClassName(final OptionalFactory optionalFactory) {
        this.optionalFactory = optionalFactory;
    }

    Optional<ClassName> extractClass(final TypeName typeName) {

        if (typeName instanceof ClassName) {
            return this.optionalFactory.of((ClassName) typeName);
        }

        if (typeName instanceof ParameterizedTypeName) {
            return this.optionalFactory.of(((ParameterizedTypeName) typeName).rawType);
        }

        if (typeName instanceof ArrayTypeName) {
            return extractClass(((ArrayTypeName) typeName).componentType);
        }

        return this.optionalFactory.empty();
    }

}
