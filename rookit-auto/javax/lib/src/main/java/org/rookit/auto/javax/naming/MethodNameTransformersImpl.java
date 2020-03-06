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
package org.rookit.auto.javax.naming;

import com.google.inject.Inject;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.utils.guice.Self;
import org.rookit.utils.primitive.VoidUtils;
import org.rookit.utils.string.StringUtils;
import org.rookit.utils.string.template.Template1;

import javax.lang.model.util.Elements;

final class MethodNameTransformersImpl implements MethodNameTransformers {

    private final Elements elements;
    private final StringUtils stringUtils;
    private final VoidUtils voidUtils;
    private final Template1 noopTemplate;
    private final ExtendedPackageElementFactory factory;

    @Inject
    private MethodNameTransformersImpl(
            final Elements elements,
            final StringUtils stringUtils,
            final VoidUtils voidUtils, @Self final Template1 noopTemplate,
            final ExtendedPackageElementFactory factory) {
        this.elements = elements;
        this.stringUtils = stringUtils;
        this.voidUtils = voidUtils;
        this.noopTemplate = noopTemplate;
        this.factory = factory;
    }

    @Override
    public MethodNameTransformer fromTemplate(final Template1 methodTemplate) {
        return new BaseMethodNameTransformer(methodTemplate);
    }

    @Override
    public String toString() {
        return "NamingFactoriesImpl{" +
                "elements=" + this.elements +
                ", stringUtils=" + this.stringUtils +
                ", voidUtils=" + this.voidUtils +
                ", noopTemplate=" + this.noopTemplate +
                ", factory=" + this.factory +
                "}";
    }

}
