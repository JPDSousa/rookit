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
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import static java.lang.String.format;

final class JointStringFactoryImpl implements JointStringFactory {

    @SuppressWarnings("FieldNotUsedInToString")
    private final Joiner joiner;
    private final Pattern splitter;

    JointStringFactoryImpl(final Joiner joiner, final Pattern splitter) {
        this.joiner = joiner;
        this.splitter = splitter;
    }

    @Override
    public JointString parse(final CharSequence rawItems) {
        if (StringUtils.isBlank(rawItems)) {
            throw  new IllegalArgumentException("Cannot create a joint string from an empty string");
        }
        return create(ImmutableList.copyOf(this.splitter.split(rawItems)));
    }

    @Override
    public JointString create(final Collection<String> items) {
        if (items.stream().anyMatch(String::isEmpty)) {
            final String errorMessage = format("Cannot create a joint string, since there's an empty item in the " +
                    "collection: %s", items);
            throw new IllegalArgumentException(errorMessage);
        }
        return new JointStringImpl(this.joiner, items, this);
    }

    @Override
    public JointString create(final Collection<String> items, final String... moreItems) {
        final ImmutableList.Builder<String> allItems = ImmutableList.<String>builder()
                .addAll(items);

        Arrays.stream(moreItems)
                .map(this.splitter::split)
                .forEach(allItems::add);

        return create(allItems.build());
    }

    @Override
    public String toString() {
        return "JointStringFactoryImpl{" +
                "splitter=" + this.splitter +
                "}";
    }
}
