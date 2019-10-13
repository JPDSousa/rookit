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

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.auto.source.guice.JavadocTemplate;
import org.rookit.utils.object.DynamicObject;
import org.rookit.utils.registry.Registry;

final class JavaPoetJavadocTemplate1Registry implements Registry<String, JavaPoetJavadocTemplate1> {

    private static final String TEXT = "text";

    private final Registry<String, DynamicObject> delegate;
    private final JavaPoetJavadocTemplate1Factory factory;

    @Inject
    private JavaPoetJavadocTemplate1Registry(@JavadocTemplate final Registry<String, DynamicObject> delegate,
                                             final JavaPoetJavadocTemplate1Factory factory) {
        this.delegate = delegate;
        this.factory = factory;
    }

    @Override
    public Maybe<JavaPoetJavadocTemplate1> get(final String key) {
        return this.delegate.get(key)
                .map(dynamicObject -> dynamicObject.getString(TEXT))
                .flatMap(this.factory::create);
    }

    @Override
    public Single<JavaPoetJavadocTemplate1> fetch(final String key) {
        return this.delegate.fetch(key)
                .map(dynamicObject -> dynamicObject.getString(TEXT))
                .flatMap(rawFormat -> this.factory.create(rawFormat).toSingle());
    }

    @Override
    public String toString() {
        return "JavaPoetJavadocTemplate1Registry{" +
                "delegate=" + this.delegate +
                ", factory=" + this.factory +
                "}";
    }

    @Override
    public void close() {
        // nothing to close
    }
}
