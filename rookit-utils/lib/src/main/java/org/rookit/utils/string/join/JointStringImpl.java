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
package org.rookit.utils.string.join;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.List;

final class JointStringImpl implements JointString {

    private final Joiner joiner;
    private final List<String> items;
    private final JointStringFactory factory;

    JointStringImpl(final Joiner joiner, final Collection<String> items, final JointStringFactory factory) {
        this.joiner = joiner;
        this.items = ImmutableList.copyOf(items);
        this.factory = factory;
    }

    @Override
    public List<String> asList() {
        return this.items;
    }

    @Override
    public String asString() {
        return this.joiner.join(this.items);
    }

    @Override
    public JointString resolve(final String item) {
        return this.factory.create(this.items, item);
    }

    @Override
    public JointString resolve(final JointString jointString) {
        return this.factory.create(
                ImmutableList.<String>builder()
                        .addAll(asList())
                        .addAll(jointString.asList())
                        .build()
        );
    }

    @Override
    public int length() {
        return this.items.size();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if ((o == null) || (getClass() != o.getClass())) return false;

        final JointStringImpl other = (JointStringImpl) o;

        return new EqualsBuilder()
                .append(this.items, other.items)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.items)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "JointStringImpl{" +
                "joiner=" + this.joiner +
                ", items=" + this.items +
                ", factory=" + this.factory +
                "}";
    }
}
