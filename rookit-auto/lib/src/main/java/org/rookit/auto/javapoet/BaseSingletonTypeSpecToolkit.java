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
package org.rookit.auto.javapoet;

import com.google.common.base.MoreObjects;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.rookit.auto.javapoet.type.SingletonTypeSpecToolkit;

import javax.lang.model.element.Modifier;
import java.util.function.Function;

public final class BaseSingletonTypeSpecToolkit implements SingletonTypeSpecToolkit {

    public static SingletonTypeSpecToolkit create(final String fieldName) {
        return new BaseSingletonTypeSpecToolkit(fieldName);
    }

    private final String fieldName;
    private final MethodSpec constructor;

    private BaseSingletonTypeSpecToolkit(final String fieldName) {
        this.fieldName = fieldName;
        this.constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    @Override
    public MethodSpec constructor() {
        return this.constructor;
    }

    @Override
    public FieldSpec field(final ClassName type, final Function<String, CodeBlock> initializer) {
        return FieldSpec.builder(type, this.fieldName, Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer(initializer.apply(this.fieldName))
                .build();
    }

    @Override
    public MethodSpec method(final ClassName type, final String methodName) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(type)
                .addStatement("return $S", this.fieldName)
                .build();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fieldName", this.fieldName)
                .add("constructor", this.constructor)
                .toString();
    }
}
