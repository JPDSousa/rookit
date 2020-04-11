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
package org.rookit.convention.auto.property;

import org.rookit.convention.auto.property.collection.ImmutableCollectionProperty;
import org.rookit.convention.auto.property.collection.MutableCollectionProperty;
import org.rookit.convention.auto.property.map.ImmutableMapProperty;
import org.rookit.convention.auto.property.map.MutableMapProperty;
import org.rookit.convention.auto.property.optional.ImmutableOptionalProperty;
import org.rookit.convention.auto.property.optional.MutableOptionalProperty;

public interface PropertyVisitor<R, P> {

    R visitImmutableCollection(ImmutableCollectionProperty collection, P parameter);

    R visitImmutableMap(ImmutableMapProperty map, P parameter);

    R visitImmutableOptional(ImmutableOptionalProperty optional, P parameter);

    R visitMutableCollection(MutableCollectionProperty collection, P parameter);

    R visitMutableMap(MutableMapProperty map, P parameter);

    R visitMutableOptional(MutableOptionalProperty optional, P parameter);

    R visitUnknown(Property property, P parameter);

    R visitImmutable(ImmutableProperty immutable, P param);

    R visitMutable(MutableProperty mutable, P param);

}
