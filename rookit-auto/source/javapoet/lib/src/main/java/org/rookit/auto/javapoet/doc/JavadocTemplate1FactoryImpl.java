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
package org.rookit.auto.javapoet.doc;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import org.apache.commons.lang3.StringUtils;
import org.rookit.auto.source.doc.JavadocTemplate1;
import org.rookit.auto.source.doc.JavadocTemplate1Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class JavadocTemplate1FactoryImpl implements JavadocTemplate1Factory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(JavadocTemplate1FactoryImpl.class);

    @Inject
    private JavadocTemplate1FactoryImpl() { }

    @Override
    public Maybe<JavadocTemplate1> create(final String rawFormat) {
        // TODO this validation is highly shotgun surgerish, given that it "copies" logic from JavaPoet's CodeBlock.
        final int tokensCount = StringUtils.countMatches(rawFormat, '$');
        if (tokensCount != 1) {
            //noinspection AutoBoxing
            logger.info("Attempting to create a javadoc template with 1 token from a format with {} tokens. Skipping.",
                    tokensCount);
            return Maybe.empty();
        }
        return Maybe.just(new JavaPoetJavadocTemplate1Impl(rawFormat));
    }

}
