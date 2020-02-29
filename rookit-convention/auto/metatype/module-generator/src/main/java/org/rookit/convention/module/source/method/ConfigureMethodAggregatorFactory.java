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
package org.rookit.convention.module.source.method;

import com.google.inject.Inject;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregator;
import org.rookit.auto.javax.aggregator.ExtendedElementAggregatorFactory;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.guice.MetaType;
import org.rookit.utils.primitive.VoidUtils;

final class ConfigureMethodAggregatorFactory implements ExtendedElementAggregatorFactory<MethodSource> {

    private final NamingFactory apiNamingFactory;
    private final NamingFactory implNamingFactory;
    private final MethodSourceFactory methodFactory;
    private final VoidUtils voidUtils;

    @Inject
    private ConfigureMethodAggregatorFactory(@MetaTypeAPI final NamingFactory apiNamingFactory,
                                             @MetaType final NamingFactory implNamingFactory,
                                             final MethodSourceFactory methodFactory,
                                             final VoidUtils voidUtils) {
        this.apiNamingFactory = apiNamingFactory;
        this.implNamingFactory = implNamingFactory;
        this.methodFactory = methodFactory;
        this.voidUtils = voidUtils;
    }

    @Override
    public ExtendedElementAggregator<MethodSource> create() {
        return new ConfigureMethodAggregator(this.apiNamingFactory, this.implNamingFactory,
                                             this.methodFactory, this, this.voidUtils, codeFactory);
    }

    @Override
    public String toString() {
        return "ConfigureMethodAggregatorFactory{" +
                "apiNamingFactory=" + this.apiNamingFactory +
                ", implNamingFactory=" + this.implNamingFactory +
                ", methodFactory=" + this.methodFactory +
                ", voidUtils=" + this.voidUtils +
                "}";
    }
}
