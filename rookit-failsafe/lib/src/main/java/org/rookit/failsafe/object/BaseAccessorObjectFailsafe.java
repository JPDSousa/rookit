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
package org.rookit.failsafe.object;

import org.rookit.failsafe.AccessorObjectFailsafe;
import org.rookit.failsafe.ArrayFailsafe;
import org.rookit.failsafe.CollectionFailsafe;
import org.rookit.failsafe.ComparableFailsafe;
import org.rookit.failsafe.MapFailsafe;
import org.rookit.failsafe.NumberFailsafe;
import org.rookit.failsafe.ObjectFailsafe;
import org.rookit.failsafe.OptionalFailsafe;
import org.rookit.failsafe.StringFailsafe;
import org.rookit.failsafe.TimeFailsafe;

@SuppressWarnings("QuestionableName") // string is consistent with the naming of other accessors
final class BaseAccessorObjectFailsafe extends DelegateObjectFailsafe implements AccessorObjectFailsafe {

    private final MapFailsafe map;
    private final OptionalFailsafe optional;
    private final CollectionFailsafe collection;
    private final ArrayFailsafe array;
    private final ComparableFailsafe comparable;
    private final StringFailsafe string;
    private final NumberFailsafe number;
    private final TimeFailsafe time;

    BaseAccessorObjectFailsafe(final MapFailsafe map,
                               final OptionalFailsafe optional,
                               final CollectionFailsafe collection,
                               final ArrayFailsafe array,
                               final ComparableFailsafe comparable,
                               final StringFailsafe string,
                               final NumberFailsafe number,
                               final TimeFailsafe time,
                               final ObjectFailsafe delegate) {
        super(delegate);
        this.map = map;
        this.optional = optional;
        this.collection = collection;
        this.array = array;
        this.comparable = comparable;
        this.string = string;
        this.number = number;
        this.time = time;
    }

    @Override
    public MapFailsafe map() {
        return this.map;
    }

    @Override
    public OptionalFailsafe optional() {
        return this.optional;
    }

    @Override
    public CollectionFailsafe collection() {
        return this.collection;
    }

    @Override
    public ComparableFailsafe comparable() {
        return this.comparable;
    }

    @Override
    public ArrayFailsafe array() {
        return this.array;
    }

    @Override
    public StringFailsafe string() {
        return this.string;
    }

    @Override
    public NumberFailsafe number() {
        return this.number;
    }

    @Override
    public TimeFailsafe time() {
        return this.time;
    }

    @Override
    public String toString() {
        return "BaseAccessorObjectFailsafe{" +
                "map=" + this.map +
                ", optional=" + this.optional +
                ", collection=" + this.collection +
                ", array=" + this.array +
                ", comparable=" + this.comparable +
                ", string=" + this.string +
                ", number=" + this.number +
                ", time=" + this.time +
                "}";
    }
}
