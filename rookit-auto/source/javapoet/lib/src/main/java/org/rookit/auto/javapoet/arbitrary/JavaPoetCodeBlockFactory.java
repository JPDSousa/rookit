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
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class JavaPoetCodeBlockFactory implements ArbitraryCodeSourceFactory {

    @Inject
    private JavaPoetCodeBlockFactory() { }

    @Override
    public ArbitraryCodeSource createFromFormat(final String format, final List<Object> args) {

        return new JavaPoetCodeBlock(format, args);
    }

    @Override
    public ArbitraryCodeSource createEmpty() {

        return createFromFormat(EMPTY, ImmutableList.of());
    }

    @Override
    public ArbitraryCodeSource initializeDependency(final CharSequence name) {

        return createFromFormat("this.$L = $L", ImmutableList.of(name, name));
    }

    @Override
    public ArbitraryCodeSource returnNew(
            final TypeReferenceSource objectType, final Iterable<CharSequence> paramNames) {

        final List<Object> params = ImmutableList.builder()
                .add(objectType)
                .addAll(paramNames)
                .build();

        return createFromFormat("return new $T($L)", params);
    }

    @Override
    public ArbitraryCodeSource returnInstanceField(final CharSequence fieldName) {

        return createFromFormat("return this.$L", ImmutableList.of(fieldName));
    }

    @Override
    public ArbitraryCodeSource newValue(
            final TypeReferenceSource type, final CharSequence name, final ArbitraryCodeSource initializer) {

        return append(
                createFromFormat("final $T $L = ", ImmutableList.of(type, name)),
                initializer
        );
    }

    @Override
    public ArbitraryCodeSource returnField(final CharSequence fieldName) {

        return createFromFormat("return $L", ImmutableList.of(fieldName));
    }

    private ArbitraryCodeSource append(final ArbitraryCodeSource left, final ArbitraryCodeSource right) {

        return createFromFormat(
                left.format().toString() + right.format(),
                ImmutableList.builder()
                        .addAll(left.arguments())
                        .addAll(right.arguments())
                        .build()
        );
    }

}
