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
package org.rookit.auto.javapoet.type;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.rookit.auto.source.type.MutableTypeSource;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

public abstract class AbstractJavaPoetTypeSource implements MutableTypeSource {

    private final Executor executor;

    protected AbstractJavaPoetTypeSource(final Executor executor) {
        this.executor = executor;
    }

    @Override
    public CompletableFuture<Void> writeTo(final Filer filer) {
        return CompletableFuture.runAsync(() -> writeTypeTo(filer), this.executor);
    }

    protected abstract TypeSpec typeSpec();

    private void writeTypeTo(final Filer filer) {
        try {
            JavaFile.builder(identifier().packageElement().getQualifiedName().toString(), typeSpec())
                    .build()
                    .writeTo(filer);
        } catch (final IOException e) {
            throw new CompletionException(e);
        }
    }

    @Override
    public String toString() {
        return "AbstractJavaPoetTypeSource{" +
                "executor=" + this.executor +
                "}";
    }
}
