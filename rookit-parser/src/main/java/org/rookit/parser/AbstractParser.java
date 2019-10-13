/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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
package org.rookit.parser;

import one.util.streamex.StreamEx;
import org.rookit.parser.utils.ParserValidator;

import java.util.Optional;

@SuppressWarnings("javadoc")
public abstract class AbstractParser<T, R> implements Parser<T, R> {

    protected static final Validator VALIDATOR = ParserValidator.getDefault();
    private static final String PARAM_CONTEXT = "context";

    protected abstract R getDefaultBaseResult();

    @Override
    public final Optional<R> parse(final T context) {
        VALIDATOR.checkArgument().isNotNull(context, PARAM_CONTEXT);

        return parseFromBaseResult(context, getDefaultBaseResult());
    }

    @Override
    public final Optional<R> parse(final T context, final R baseResult) {
        VALIDATOR.checkArgument().isNotNull(context, PARAM_CONTEXT);
        VALIDATOR.checkArgument().isNotNull(baseResult, "baseResult");

        return parseFromBaseResult(context, baseResult);
    }

    @Override
    public StreamEx<R> parseAll(final T context) {
        return StreamEx.of(parse(context));
    }

    @Override
    public final StreamEx<R> parseAll(final T context, final R baseResult) {
        VALIDATOR.checkArgument().isNotNull(context, PARAM_CONTEXT);

        return parseAllFromBaseResult(context, baseResult);
    }

    protected StreamEx<R> parseAllFromBaseResult(final T context, final R baseResult) {
        return StreamEx.of(parseFromBaseResult(context, baseResult));
    }

    protected abstract Optional<R> parseFromBaseResult(T context, R baseResult);

}
