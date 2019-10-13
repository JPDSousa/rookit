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
package org.rookit.auto.javax.runtime.name;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.lang.model.element.Name;
import java.util.stream.IntStream;

final class CharSequence2Name implements Name {

    private final CharSequence content;

    CharSequence2Name(final CharSequence content) {
        this.content = content;
    }

    @Override
    public boolean contentEquals(final CharSequence cs) {
        return this.content.equals(cs.toString());
    }

    @Override
    public int length() {
        return this.content.length();
    }

    @Override
    public char charAt(final int index) {
        return this.content.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        return this.content.subSequence(start, end);
    }

    @Override
    public IntStream chars() {
        return this.content.chars();
    }

    @Override
    public IntStream codePoints() {
        return this.content.codePoints();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if ((o == null) || (getClass() != o.getClass())) return false;

        final CharSequence2Name other = (CharSequence2Name) o;

        return new EqualsBuilder()
                .append(this.content, other.content)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.content)
                .toHashCode();
    }

    @Override
    public String toString() {
        return this.content.toString();
    }
}
