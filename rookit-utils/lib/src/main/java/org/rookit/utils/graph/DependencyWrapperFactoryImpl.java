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
package org.rookit.utils.graph;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.utils.optional.OptionalFactory;

import java.util.function.Function;

final class DependencyWrapperFactoryImpl implements DependencyWrapperFactory {

    private final OptionalFactory optionalFactory;

    @Inject
    private DependencyWrapperFactoryImpl(final OptionalFactory optionalFactory) {
        this.optionalFactory = optionalFactory;
    }

    @Override
    public <D> DependencyWrapper<D> createSingle(
            final String dependencyName,
            final Function<D, Dependency<?>> dependencyFactory) {
        return new DependencyWrapperImpl<>(this.optionalFactory, dependencyFactory, dependencyName);
    }

    @Override
    public <D> MultiDependencyWrapper<D> createMulti(
            final String dependencyName,
            final Function<D, Dependency<?>> dependencyFactory) {
        return new MultiDependencyWrapperImpl<>(ImmutableList.of(), dependencyFactory, dependencyName);
    }

    @Override
    public String toString() {
        return "DependencyWrapperFactoryImpl{" +
                "optionalFactory=" + this.optionalFactory +
                "}";
    }

}
