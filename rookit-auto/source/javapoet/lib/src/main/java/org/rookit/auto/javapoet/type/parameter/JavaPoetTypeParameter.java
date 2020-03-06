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
package org.rookit.auto.javapoet.type.parameter;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;

import static java.util.Arrays.copyOf;

final class JavaPoetTypeParameter implements TypeParameterSource {

    private final OptionalFactory optionalFactory;
    private final ClassName className;
    private final TypeName[] arguments;

    JavaPoetTypeParameter(
            final OptionalFactory optionalFactory,
            final ClassName className,
            final TypeName[] arguments) {
        this.optionalFactory = optionalFactory;
        this.className = className;
        this.arguments = copyOf(arguments, arguments.length);
    }

    ParameterizedTypeName getJavaPoet() {
        return ParameterizedTypeName.get(this.className, this.arguments);
    }

    @Override
    public Optional<String> packageName() {
        return this.optionalFactory.of(this.className.packageName());
    }

}
