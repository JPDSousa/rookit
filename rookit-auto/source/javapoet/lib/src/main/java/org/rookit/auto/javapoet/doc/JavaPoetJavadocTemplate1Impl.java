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
package org.rookit.auto.javapoet.doc;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.doc.JavadocTemplate1;

import java.lang.reflect.Type;

final class JavaPoetJavadocTemplate1Impl implements JavadocTemplate1 {

    private final String content;

    JavaPoetJavadocTemplate1Impl(final String content) {
        this.content = content;
    }

    @Override
    public String build(final Type parameter) {
        return CodeBlock.of(this.content, parameter).toString();
    }

    @Override
    public String build(final ExtendedTypeElement parameter) {
        return CodeBlock.of(this.content, ClassName.get(parameter)).toString();
    }

    @Override
    public String toString() {
        return "JavaPoetJavadocTemplate1Impl{" +
                "content='" + this.content + '\'' +
                "}";
    }
}
