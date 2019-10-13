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
package org.rookit.auto;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;
import org.rookit.utils.guice.Dummy;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class DummyJavaFileObject implements JavaFileObject {

    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final Reader reader;
    private final Writer writer;

    @Inject
    private DummyJavaFileObject(@Dummy final InputStream inputStream,
                                @Dummy final OutputStream outputStream,
                                @Dummy final Reader reader,
                                @Dummy final Writer writer) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Kind getKind() {
        return Kind.OTHER;
    }

    @Override
    public boolean isNameCompatible(final String simpleName, final Kind kind) {
        return false;
    }

    @Override
    public NestingKind getNestingKind() {
        return NestingKind.TOP_LEVEL;
    }

    @Override
    public Modifier getAccessLevel() {
        return Modifier.PRIVATE;
    }

    @Override
    public URI toUri() {
        return null;
    }

    @Override
    public String getName() {
        return EMPTY;
    }

    @Override
    public InputStream openInputStream() {
        return this.inputStream;
    }

    @Override
    public OutputStream openOutputStream() {
        return this.outputStream;
    }

    @Override
    public Reader openReader(final boolean ignoreEncodingErrors) {
        return this.reader;
    }

    @Override
    public CharSequence getCharContent(final boolean ignoreEncodingErrors) {
        return EMPTY;
    }

    @Override
    public Writer openWriter() {
        return this.writer;
    }

    @Override
    public long getLastModified() {
        return 0;
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("inputStream", this.inputStream)
                .add("outputStream", this.outputStream)
                .add("reader", this.reader)
                .add("writer", this.writer)
                .toString();
    }
}
