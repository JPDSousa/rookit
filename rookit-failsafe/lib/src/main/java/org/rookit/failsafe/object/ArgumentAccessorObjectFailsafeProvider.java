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

import com.google.inject.Inject;
import com.google.inject.Provider;
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
import org.rookit.failsafe.guice.Argument;
import org.rookit.utils.source.Prototype;

@Prototype
@SuppressWarnings("QuestionableName") // string is consistent with the current naming convention
final class ArgumentAccessorObjectFailsafeProvider implements Provider<AccessorObjectFailsafe> {
    
    private final MapFailsafe map;
    private final OptionalFailsafe optional;
    private final CollectionFailsafe collection;
    private final ArrayFailsafe array;
    private final ComparableFailsafe comparable;
    private final StringFailsafe string;
    private final NumberFailsafe number;
    private final TimeFailsafe time;
    private final ObjectFailsafe objectFailsafe;

    @Inject
    private ArgumentAccessorObjectFailsafeProvider(@Argument final MapFailsafe map,
                                                   @Argument final OptionalFailsafe optional,
                                                   @Argument final CollectionFailsafe collection,
                                                   @Argument final ArrayFailsafe array,
                                                   @Argument final ComparableFailsafe comparable,
                                                   @Argument final StringFailsafe string,
                                                   @Argument final NumberFailsafe number,
                                                   @Argument final TimeFailsafe time,
                                                   @Argument final ObjectFailsafe objectFailsafe) {
        this.map = map;
        this.optional = optional;
        this.collection = collection;
        this.array = array;
        this.comparable = comparable;
        this.string = string;
        this.number = number;
        this.time = time;
        this.objectFailsafe = objectFailsafe;
    }

    @Override
    public AccessorObjectFailsafe get() {
        return new BaseAccessorObjectFailsafe(
                this.map,
                this.optional,
                this.collection,
                this.array,
                this.comparable,
                this.string,
                this.number,
                this.time,
                this.objectFailsafe
        );
    }

    @Override
    public String toString() {
        return "ArgumentAccessorObjectFailsafeProvider{" +
                "map=" + this.map +
                ", optional=" + this.optional +
                ", collection=" + this.collection +
                ", array=" + this.array +
                ", comparable=" + this.comparable +
                ", string=" + this.string +
                ", number=" + this.number +
                ", time=" + this.time +
                ", objectFailsafe=" + this.objectFailsafe +
                "}";
    }
}
