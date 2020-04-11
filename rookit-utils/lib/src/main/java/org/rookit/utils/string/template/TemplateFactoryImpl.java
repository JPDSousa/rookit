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
package org.rookit.utils.string.template;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class TemplateFactoryImpl implements TemplateFactory {

    // TODO inject?
    private static final Pattern TEMPLATE1_FORMAT = Pattern.compile("\\{}");

    private final Template1 selfTemplate1;

    @Inject
    private TemplateFactoryImpl() {
        this.selfTemplate1 = new SelfTemplate1();
    }

    @Override
    public Template1 template1() {
        return this.selfTemplate1;
    }

    @Override
    public Template1 template1(final String template) {
        final int matches = StringUtils.countMatches(template, "{}");

        if (matches == 0) {
            throw new IllegalArgumentException("Invalid template string: must contain {}.");
        }
        if (matches > 1) {
            throw new IllegalArgumentException("Invalid template string: must contain a single {} token.");
        }

        final String[] split = TEMPLATE1_FORMAT.split(template);
        final String prefix = (split.length >= 1) ? split[0] : EMPTY;
        final String suffix = (split.length >= 2) ? split[1] : EMPTY;
        return new Template1Impl(prefix, suffix);
    }

}
