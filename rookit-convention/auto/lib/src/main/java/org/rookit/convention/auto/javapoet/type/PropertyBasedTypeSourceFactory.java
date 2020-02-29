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
package org.rookit.convention.auto.javapoet.type;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.utils.primitive.VoidUtils;

final class PropertyBasedTypeSourceFactory extends AbstractInterfaceTypeSourceFactory {

    private final ExtendedElementVisitor<StreamEx<MethodSource>, Void> visitor;
    private final VoidUtils voidUtils;

    PropertyBasedTypeSourceFactory(
            final TypeVariableSourceFactory parameterResolver,
            final ExtendedElementVisitor<StreamEx<MethodSource>, Void> visitor,
            final ConventionTypeElementFactory elementFactory,
            final VoidUtils voidUtils,
            final TypeSourceFactory typeFactory) {
        super(parameterResolver, elementFactory, visitor, typeFactory, voidUtils);
        this.visitor = visitor;
        this.voidUtils = voidUtils;
    }

    @Override
    protected Iterable<MethodSource> methodsFor(final ExtendedTypeElement element) {
        return element.accept(this.visitor, this.voidUtils.returnVoid());
    }

    @Override
    public String toString() {
        return "PropertyBasedTypeSourceFactory{" +
                "visitor=" + this.visitor +
                ", voidUtils=" + this.voidUtils +
                "} " + super.toString();
    }

}
