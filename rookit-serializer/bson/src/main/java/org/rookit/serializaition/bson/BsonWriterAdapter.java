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
package org.rookit.serializaition.bson;

import org.bson.BsonWriter;
import org.rookit.serialization.TypeWriter;

final class BsonWriterAdapter implements TypeWriter {

    private final BsonWriter writer;

    BsonWriterAdapter(final BsonWriter writer) {
        this.writer = writer;
    }

    @Override
    public TypeWriter name(final String name) {
        this.writer.writeName(name);
        return this;
    }

    @Override
    public TypeWriter value(final boolean value) {

        this.writer.writeBoolean(value);
        return this;
    }

    @Override
    public TypeWriter value(final int value) {

        this.writer.writeInt32(value);
        return this;
    }

    @Override
    public TypeWriter value(final String value) {
        this.writer.writeString(value);
        return this;
    }

    @Override
    public TypeWriter startArray() {
        this.writer.writeStartArray();
        return this;
    }

    @Override
    public TypeWriter endArray() {
        this.writer.writeEndArray();
        return this;
    }

    @Override
    public TypeWriter startObject() {
        this.writer.writeStartDocument();
        return this;
    }

    @Override
    public TypeWriter endObject() {
        this.writer.writeEndDocument();
        return this;
    }

    @Override
    public TypeWriter markAbsent() {

        this.writer.writeNull();
        return this;
    }

}
