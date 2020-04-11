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
package org.rookit.serialization.json;

import com.fasterxml.jackson.core.JsonGenerator;
import org.rookit.failsafe.Failsafe;
import org.rookit.serialization.TypeWriter;

import java.io.IOException;

final class JsonWriterAdapter implements TypeWriter {

    private final JsonGenerator writer;
    private final Failsafe failsafe;

    JsonWriterAdapter(final JsonGenerator writer, final Failsafe failsafe) {
        this.writer = writer;
        this.failsafe = failsafe;
    }

    @Override
    public TypeWriter name(final String name) {

        try {
            this.writer.writeFieldName(name);
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter value(final boolean value) {

        try {
            this.writer.writeBoolean(value);
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter value(final int value) {

        try {
            this.writer.writeNumber(value);
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter value(final String value) {

        try {
            this.writer.writeString(value);
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter startArray() {

        try {
            this.writer.writeStartArray();
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter endArray() {

        try {
            this.writer.writeEndArray();
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter startObject() {

        try {
            this.writer.writeStartObject();
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter endObject() {

        try {
            this.writer.writeEndObject();
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public TypeWriter markAbsent() {

        try {
            this.writer.writeNull();
            return this;
        } catch (final IOException e) {
            return this.failsafe.handleException().inputOutputException(e);
        }
    }

}
