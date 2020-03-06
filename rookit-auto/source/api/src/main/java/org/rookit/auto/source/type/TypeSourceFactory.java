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
package org.rookit.auto.source.type;

import org.rookit.auto.source.type.reference.TypeReferenceSource;

public interface TypeSourceFactory {

    default TypeSource createClass(final TypeReferenceSource reference) {
        return createMutableClass(reference);
    }

    MutableTypeSource createMutableClass(TypeReferenceSource referenceSource);

    default TypeSource createInterface(final TypeReferenceSource reference) {
        return createMutableInterface(reference);
    }

    MutableTypeSource createMutableInterface(TypeReferenceSource reference);

    default TypeSource createAnnotation(final TypeReferenceSource reference) {
        return createMutableAnnotation(reference);
    }

    MutableTypeSource createMutableAnnotation(TypeReferenceSource reference);

    MutableTypeSource makeMutable(TypeSource typeSource);

}
