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
package org.rookit.guice.auto.bind;

import com.google.common.collect.ImmutableList;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

final class SourceBindStatementImpl implements SourceBindingStatement {

    private final ArbitraryCodeSourceFactory codeFactory;
    private final String bindTemplate;
    private final TypeReferenceSource source;
    private final List<Object> preParams;
    private final List<Object> postParams;

    SourceBindStatementImpl(
            final ArbitraryCodeSourceFactory codeFactory,
            final String bindTemplate,
            final TypeReferenceSource source,
            final Collection<Object> preParams,
            final Collection<Object> postParams) {
        this.codeFactory = codeFactory;
        this.bindTemplate = bindTemplate;
        this.source = source;
        this.preParams = ImmutableList.copyOf(preParams);
        this.postParams = ImmutableList.copyOf(postParams);
    }

    @Override
    public BindingStatement to(final TypeReferenceSource referenceSource) {

        final String bindFormat = format(this.bindTemplate, ".to($T)");
        final List<Object> params = ImmutableList.builder()
                .addAll(this.preParams)
                .add(referenceSource)
                .addAll(this.postParams)
                .build();

        return new BindingStatementImpl(
                this.codeFactory.createFromFormat(bindFormat, params),
                this.source
        );
    }

}
