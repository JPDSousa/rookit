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
package org.rookit.auto.source;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.utils.primitive.VoidUtils;

final class CodeSourceFactoriesImpl implements CodeSourceFactories {

    private final TypeSourceContainerFactory containerFactory;
    private final VoidUtils voidUtils;

    @Inject
    private CodeSourceFactoriesImpl(
            final TypeSourceContainerFactory containerFactory,
            final VoidUtils voidUtils) {
        this.containerFactory = containerFactory;
        this.voidUtils = voidUtils;
    }

    @Override
    public CodeSourceFactory visitorCodeSourceFactory(
            final ExtendedElementVisitor<StreamEx<TypeSource>, Void> visitor) {
        return new SpecCodeSourceFactory(this.containerFactory, visitor, this.voidUtils);
    }

    @Override
    public String toString() {
        return "CodeSourceFactoriesImpl{" +
                "containerFactory=" + this.containerFactory +
                ", voidUtils=" + this.voidUtils +
                "}";
    }

}
