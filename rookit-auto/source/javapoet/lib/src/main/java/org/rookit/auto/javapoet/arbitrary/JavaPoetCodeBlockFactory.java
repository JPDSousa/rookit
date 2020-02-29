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
package org.rookit.auto.javapoet.arbitrary;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.utils.primitive.VoidUtils;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class JavaPoetCodeBlockFactory implements ArbitraryCodeSourceFactory {

    private final VoidUtils voidUtils;

    @Inject
    private JavaPoetCodeBlockFactory(final VoidUtils voidUtils) {
        this.voidUtils = voidUtils;
    }

    @Override
    public ArbitraryCodeSource createFromFormat(final String format, final List<Object> args) {

        return new JavaPoetCodeBlock(format, args);
    }

    @Override
    public ArbitraryCodeSource createEmpty() {

        return createFromFormat(EMPTY, ImmutableList.of());
    }

    @Override
    public String toString() {
        return "JavaPoetCodeBlockFactory{" +
                "voidUtils=" + this.voidUtils +
                "}";
    }

}
